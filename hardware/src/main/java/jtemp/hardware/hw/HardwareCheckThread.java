package jtemp.hardware.hw;

/**
 * Created by ZMS on 2016/11/27.
 */

public class HardwareCheckThread extends Thread {

    private static boolean hardwareServiceRunning = false;

    private static HardwareCheckThread instance;

    public static boolean startHardwareCheck() {
        if (hardwareServiceRunning) {
            return false;
        }
        hardwareServiceRunning = true;
        instance = new HardwareCheckThread();
        instance.start();
        return true;
    }

    private HardwareCheckThread() {
    }

    public static void shutdown() {
        hardwareServiceRunning = false;
    }


    @Override
    public void run() {
        while (hardwareServiceRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            checkHardware();
        }
        hardwareServiceRunning = false;
    }

    private void checkHardware() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SerialPortHardwareManager.checkAndOpenHardware();
    }

    public static boolean isHardwareServiceRunning() {
        return hardwareServiceRunning;
    }
}
