package app.vit.vitauth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.vit.data.Student;

public class StudentListAdapter extends ArrayAdapter<Student> {

    private final Context context;
    private final List<Student> students;

    public StudentListAdapter(Context context, List<Student> students) {

        super(context, R.layout.student_list_item, students);

        this.context = context;
        this.students = students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_list_item, parent, false);
        }

        TextView regnoView = (TextView) convertView.findViewById(R.id.list_item_regno);
        TextView nameView = (TextView) convertView.findViewById(R.id.list_item_name);

        regnoView.setText(students.get(position).getRegisterNumber());
        nameView.setText(students.get(position).getName());

        return convertView;
    }
}
