package app.vit.vitauth.exam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.vit.vitauth.R;

public class ExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exam);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ExamFragment()).commit();
        }
    }
}
