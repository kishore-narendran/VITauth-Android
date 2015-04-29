package app.vit.vitauth.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.vit.vitauth.MainApplication;
import app.vit.vitauth.R;
import app.vit.vitauth.bluetooth.BluetoothActivity;

public class DeviceActivity extends AppCompatActivity {

    private MainApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initData();

        setContentView(R.layout.activity_device);

        checkDevice();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new DeviceFragment()).commit();
        }
    }

    private void initData() {
        application = (MainApplication) getApplicationContext();
    }

    private void checkDevice() {
        if (!application.isConnect()) {
            Intent intent = new Intent(this, BluetoothActivity.class);
            startActivity(intent);
        }
    }
}
