package app.vit.vitauth.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.vit.vitauth.R;
import data.Class;

public class ExamListAdapter extends ArrayAdapter<Class> {

    private final Context context;
    private final List<Class> classes;

    public ExamListAdapter(Context context, List<Class> classes) {

        super(context, R.layout.exam_listview_item, classes);

        this.context = context;
        this.classes = classes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exam_listview_item, parent, false);
        }

        TextView classNumberView = (TextView) convertView.findViewById(R.id.list_item_classNumber);
        TextView codeTitle = (TextView) convertView.findViewById(R.id.list_item_codeTitle);

        classNumberView.setText(classes.get(position).getClassNumber().toString());
        codeTitle.setText(classes.get(position).getCode() + " - " + classes.get(position).getTitle());

        return convertView;
    }
}
