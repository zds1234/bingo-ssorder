package jtemp.hardware.hw;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.ftdi.j2xx.D2xxManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ZMS on 2016/11/24.
 */

public class SerialPortHardwareManager {

    public static final int MESSAGE_RECEIVE_DEVICE_DATA = 0x0101;
    public static final int MESSAGE_HDM_TICK = 0x0102;

    public static final String TAG = "SerialPortHW";

    private static Context applicationContext;

    private static D2xxManager ftD2xx;

    private static boolean init = false;

    private static int deviceCount;

    private static List<Handler> handlers = Collections.synchronizedList(new ArrayList<Handler>());

    private static Map<String, SerialPortDevice> deviceList = Collections.synchronizedMap(new HashMap<String, SerialPortDevice>());

    public static void initHardware(Context applicationContext) {
        try {
            if (init) {
                return;
            }
            SerialPortHardwareManager.applicationContext = applicationContext;
            ftD2xx = D2xxManager.getInstance(applicationContext);
            if (!ftD2xx.setVIDPID(0x0403, 0xada1)) {
                Log.i(TAG, "ftD2xx.setVIDPID(0x0403, 0xada1) error");
            }
        } catch (D2xxManager.D2xxException e) {
            e.printStackTrace();
        }
    }

    private static D2xxManager.FtDeviceInfoListNode[] getDeviceInfoList() {
        deviceCount = ftD2xx.createDeviceInfoList(applicationContext);
        if (deviceCount <= 0) {
            return null;
        }
        D2xxManager.FtDeviceInfoListNode[] deviceList = new D2xxManager.FtDeviceInfoListNode[deviceCount];
        ftD2xx.getDeviceInfoList(deviceCount, deviceList);
        return deviceList;
    }

    public static void checkAndOpenHardware() {
        long start = System.currentTimeMillis();
        try {
            //tick
            for (Handler handler : handlers) {
                handler.sendMessage(handler.obtainMessage(MESSAGE_HDM_TICK, System.currentTimeMillis()));
            }
            //移除断开设备
            for (Iterator<SerialPortDevice> it = deviceList.values().iterator(); it.hasNext(); ) {
                SerialPortDevice device = it.next();
                if (device.isClosed()) {
                    Log.i(TAG, "设备断开连接：" + device);
                    it.remove();
                }
            }

            //发现新设备
            D2xxManager.FtDeviceInfoListNode[] devList = getDeviceInfoList();
            if (devList == null || devList.length == 0) {
                return;
            }
            for (D2xxManager.FtDeviceInfoListNode devNode : devList) {
                String key = String.valueOf(devNode.id) + "_" + String.valueOf(devNode.serialNumber) + "_" + String.valueOf(devNode.location);
                if (isNewDevice(key)) {
                    Log.i(TAG, "发现新设备:" + key);
                    SerialPortDevice device = new SerialPortDevice(ftD2xx, devNode);
                    deviceList.put(key, device);
                }
            }

            //连接新设备
            for (Iterator<SerialPortDevice> it = deviceList.values().iterator(); it.hasNext(); ) {
                SerialPortDevice device = it.next();
                if (!device.isDeviceOpen()) {
                    Log.i(TAG, "设备连接成功：" + device.toString());
                    device.openDevice();
                } else {
                    Log.i(TAG, "设备连接失败：" + device.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long time = System.currentTimeMillis() - start;
            Log.i(TAG, "设备检测结束 " + time + "(ms)");
        }
    }

    private static boolean isNewDevice(String devKey) {
        return !deviceList.containsKey(devKey);
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void onDeviceReadData(SerialPortDevice device, byte[] data) {
        Log.i(TAG, "从设备 " + device + " 读取到数据：" + new String(data));
        for (Handler handler : handlers) {
            handler.sendMessage(handler.obtainMessage(MESSAGE_RECEIVE_DEVICE_DATA, data));
        }
    }

    public static void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public static Map<String, SerialPortDevice> getDeviceList() {
        return deviceList;
    }

    public static int getDeviceCount() {
        return deviceCount;
    }

    public static void removeHandler(Handler handler) {
        handlers.remove(handler);
    }

    public static void print(String s) {
        for (SerialPortDevice device : deviceList.values()) {
            try {
                device.print(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
