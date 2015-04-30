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

import app.vit.data.LoginInfo;
import app.vit.vitauth.R;

public class LoginFragment extends Fragment {

    private View rootView;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initView();

        return rootView;
    }

    private void initView() {
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
                LoginInfo obj = new LoginInfo(Integer.parseInt(employeeIdEditText.getText().toString()),
                        timeSpinner.getSelectedItem().toString(),
                        buildingSpinner.getSelectedItem().toString() +" "+ roomNumber.getText().toString(),
                        slotSpinner.getSelectedItem().toString(),
                        examinationTypeSpinner.getSelectedItem().toString(),
                        semesterSpinner.getSelectedItem().toString());
                login(obj);
            }
        });
    }

    void showProgressDialog(int resId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setMessage(getResources().getString(resId));
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void login(LoginInfo obj) {
         new LoginTask(this).execute(obj);
    }
}
