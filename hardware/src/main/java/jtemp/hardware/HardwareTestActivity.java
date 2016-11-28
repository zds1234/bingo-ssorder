package jtemp.hardware;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import jtemp.hardware.hw.HardwareCheckThread;
import jtemp.hardware.hw.SerialPortDevice;
import jtemp.hardware.hw.SerialPortHardwareManager;

public class HardwareTestActivity extends AppCompatActivity implements View.OnClickListener {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SerialPortHardwareManager.MESSAGE_RECEIVE_DEVICE_DATA:
                    byte[] data = (byte[]) msg.obj;
                    TextView textView = (TextView) findViewById(R.id.read_info);
                    textView.setText(new String(data));
                    break;
                case SerialPortHardwareManager.MESSAGE_HDM_TICK:
                    refreshHardware();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_test);
        refreshHardware();
        findViewById(R.id.print).setOnClickListener(this);
        SerialPortHardwareManager.addHandler(handler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SerialPortHardwareManager.addHandler(handler);
    }

    private void refreshHardware() {
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(new Date())).append("\n");
        sb.append("设备检测启动：").append(HardwareCheckThread.isHardwareServiceRunning()).append("\n");
        sb.append("设备连接数量：").append(SerialPortHardwareManager.getDeviceCount()).append("\n");
        sb.append("设备连接成功数量：").append(SerialPortHardwareManager.getDeviceList().size()).append("\n");

        Map<String, SerialPortDevice> map = SerialPortHardwareManager.getDeviceList();
        if (!map.isEmpty()) {
            for (SerialPortDevice device : map.values()) {
                sb.append(device.toString()).append("\n");
            }
        } else {
            sb.append("无设备连接");
        }
        TextView text = (TextView) findViewById(R.id.device_info);
        text.setText(sb.toString());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.print) {
            print();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SerialPortHardwareManager.removeHandler(handler);
    }

    private void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------测试---------").append("\n");
        sb.append("序号：001\n");
        sb.append("打印时间：").append(sdf.format(new Date())).append("\n");
        sb.append("账单号：").append("001").append("\n\n");
        sb.append("----------------------\n");
        sb.append("品名      数量      价格      金额\n");
        for (int i = 0; i < 10; i++) {
            sb.append("米饭      10      1       10\n");
        }
        sb.append("----------------------\n");
        sb.append("总金额 ：").append("10000.0").append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        SerialPortHardwareManager.print(sb.toString());
    }
}
