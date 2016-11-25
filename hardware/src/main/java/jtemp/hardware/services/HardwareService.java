package jtemp.hardware.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import jtemp.hardware.hw.SerialPortHardwareManager;


public class HardwareService extends Service {

    class BackgroundThread extends Thread {

        private boolean shutdown;

        public boolean isShutdown() {
            return shutdown;
        }

        public void setShutdown(boolean shutdown) {
            this.shutdown = shutdown;
        }

        @Override
        public void run() {
            while (!shutdown) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                checkHardware();
            }
        }
    }

    private void checkHardware() {
        SerialPortHardwareManager.checkAndOpenHardware();
    }

    private BackgroundThread backgroundThread;

    private Handler handler;

    @Override
    public void onCreate() {
        backgroundThread = new BackgroundThread();
        backgroundThread.start();
        SerialPortHardwareManager.initHardware(getApplicationContext(), handler);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (backgroundThread != null) {
            backgroundThread.setShutdown(true);
        }
    }
}
