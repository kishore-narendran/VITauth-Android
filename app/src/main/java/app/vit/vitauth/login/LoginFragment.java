package app.vit.vitauth.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import app.vit.vitauth.R;
import data.Credentials;

public class LoginFragment extends Fragment {

    private View rootView;
    private ProgressDialog progress;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button button = (Button) rootView.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText employeeIdEditText = (EditText) rootView.findViewById(R.id.employee_id);
                EditText passwordEditText = (EditText) rootView.findViewById(R.id.password);

                Credentials credentials = new Credentials(employeeIdEditText.getText().toString(), passwordEditText.getText().toString());
                login(credentials);
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

    private void login(Credentials credentials) {
        new LoginTask(this).execute(credentials);
    }
}
