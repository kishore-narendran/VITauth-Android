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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.vit.vitauth.R;
import data.Class;
import data.ClassStudent;
import data.ExamInfo;

public class ExamFragment extends Fragment {

    private Intent intent;
    private ExamInfo examInfo;
    private Class[] classes;

    public ExamFragment() {
        // Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("exam_info")) {
            examInfo = (ExamInfo) intent.getExtras().getSerializable("exam_info");
            classes = examInfo.getClasses();
        } else {
            classes = new Class[3];
            classes[0] = new Class(2466, "CSE211", "Operating Systems", new ClassStudent[2]);
            classes[1] = new Class(3663, "CSE305", "Computer Networks", new ClassStudent[2]);
            classes[2] = new Class(4687, "CSE319", "Datbase Systems", new ClassStudent[2]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exam, container, false);

        String viewText[] = new String[classes.length];
        for (int i = 0; i < classes.length; ++i) {
            viewText[i] = classes[i].getCode() + " - " + classes[i].getTitle() + " (" + classes[i].getClassNumber() + ")";
        }

        List<String> examList = new ArrayList<>(Arrays.asList(viewText));
        ArrayAdapter<String> examListAdapter = new ArrayAdapter<>(getActivity(), R.layout.exam_listview_item, R.id.list_item_exam_textview, examList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_exam);
        listView.setAdapter(examListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class classInfo = classes[position];
                Toast.makeText(getActivity().getApplicationContext(), classInfo.getTitle(),
                        Toast.LENGTH_LONG).show();

                // Intent intent = new Intent(getActivity(), StudentsActivity.class).putExtras("exam_info", examInfo);
                // startActivity(intent);
            }
        });

        return rootView;
    }
}
