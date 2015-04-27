package app.vit.vitauth.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.vit.data.Class;
import app.vit.data.ClassStudent;
import app.vit.data.ExamInfo;
import app.vit.vitauth.R;
import app.vit.vitauth.student.StudentsActivity;

public class ExamFragment extends Fragment {

    private Intent intent;
    private ExamInfo examInfo;
    private Class[] classes;
    private ExamListAdapter examListAdapter;
    private String examInfoStr;

    public ExamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("exam_info")) {
            examInfoStr = intent.getStringExtra("exam_info");
            Gson gson = new Gson();
            ExamInfo examInfo = gson.fromJson(examInfoStr, ExamInfo.class);
            classes = examInfo.getClasses();

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
                intent.putExtra("exam_info", examInfoStr);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            }
        });

        return rootView;
    }
}
