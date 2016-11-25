package jtemp.hardware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import jtemp.hardware.hw.SerialPortHardwareManager;

public class HardwareTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SerialPortHardwareManager.initHardware(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_test);
        SerialPortHardwareManager.setupD2xxLibrary();
        refreshHardware();
        findViewById(R.id.refresh).setOnClickListener(this);
        findViewById(R.id.cut).setOnClickListener(this);
        findViewById(R.id.print).setOnClickListener(this);
        findViewById(R.id.read).setOnClickListener(this);
    }

    private void refreshHardware() {
        List<HardDevice> list = SerialPortHardwareManager.getDeviceInfoList();
        StringBuilder sb = new StringBuilder();
        if (!list.isEmpty()) {
            for (HardDevice hardDevice : list) {
                sb.append(hardDevice.toString()).append("\n");
            }
        } else {
            sb.append("无设备连接");
        }
        TextView text = (TextView) findViewById(R.id.device_info);
        text.setText(sb.toString());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.refresh) {
            refreshHardware();
        } else if (v.getId() == R.id.cut) {
        } else if (v.getId() == R.id.print) {
            print();
        } else if (v.getId() == R.id.read) {
        }
    }

    private void print() {

    }
}
