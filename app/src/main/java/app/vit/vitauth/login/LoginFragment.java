package app.vit.vitauth.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import app.vit.vitauth.R;
import app.vit.data.GetExamInfo;

public class LoginFragment extends Fragment {

    private View rootView;
    private ProgressDialog progress;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = (Button) rootView.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText employeeIdEditText = (EditText) rootView.findViewById(R.id.employeeID);
                Spinner buildingSpinner = (Spinner) rootView.findViewById(R.id.building);
                EditText roomNumber = (EditText) rootView.findViewById(R.id.roomNumber);
                Spinner examinationTypeSpinner = (Spinner) rootView.findViewById(R.id.examination);
                Spinner slotSpinner = (Spinner) rootView.findViewById(R.id.examinationSlot);
                Spinner timeSpinner = (Spinner) rootView.findViewById(R.id.examinationTime);
                Spinner semesterSpinner = (Spinner) rootView.findViewById(R.id.semester);
                GetExamInfo obj = new GetExamInfo(Integer.parseInt(employeeIdEditText.getText().toString()),
                                                                   timeSpinner.getSelectedItem().toString(),
                                                                   buildingSpinner.getSelectedItem().toString() +" "+ roomNumber.getText().toString(),
                                                                   slotSpinner.getSelectedItem().toString(),
                                                                   examinationTypeSpinner.getSelectedItem().toString(),
                                                                   semesterSpinner.getSelectedItem().toString());
                login(obj);

            }

        });
        return rootView;
    }

    public void showProgress() {
        progress = new ProgressDialog(getActivity());
        progress.setTitle(getString(R.string.login_progress_title));
        progress.setMessage(getString(R.string.login_progress_message));
        progress.show();
    }

    public void dismissProgress() {
        progress.dismiss();
    }

    private void login(GetExamInfo obj) {
         new LoginTask(this).execute(obj);
    }
}
