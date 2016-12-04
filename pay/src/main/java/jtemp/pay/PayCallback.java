package jtemp.pay;

/**
 * Created by ZMS on 2016/12/2.
 */

public interface PayCallback {

    void paySuccess(String order, double value, Object msg);

    void payFailed(String order, double value, Object msg);

}
