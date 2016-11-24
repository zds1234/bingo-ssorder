package jtemp.hardware.hw;

import com.ftdi.j2xx.D2xxManager;

/**
 * Created by ZMS on 2016/11/24.
 */

public enum DeviceType {

    FT_DEVICE_232B(D2xxManager.FT_DEVICE_232B, "DEVICE_232B"),
    FT_DEVICE_8U232AM(D2xxManager.FT_DEVICE_8U232AM, "DEVICE_8U232AM"),
    FT_DEVICE_UNKNOWN(D2xxManager.FT_DEVICE_UNKNOWN, "DEVICE_UNKNOWN"),
    FT_DEVICE_2232(D2xxManager.FT_DEVICE_2232, "DEVICE_2232"),
    FT_DEVICE_232R(D2xxManager.FT_DEVICE_232R, "DEVICE_232R"),
    FT_DEVICE_245R(D2xxManager.FT_DEVICE_245R, "DEVICE_245R"),
    FT_DEVICE_2232H(D2xxManager.FT_DEVICE_2232H, "DEVICE_2232H"),
    FT_DEVICE_4232H(D2xxManager.FT_DEVICE_4232H, "DEVICE_4232H"),
    FT_DEVICE_232H(D2xxManager.FT_DEVICE_232H, "DEVICE_232H"),
    FT_DEVICE_X_SERIES(D2xxManager.FT_DEVICE_X_SERIES, "DEVICE_X_SERIES"),
    //
    ;

    public static DeviceType fromType(int type) {
        for (DeviceType dt : DeviceType.values()) {
            if (dt.getType() == type) {
                return dt;
            }
        }
        return DeviceType.FT_DEVICE_232B;
    }

    DeviceType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    int type;
    String name;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
