package jtemp.hardware.hw;

import android.util.Log;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;

/**
 * Created by ZMS on 2016/11/25.
 */

public class SerialPortDevice {

    D2xxManager.FtDeviceInfoListNode devInfo;
    D2xxManager d2xxManager;

    private FT_Device device;

    public SerialPortDevice(D2xxManager ftD2xx, D2xxManager.FtDeviceInfoListNode devNode) {
        this.devInfo = devNode;
        this.d2xxManager = ftD2xx;
    }

    public boolean isClosed() {
        return device == null || !device.isOpen();
    }

    public String getId() {
        return String.valueOf(devInfo.id);
    }

    public boolean isDeviceOpen() {
        return device != null && device.isOpen();
    }

    public void openDevice() {
        D2xxManager.DriverParameters parameters = new D2xxManager.DriverParameters();
        device = d2xxManager.openBySerialNumber(SerialPortHardwareManager.getApplicationContext(), devInfo.serialNumber, parameters);
        if (isDeviceOpen()) {
            device.setBitMode((byte) 0, D2xxManager.FT_BITMODE_RESET);
            device.setBaudRate(9600);
            device.setDataCharacteristics((byte) 8, (byte) 0, (byte) 0);
            device.setFlowControl((short) 0, (byte) 0x0b, (byte) 0x0d);
            Log.i(SerialPortHardwareManager.TAG, "成功打开串口设备:" + this);
            DeviceReader reader = new DeviceReader(this);
            reader.start();
        } else {
            Log.i(SerialPortHardwareManager.TAG, "无法打开串口设备:" + this);
        }
    }

    @Override
    public String toString() {
        return String.format("DEVICE:[serialNumber=%s,location=%s]", devInfo.serialNumber, devInfo.location);
    }

    class DeviceReader extends Thread {

        SerialPortDevice device;

        private long lastLoopback = System.currentTimeMillis();

        public DeviceReader(SerialPortDevice serialPortDevice) {
            this.device = serialPortDevice;
        }

        @Override
        public void run() {
            while (device.isDeviceOpen()) {
                try {
                    Thread.sleep(50);
                    read();
                    if (System.currentTimeMillis() - lastLoopback >= 1000) {
                        loopback();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void read() {
            synchronized (device) {
                int len = device.device.getQueueStatus();
                if (len > 0) {
                    byte[] data = new byte[len];
                    device.device.read(data);
                    SerialPortHardwareManager.onDeviceReadData(device, data);
                }
            }
        }

        private void loopback() {
//            byte[] data1 = new byte [] {(byte)0xFF};
//            byte[] data2 = new byte [] {(byte)0x00};
        }
    }
}
