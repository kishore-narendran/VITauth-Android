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
import app.vit.vitauth.MainApplication;
import app.vit.vitauth.R;
import app.vit.vitauth.student.StudentsActivity;

public class ExamFragment extends Fragment {

    private final String intentExtraClassNumber = "class_number";
    private final String intentExtraListPosition = "list_position";

    private MainApplication application;

    private View rootView;

    private ExamInfo examInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_exam, container, false);

        initData();
        initView();

        return rootView;
    }

    private void initData() {
        Gson gson = new Gson();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        String examInfoStr = sharedPreferences.getString("exam_info", null);

        examInfo = gson.fromJson(examInfoStr, ExamInfo.class);
    }

    private void initView() {
        final Class[] classes = examInfo.getClasses();
        List<Class> examList = new ArrayList<>(Arrays.asList(classes));
        ExamListAdapter examListAdapter = new ExamListAdapter(getActivity(), examList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_exam);
        listView.setAdapter(examListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), StudentsActivity.class);
                intent.putExtra(intentExtraClassNumber, classes[position].getClassNumber());
                intent.putExtra(intentExtraListPosition, position);

                startActivity(intent);
            }
        });
    }
}
