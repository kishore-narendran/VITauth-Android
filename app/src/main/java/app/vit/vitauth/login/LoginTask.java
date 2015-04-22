package app.vit.vitauth.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import app.vit.vitauth.exam.ExamActivity;
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
            final String VITAUTH_BASE_URL = "http://vitauth.herokuapp.com/api/client/GetExamInfo";
            byte[] postData = json.getBytes();
            int postDataLength = postData.length;
            URL url = new URL(VITAUTH_BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            urlConnection.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                wr.write(postData);
                wr.flush();
                wr.close();
            }

            InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            responseStreamReader.close();

            String response = stringBuilder.toString();
            Log.i("Response", response);
            gson = new Gson();
            ExamInfo examInfo1 = gson.fromJson(response, ExamInfo.class);
            return examInfo1;
        }
       catch (Exception e) {
            Log.e("Error", e.getMessage());

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
