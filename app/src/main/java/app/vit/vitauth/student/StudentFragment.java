package app.vit.vitauth.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import app.vit.data.ExamInfo;
import app.vit.data.Student;
import app.vit.vitauth.MainApplication;
import app.vit.vitauth.R;
import app.vit.vitauth.StudentListAdapter;
import app.vit.vitauth.bluetooth.BluetoothActivity;
import app.vit.vitauth.device.DeviceActivity;

public class StudentFragment extends Fragment {

    private final String intentExtraClassNumber = "class_number";
    private final String intentExtraListPosition = "list_position";

    private final int defaultClassNumber = 1000;
    private final int defaultListPosition = 0;

    private MainApplication application;

    private View rootView;

    private int classNumber;
    private int examListPosition;

    private ExamInfo examInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student, container, false);
        setHasOptionsMenu(true);

        initData();
        initView();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_student, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_scan:
                openScanner();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        Gson gson = new Gson();

        application = (MainApplication) getActivity().getApplicationContext();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        String examInfoStr = sharedPreferences.getString("exam_info", null);

        examInfo = gson.fromJson(examInfoStr, ExamInfo.class);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(intentExtraClassNumber) && intent.hasExtra(intentExtraListPosition)) {
            classNumber = intent.getIntExtra(intentExtraClassNumber, defaultClassNumber);
            examListPosition = intent.getIntExtra(intentExtraListPosition, defaultListPosition);
        }
    }

    private void initView() {
        Student[] students = examInfo.getClasses()[examListPosition].getStudents();

        ArrayList<Student> studentsArrayList = new ArrayList<>(Arrays.asList(students));
        StudentListAdapter studentListAdapter = new StudentListAdapter(getActivity(), studentsArrayList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
        listView.setAdapter(studentListAdapter);
    }

    private void openScanner() {
        Intent intent;
        if (!application.isConnect()) {
            intent = new Intent(getActivity(), BluetoothActivity.class);
        } else {
            intent = new Intent(getActivity(), DeviceActivity.class);
            intent.putExtra(intentExtraClassNumber, classNumber);
            intent.putExtra(intentExtraListPosition, examListPosition);
        }
        startActivity(intent);
    }

}
