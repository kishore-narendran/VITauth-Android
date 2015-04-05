package app.vit.vitauth.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import app.vit.vitauth.R;

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

    public class ListStudentsAdapter extends ArrayAdapter<ListItemStudent> {

        private final Context context;
        private final ArrayList<ListItemStudent> itemsArrayList;

        public ListStudentsAdapter(Context context, ArrayList<ListItemStudent> itemsArrayList) {

            super(context, R.layout.list_item_students, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.list_item_students, parent, false);

            TextView regnoView = (TextView) rowView.findViewById(R.id.list_item_regno);
            TextView nameView = (TextView) rowView.findViewById(R.id.list_item_name);

            regnoView.setText(itemsArrayList.get(position).getRegno());
            nameView.setText(itemsArrayList.get(position).getName());

            return rowView;
        }
    }

    public class ListItemStudent {

        String regno;
        String name;

        public ListItemStudent(String regno, String name) {
            this.regno = regno;
            this.name = name;
        }

        public String getRegno() {
            return regno;
        }

        public String getName() {
            return name;
        }

    }
}
