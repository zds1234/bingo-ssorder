package jtemp.pay;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jtemp.utils.Digest;
import jtemp.utils.http.Http;
import jtemp.utils.http.HttpResponse;

/**
 * Created by ZMS on 2016/12/1.
 */

public class SemoorPayService implements PayService {

    public enum PayParams {
        baseUrl,
        device_id,
        secretKey
    }

    private static SemoorPayService instance = new SemoorPayService();

    public static PayService getInstance() {
        return instance;
    }

    private Map<String, String> config = new HashMap<>();

    @Override
    public void config(Map<String, String> config) {
        this.config.clear();
        this.config.putAll(config);
    }

    @Override
    public void startWechatPay(String orderId, String authCode, double payValue, PayCallback payCallback) {
        startPay(orderId, authCode, payValue, "wxpay", payCallback);
    }

    @Override
    public void startAliPay(String orderId, String authCode, double payValue, PayCallback payCallback) {
        startPay(orderId, authCode, payValue, "alipay", payCallback);
    }

    private void startPay(final String orderId, String authCode, final double payValue, String payType, final PayCallback payCallback) {

        String totalPay = formatPayValue(payValue);

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("action", "micropay");
        paramsMap.put("authcode", authCode);
        paramsMap.put("device_id", getDeviceId());
        paramsMap.put("paytype", payType);
        paramsMap.put("total", totalPay);
        paramsMap.put("info", orderId);

        List<String> paramsKey = new ArrayList<>(paramsMap.keySet());
        Collections.sort(paramsKey);
        String param = "";
        for (int i = 0; i < paramsKey.size(); i++) {
            String and = "";
            if (i > 0) {
                and = "&";
            }
            param = param + and + paramsKey.get(i) + "=" + paramsMap.get(paramsKey.get(i));
        }

        String sign = Digest.SHA1(param + "&key=" + getSecretKey());
        final String fullParam = param + "&sign=" + sign;
        System.out.println("SemoorPayService------>" + fullParam);
        new Thread() {
            @Override
            public void run() {
                int retryCount = 0;
                boolean success = false;
                String error = "";
                while (retryCount < 5) {
                    retryCount++;
                    try {
                        HttpResponse response = Http.httpPost(config.get(PayParams.baseUrl.name()), fullParam.getBytes());
                        if (response.isSuccess()) {
                            if (response.getData() != null) {
                                String result = new String(response.getData(), "UTF-8");
                                System.out.println("SemoorPayService return ---> " + result);
                                if (result.indexOf("ok=\"0\"") > 0) {//失败
                                    int index = result.indexOf("info=");
                                    int index2 = result.indexOf("\"", index + 6);
                                    if (index > 0 && index2 > 0) {
                                        error = result.substring(index + 6, index2);
                                    }
                                    success = false;
                                    break;
                                } else if (result.indexOf("ok=\"1\"") > 0) {//成功
                                    success = true;
                                    break;
                                } else if (result.indexOf("ok=\"-1\"") > 0) {//支付中
                                    if (retryCount >= 5) {
                                        int index = result.indexOf("info=");
                                        int index2 = result.indexOf("\"", index + 6);
                                        if (index > 0 && index2 > 0) {
                                            error = result.substring(index + 6, index2);
                                        }
                                        success = false;
                                        break;
                                    }
                                }
                            }
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                final boolean fsuccess = success;
                final String ferror = error;
                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        if (fsuccess) {
                            payCallback.paySuccess(orderId, payValue, ferror);
                        } else {
                            payCallback.payFailed(orderId, payValue, ferror);
                        }
                    }
                };
                handler.sendMessage(handler.obtainMessage(0));
            }
        }.start();
    }

    private String formatPayValue(double payValue) {
        int iValue = (int) payValue;
        if (iValue == payValue) {
            return String.valueOf(iValue);
        }
        return String.valueOf(payValue);
    }

    private String getSecretKey() {
        return config.get(PayParams.secretKey.name());
    }

    private String getDeviceId() {
        return config.get(PayParams.device_id.name());
    }
}
