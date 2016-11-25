package jtemp.hardware.hw;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.ftdi.j2xx.D2xxManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ZMS on 2016/11/24.
 */

public class SerialPortHardwareManager {

    public static final int MESSAGE_RECEIVE_DEVICE_DATA = 0x0101;

    public static final String TAG = "SerialPortHW";

    private static Context applicationContext;

    private static D2xxManager ftD2xx;

    private static Handler handler;

    private static boolean init = false;

    private static Map<String, SerialPortDevice> deviceList = new HashMap<>();

    public static void initHardware(Context applicationContext, Handler handler) {
        try {
            if (init) {
                return;
            }
            SerialPortHardwareManager.handler = handler;
            SerialPortHardwareManager.applicationContext = applicationContext;
            ftD2xx = D2xxManager.getInstance(applicationContext);
            if (!ftD2xx.setVIDPID(0x0403, 0xada1)) {
                Log.i(TAG, "ftD2xx.setVIDPID(0x0403, 0xada1) error");
            }
        } catch (D2xxManager.D2xxException e) {
            e.printStackTrace();
        }
    }

    public static int getDeviceCount() {
        return ftD2xx.createDeviceInfoList(applicationContext);
    }

    private static D2xxManager.FtDeviceInfoListNode[] getDeviceInfoList() {
        int devCount = getDeviceCount();
        if (devCount <= 0) {
            return null;
        }
        D2xxManager.FtDeviceInfoListNode[] deviceList = new D2xxManager.FtDeviceInfoListNode[devCount];
        ftD2xx.getDeviceInfoList(devCount, deviceList);
        return deviceList;
    }

    public static void checkAndOpenHardware() {
        long start = System.currentTimeMillis();
        Log.i(TAG, "设备检测开始");
        //移除断开设备
        for (Iterator<SerialPortDevice> it = deviceList.values().iterator(); it.hasNext(); ) {
            SerialPortDevice device = it.next();
            if (!device.isClosed()) {
                Log.i(TAG, "设备断开连接：" + device);
                makeToast("设备断开连接：" + device);
                it.remove();
            }
        }

        //发现新设备
        D2xxManager.FtDeviceInfoListNode[] devList = getDeviceInfoList();
        if (devList == null || devList.length == 0) {
            return;
        }
        for (D2xxManager.FtDeviceInfoListNode devNode : devList) {
            if (isNewDevice(devNode)) {
                SerialPortDevice device = new SerialPortDevice(ftD2xx, devNode);
                deviceList.put(device.getId(), device);
            }
        }

        //连接新设备
        for (Iterator<SerialPortDevice> it = deviceList.values().iterator(); it.hasNext(); ) {
            SerialPortDevice device = it.next();
            if (!device.isDeviceOpen()) {
                device.openDevice();
            }
        }

        long time = System.currentTimeMillis() - start;
        Log.i(TAG, "设备检测结束 " + time + "(ms)");

    }

    private static boolean isNewDevice(D2xxManager.FtDeviceInfoListNode devNode) {
        return false;
    }

    private static void makeToast(String s) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show();
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void onDeviceReadData(SerialPortDevice device, byte[] data) {
        Log.i(TAG, "从设备 " + device + " 读取到数据：" + new String(data));
        handler.sendMessage(handler.obtainMessage(MESSAGE_RECEIVE_DEVICE_DATA, data));
    }
}
