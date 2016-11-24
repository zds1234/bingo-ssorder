package jtemp.hardware.hw;

import android.content.Context;
import android.widget.Toast;

import com.ftdi.j2xx.D2xxManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZMS on 2016/11/24.
 */

public class HardwareHelper {

    private static Context applicationContext;

    public static D2xxManager ftD2xx;

    private static boolean init = false;

    private static boolean setup = false;

    public static void initHardware(Context applicationContext) {
        try {
            if (init) {
                return;
            }
            HardwareHelper.applicationContext = applicationContext;
            ftD2xx = D2xxManager.getInstance(applicationContext);
            init = true;
        } catch (D2xxManager.D2xxException e) {
            e.printStackTrace();
        }
    }

    public static void setupD2xxLibrary() {
        if (init && setup) {
            return;
        }
        if (!ftD2xx.setVIDPID(0x0403, 0xada1)) {
            Toast.makeText(applicationContext, "ftd2xx setVIDPID Error", Toast.LENGTH_SHORT).show();
        } else {
            setup = true;
        }
    }

    public static int getDeviceCount() {
        return ftD2xx.createDeviceInfoList(applicationContext);
    }

    public static List<HardDevice> getDeviceInfoList() {
        int devCount = getDeviceCount();
        if (devCount <= 0) {
            return Collections.emptyList();
        }
        List<HardDevice> list = new ArrayList<>();
        D2xxManager.FtDeviceInfoListNode[] deviceList = new D2xxManager.FtDeviceInfoListNode[devCount];
        ftD2xx.getDeviceInfoList(devCount, deviceList);
        for (D2xxManager.FtDeviceInfoListNode dev : deviceList) {
            HardDevice device = new HardDevice();
            device.setSerialNumber(dev.serialNumber);
            device.setDescription(dev.description);
            device.setId(dev.id);
            device.setLocation(dev.location);
            device.setType(DeviceType.fromType(dev.type));
            list.add(device);
        }
        return list;
    }

}
