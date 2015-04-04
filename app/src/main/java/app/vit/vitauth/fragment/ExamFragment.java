package app.vit.vitauth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.vit.vitauth.R;
import data.Class;
import data.ExamInfo;

public class ExamFragment extends Fragment {

    private ExamInfo examInfo;

    public ExamFragment() {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("exam_info")) {
            examInfo = (ExamInfo) intent.getExtras().getSerializable("exam_info");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exam, container, false);
        Class[] classes = examInfo.getClasses();

        String classNumbers[] = new String[classes.length];

        for (int i = 0; i < classes.length; ++i) {
            classNumbers[i] = classes[i].getClassNumber().toString();
        }

        List<String> examList = new ArrayList<>(Arrays.asList(classNumbers));
        ArrayAdapter<String> examListAdapter = new ArrayAdapter<>(getActivity(), R.layout.exam_listview_item, R.id.listview_exam, examList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_exam);
        listView.setAdapter(examListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class classInfo = examInfo.getClasses()[position];

                // Intent intent = new Intent(getActivity(), StudentsActivity.class).putExtras("class_info", classInfo);
                // startActivity(intent);
            }
        });

        return rootView;
    }
}
