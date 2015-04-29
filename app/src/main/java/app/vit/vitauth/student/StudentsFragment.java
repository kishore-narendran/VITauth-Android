package app.vit.vitauth.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import app.vit.data.ClassStudent;
import app.vit.data.ExamInfo;
import app.vit.vitauth.MainApplication;
import app.vit.vitauth.R;
import app.vit.vitauth.StudentListAdapter;

public class StudentsFragment extends Fragment {

    private MainApplication application;

    private StudentListAdapter studentListAdapter;
    private View rootView;

    private ClassStudent students[];
    private String examInfoStr;
    private ExamInfo examInfo;

    public StudentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_students, container, false);

        //Generating static data for testing
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("exam_info")) {
            int position = Integer.parseInt(intent.getStringExtra("position"));
            examInfoStr = intent.getStringExtra("exam_info");
            Gson gson = new Gson();
            examInfo = gson.fromJson(examInfoStr, ExamInfo.class);
            students = examInfo.getClasses()[position].getStudents();
        }

        ArrayList<ClassStudent> students1 = new ArrayList<>(Arrays.asList(students));
        studentListAdapter = new StudentListAdapter(getActivity(), students1);


        ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
        listView.setAdapter(studentListAdapter);

        return rootView;
    }
}
