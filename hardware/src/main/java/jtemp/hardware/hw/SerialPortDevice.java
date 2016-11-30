package jtemp.hardware.hw;

import android.util.Log;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;

import java.io.UnsupportedEncodingException;

/**
 * Created by ZMS on 2016/11/25.
 */

public class SerialPortDevice {

    D2xxManager.FtDeviceInfoListNode devInfo;
    D2xxManager d2xxManager;

    boolean readable;

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
        if (device != null && device.isOpen()) {
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

    public boolean isReadable() {
        return readable;
    }

    @Override
    public String toString() {
        return String.format("DEVICE:[serialNumber=%s,location=%s,open=%s,readable=%s]", devInfo.serialNumber, devInfo.location, isDeviceOpen(), isReadable());
    }

    public void print(String s) throws UnsupportedEncodingException {
        if (!isDeviceOpen()) {
            return;
        }
        device.setLatencyTimer((byte) 16);
        byte[] out = s.getBytes("GBK");
        device.write(out);
        byte[] cmd_feed = new byte[]{0x0a};
        device.write(cmd_feed);
        byte[] cmd_cut = new byte[]{0x1d, 0x56, 0x00};
        device.write(cmd_cut);
    }

    class DeviceReader extends Thread {

        SerialPortDevice device;

        private byte[] readData;

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
                    if (System.currentTimeMillis() - lastLoopback >= 5000) {
                        loopback();
                        lastLoopback = System.currentTimeMillis();
                    }
                } catch (Exception e) {
                    readData = null;
                    e.printStackTrace();
                }
            }
        }

        private void read() {
            synchronized (device) {
                int len = device.device.getQueueStatus();
                if (len > 0) {
                    device.readable = true;
                    if (readData == null) {
                        readData = new byte[len];
                        device.device.read(readData);
                    } else {
                        byte[] data = new byte[len];
                        device.device.read(data);
                        byte[] temp = new byte[readData.length + data.length];
                        System.arraycopy(readData, 0, temp, 0, readData.length);
                        System.arraycopy(data, 0, temp, readData.length, data.length);
                        readData = temp;
                    }
                    return;
                }
                if (readData != null) {
                    byte[] temp = readData;
                    readData = null;
                    SerialPortHardwareManager.onDeviceReadData(device, temp);
                }
            }
        }

        private void loopback() {
            byte[] data1 = new byte[]{(byte) 0xFF};
            device.device.write(data1);
            byte[] data2 = new byte[]{(byte) 0x00};
            device.device.write(data2);
        }
    }
}
