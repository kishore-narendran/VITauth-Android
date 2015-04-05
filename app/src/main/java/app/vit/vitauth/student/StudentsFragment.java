package app.vit.vitauth.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import app.vit.vitauth.R;
import app.vit.vitauth.adapters.ListStudentsAdapter;
import data.ListItemStudent;

public class StudentsFragment extends Fragment {

    public ListStudentsAdapter myListStudentsAdapter;
    public StudentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Generating static data for testing
        ListItemStudent students[] = new ListItemStudent[6];
        students[0] = new ListItemStudent("11BCE0094", "Kishore Narendran");
        students[1] = new ListItemStudent("11BCE0350", "Aarthy Chandrasekhar");
        students[2] = new ListItemStudent("11BCE0260", "Aneesh Neelam");
        students[3] = new ListItemStudent("11BCE0354", "Karthik B");
        students[4] = new ListItemStudent("11BCE0375", "Nikhil Loney");
        students[5] = new ListItemStudent("11BCE0088", "Sreeram Boyapati");

        ArrayList<ListItemStudent> students1 = new ArrayList<>(Arrays.asList(students));
        myListStudentsAdapter = new ListStudentsAdapter(getActivity(), students1);

        View rootView = inflater.inflate(R.layout.fragment_students, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
        listView.setAdapter(myListStudentsAdapter);

        return rootView;
    }
}
