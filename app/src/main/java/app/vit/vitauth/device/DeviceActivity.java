package app.vit.vitauth.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.vit.vitauth.R;

public class DeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DeviceFragment())
                    .commit();
        }
    }
}
