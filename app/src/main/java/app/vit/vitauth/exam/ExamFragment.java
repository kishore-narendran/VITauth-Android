package app.vit.vitauth.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        examInfoStr = sharedPreferences.getString("exam_info", null);
        Gson gson = new Gson();
        examInfo = gson.fromJson(examInfoStr, ExamInfo.class);
        classes = examInfo.getClasses();

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
