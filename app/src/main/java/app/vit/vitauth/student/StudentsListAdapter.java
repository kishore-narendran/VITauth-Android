package app.vit.vitauth.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.vit.vitauth.R;
import data.ListItemStudent;

/**
 * Created by kishore on 5/4/15.
 */
public class StudentsListAdapter extends ArrayAdapter<ListItemStudent> {

    private final Context context;
    private final ArrayList<ListItemStudent> itemsArrayList;

    public StudentsListAdapter(Context context, ArrayList<ListItemStudent> itemsArrayList) {

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
