package jtemp.pay;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ZMS on 2016/12/1.
 */

public  interface PayService {
    void config(Map<String, String> config);


    void startWechatPay(String orderId, String authCode, double payValue, PayCallback payCallback);

    void startAliPay(String orderId, String authCode, double payValue, PayCallback payCallback);
}
