package app.vit.vitauth.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.vit.data.ClassStudent;
import app.vit.vitauth.R;

/**
 * Created by kishore on 5/4/15.
 */
public class StudentsListAdapter extends ArrayAdapter<ClassStudent> {

    private final Context context;
    private final ArrayList<ClassStudent> itemsArrayList;

    public StudentsListAdapter(Context context, ArrayList<ClassStudent> itemsArrayList) {

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

        regnoView.setText(itemsArrayList.get(position).getRegister_number());
        nameView.setText(itemsArrayList.get(position).getName());

        return rowView;
    }
}
