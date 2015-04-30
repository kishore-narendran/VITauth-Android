package app.vit.vitauth.student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.vit.vitauth.R;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StudentFragment())
                    .commit();
        }
    }
}
