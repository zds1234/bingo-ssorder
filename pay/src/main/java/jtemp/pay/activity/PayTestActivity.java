package jtemp.pay.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import jtemp.hardware.hw.SerialPortHardwareManager;
import jtemp.pay.Pay;
import jtemp.pay.PayCallback;
import jtemp.pay.R;

public class PayTestActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean paying = false;

    private boolean scan = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SerialPortHardwareManager.MESSAGE_RECEIVE_DEVICE_DATA:
                    byte[] data = (byte[]) msg.obj;
                    payScanCode(new String(data).trim());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pay_test);
        findViewById(R.id.online_pay).setOnClickListener(this);
        SerialPortHardwareManager.addHandler(handler);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.online_pay) {
            if (!paying) {
                startPay();
            }
        } else {
        }
    }

    private void startPay() {
        setPayText("请扫微信或支付宝二维码，会支付0.1元");
        paying = true;
        findViewById(R.id.online_pay).setEnabled(false);
        new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (paying && !scan) {
                    setPayText("请扫微信或支付宝二维码，会支付0.1元 " + ((20000 - millisUntilFinished) / 1000));
//                    if(millisUntilFinished < 15000) {
//                        payScanCode("280729987231257034");
//                    }
                } else {
                    this.cancel();
                }
            }

            @Override
            public void onFinish() {
                if (paying && !scan) {
                    stopPay("请选择支付方式", false);
                }
            }
        }.start();
    }

    private void setPayText(String text) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(text);
    }

    private void appendPayText(String text) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(textView.getText().toString().trim() + "\n" + text);
    }

    private void payScanCode(String authCode) {
        if (paying) {
            scan = true;
            if (authCode == null) {
                stopPay("扫码失败", true);
            } else if (authCode.matches("1[1-5]\\d{16}")) {
                setPayText("扫描微信付款成功 : " + authCode);
                Pay.SEMOOR.getPayService().startWechatPay("order:test", authCode, 0.1, new PayCallback() {
                    @Override
                    public void paySuccess(String order, double value, Object msg) {
                        stopPay("微信支付成功", true);
                    }

                    @Override
                    public void payFailed(String order, double value, Object msg) {
                        stopPay("微信支付失败：" + msg, true);
                    }
                });
            } else if (authCode.matches("28\\d{15,16}")) {
                setPayText("扫描支付宝成功 : " + authCode);
                Pay.SEMOOR.getPayService().startAliPay("order:test", authCode, 0.1, new PayCallback() {
                    @Override
                    public void paySuccess(String order, double value, Object msg) {
                        stopPay("支付宝支付成功", true);
                    }

                    @Override
                    public void payFailed(String order, double value, Object msg) {
                        stopPay("支付宝支付失败：" + msg, true);
                    }
                });
            } else {
                setPayText("扫描未知成功：" + authCode);
                Pay.SEMOOR.getPayService().startWechatPay("order:test", authCode, 0.1, new PayCallback() {
                    @Override
                    public void paySuccess(String order, double value, Object msg) {
                        stopPay("支付成功", true);
                    }

                    @Override
                    public void payFailed(String order, double value, Object msg) {
                        stopPay("支付失败：" + msg, true);
                    }
                });
            }
        }
    }

    private void stopPay(String text, boolean append) {
        if (append) {
            appendPayText(text);
        } else {
            setPayText(text);
        }
        findViewById(R.id.online_pay).setEnabled(true);
        paying = false;
        scan = false;
    }
}
