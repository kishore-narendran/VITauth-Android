package app.vit.vitauth.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

public class StudentsFragment extends Fragment {

    private final String intentExtraClassNumber = "class_number";
    private final String intentExtraListPosition = "list_position";

    private final int defaultClassNumber = 1000;
    private final int defaultListPosition = 0;

    private MainApplication application;

    private View rootView;

    private int classNumber;
    private int listPosition;

    private ExamInfo examInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_students, container, false);

        initData();
        initView();

        return rootView;
    }

    private void initData() {
        Gson gson = new Gson();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        String examInfoStr = sharedPreferences.getString("exam_info", null);

        examInfo = gson.fromJson(examInfoStr, ExamInfo.class);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(intentExtraClassNumber) && intent.hasExtra(intentExtraListPosition)) {
            classNumber = intent.getIntExtra(intentExtraClassNumber, defaultClassNumber);
            listPosition = intent.getIntExtra(intentExtraListPosition, defaultListPosition);
        }
    }

    private void initView() {
        Student[] students = examInfo.getClasses()[listPosition].getStudents();

        ArrayList<Student> studentsArrayList = new ArrayList<>(Arrays.asList(students));
        StudentListAdapter studentListAdapter = new StudentListAdapter(getActivity(), studentsArrayList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
        listView.setAdapter(studentListAdapter);
    }
}
