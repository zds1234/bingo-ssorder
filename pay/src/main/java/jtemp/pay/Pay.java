package jtemp.pay;

import java.util.Map;

/**
 * Created by ZMS on 2016/12/1.
 */

public enum Pay {

    SEMOOR;//亚博松

    public void config(Map<String,String> config) {
        getPayService().config(config);
    }

    public PayService getPayService() {
        switch (this){
            case SEMOOR:
                return SemoorPayService.getInstance();
            default:
                return null;
        }
    }
}
