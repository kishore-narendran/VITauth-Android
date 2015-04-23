package app.vit.vitauth.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.vit.vitauth.R;
import app.vit.vitauth.student.StudentsActivity;
import data.Class;
import data.ClassStudent;
import data.ExamInfo;

public class ExamFragment extends Fragment {

    private Intent intent;
    private ExamInfo examInfo;
    private Class[] classes;
    private ExamListAdapter examListAdapter;

    public ExamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("exam_info")) {

            classes = new Class[3];
            classes[0] = new Class(2466, "CSE211", "Operating Systems", new ClassStudent[2]);
            classes[1] = new Class(3663, "CSE305", "Computer Networks", new ClassStudent[2]);
            classes[2] = new Class(4687, "CSE319", "Database Systems", new ClassStudent[2]);

        } else {
            classes = new Class[3];
            classes[0] = new Class(2466, "CSE211", "Operating Systems", new ClassStudent[2]);
            classes[1] = new Class(3663, "CSE305", "Computer Networks", new ClassStudent[2]);
            classes[2] = new Class(4687, "CSE319", "Database Systems", new ClassStudent[2]);
        }

        View rootView = inflater.inflate(R.layout.fragment_exam, container, false);

        List<Class> examList = new ArrayList<>(Arrays.asList(classes));
        examListAdapter = new ExamListAdapter(getActivity(), examList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_exam);
        listView.setAdapter(examListAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), StudentsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
