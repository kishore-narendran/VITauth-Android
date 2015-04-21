package app.vit.vitauth.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

import app.vit.vitauth.exam.ExamActivity;
import data.Class;
import data.ExamInfo;
import data.GetExamInfo;

public class LoginTask extends AsyncTask<GetExamInfo, Void, ExamInfo> {

    private LoginFragment loginFragment;
    private ExamInfo examInfo;

    public LoginTask(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @Override
    protected ExamInfo doInBackground(GetExamInfo... params) {

        if (params.length == 0) {
            return null;
        }
        GetExamInfo ob = params[0];
        Gson gson = new Gson();
        String json = gson.toJson(ob);
        Log.i("JSON STRING", json);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            final String VITAUTH_BASE_URL = "http://vitauth.herokuapp.com/api/client/login";
            final String EMPID_PARAM = "empid";
            final String PASSWORD_PARAM = "password";

            // TODO get and parse JSON

            examInfo = new ExamInfo("WS", "CATI", "A1", "SJT301", "4:00PM", new Class[5]);
        }
        catch (Exception e) {

        }
        finally {
            return examInfo;
        }
    }

    @Override
    protected void onPostExecute(ExamInfo examInfo) {
        loginFragment.dismissProgress();
        Intent intent = new Intent(loginFragment.getActivity(), ExamActivity.class);
        loginFragment.startActivity(intent);
    }

    @Override
    protected void onPreExecute() {
        loginFragment.showProgress();
    }
}
