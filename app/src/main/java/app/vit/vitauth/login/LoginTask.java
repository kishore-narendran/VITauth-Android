package app.vit.vitauth.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import app.vit.corewise.utils.ToastUtil;
import app.vit.data.ExamInfo;
import app.vit.data.LoginInfo;
import app.vit.data.Result;
import app.vit.vitauth.MainApplication;
import app.vit.vitauth.R;
import app.vit.vitauth.bluetooth.BluetoothActivity;
import app.vit.vitauth.exam.ExamActivity;

public class LoginTask extends AsyncTask<LoginInfo, Void, Boolean> {

    private final String LOG_TAG = LoginTask.class.getSimpleName();

    private MainApplication application;
    private LoginFragment loginFragment;

    public LoginTask(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
        this.application = (MainApplication) this.loginFragment.getActivity().getApplicationContext();
    }

    @Override
    protected Boolean doInBackground(LoginInfo... params) {

        if (params.length == 0) {
            return null;
        }

        Gson gson = new Gson();

        final String VITAUTH_BASE_URL = "https://vitauth.herokuapp.com";
        final String VITAUTH_API_SUBURL = "api";
        final String VITAUTH_CLIENT_SUBURL = "client";
        final String VITAUTH_GETEXAMINFO_SUBURL = "getexaminfo";

        final String USER_AGENT = "Mozilla/5.0";

        LoginInfo ob = params[0];
        String json = gson.toJson(ob);
        byte[] postData = json.getBytes();
        int postDataLength = postData.length;

        HttpsURLConnection urlConnection = null;
        BufferedReader responseStreamReader = null;

        boolean loginResult;

        try {
            Uri builtUri = Uri.parse(VITAUTH_BASE_URL).buildUpon()
                    .appendPath(VITAUTH_API_SUBURL)
                    .appendPath(VITAUTH_CLIENT_SUBURL)
                    .appendPath(VITAUTH_GETEXAMINFO_SUBURL)
                    .build();
            Log.d(LOG_TAG, "VITauth Uri: " + builtUri.toString());

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(25000);
            urlConnection.setConnectTimeout(30000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            urlConnection.setUseCaches(false);

            OutputStream os = urlConnection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            writer.write(postData);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();

            InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());
            responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

            ExamInfo examInfo = gson.fromJson(responseStreamReader, ExamInfo.class);
            Result result = examInfo.getResult();

            if (result.getCode() == 1) {
                SharedPreferences sharedPreferences = loginFragment.getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
                Editor editor = sharedPreferences.edit();
                editor.putString("exam_info", gson.toJson(examInfo));
                editor.apply();
                loginResult = true;
            } else {
                loginResult = false;
            }


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error occured when attempting to upload data", e);
            loginResult = false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (responseStreamReader != null) {
                try {
                    responseStreamReader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return loginResult;
    }

    @Override
    protected void onPostExecute(Boolean loginResult) {
        if (loginResult) {
            loginFragment.cancelProgressDialog();

            Intent intent;
            if (!application.isConnect()) {
                intent = new Intent(loginFragment.getActivity(), BluetoothActivity.class);
            } else {
                intent = new Intent(loginFragment.getActivity(), ExamActivity.class);
            }
            loginFragment.startActivity(intent);
        } else {
            loginFragment.cancelProgressDialog();
            ToastUtil.showToast(loginFragment.getActivity(), R.string.login_invalid);
        }
    }

    @Override
    protected void onPreExecute() {
        loginFragment.showProgressDialog(R.string.login_progress_message);
    }
}
