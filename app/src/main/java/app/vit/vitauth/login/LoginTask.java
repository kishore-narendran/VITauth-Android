package app.vit.vitauth.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import app.vit.data.ExamInfo;
import app.vit.data.GetExamInfo;
import app.vit.data.Result;
import app.vit.vitauth.exam.ExamActivity;

public class LoginTask extends AsyncTask<GetExamInfo, Void, String> {

    private LoginFragment loginFragment;
    private String examInfoResponse;
    private ExamInfo examInfo;
    private Result result;

    public LoginTask(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @Override
    protected String doInBackground(GetExamInfo... params) {

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
            try {
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.write(postData);
                wr.flush();
                wr.close();
            }
            catch(Exception e)
            {
                Log.i("Error", e.getMessage());
            }

            InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            responseStreamReader.close();

            examInfoResponse = stringBuilder.toString();
            return examInfoResponse;
        }
       catch (Exception e) {
            Log.e("Error", e.getMessage());

        }
        finally {
            return examInfoResponse;
        }
    }

    @Override
    protected void onPostExecute(String examInfoResponse) {
        Gson gson = new Gson();
        examInfo = gson.fromJson(examInfoResponse, ExamInfo.class);
        result = examInfo.getResult();
        if(result.getCode() == 0) {
            loginFragment.dismissProgress();
            Toast.makeText(loginFragment.getActivity(), "Invalid Credentials!", Toast.LENGTH_LONG).show();
        }
        else {
            SharedPreferences sharedPreferences = loginFragment.getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
            Editor editor = sharedPreferences.edit();
            editor.putString("exam_info", examInfoResponse);
            editor.commit();
            loginFragment.dismissProgress();
            Intent intent = new Intent(loginFragment.getActivity(), ExamActivity.class).putExtra("exam_info", examInfoResponse);
            loginFragment.startActivity(intent);
        }
    }

    @Override
    protected void onPreExecute() {
        loginFragment.showProgress();
    }
}
