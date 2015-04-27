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
import app.vit.vitauth.R;

public class StudentsFragment extends Fragment {

    private Intent intent;
    private ClassStudent students[];
    private String examInfoStr;
    private ExamInfo examInfo;
    public StudentsListAdapter myStudentsListAdapter;
    public StudentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Generating static data for testing
        intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra("exam_info")) {
            int position = Integer.parseInt(intent.getStringExtra("position"));
            examInfoStr = intent.getStringExtra("exam_info");
            Gson gson = new Gson();
            examInfo = gson.fromJson(examInfoStr, ExamInfo.class);
            students = examInfo.getClasses()[position].getStudents();
        }

        ArrayList<ClassStudent> students1 = new ArrayList<>(Arrays.asList(students));
        myStudentsListAdapter = new StudentsListAdapter(getActivity(), students1);

        View rootView = inflater.inflate(R.layout.fragment_students, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
        listView.setAdapter(myStudentsListAdapter);

        return rootView;
    }
}
