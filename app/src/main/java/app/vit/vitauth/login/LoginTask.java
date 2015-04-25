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
import app.vit.data.GetExamInfo;

public class LoginTask extends AsyncTask<GetExamInfo, Void, String> {

    private LoginFragment loginFragment;
    private String examInfo;

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

            examInfo = stringBuilder.toString();
            return examInfo;
        }
       catch (Exception e) {
            Log.e("Error", e.getMessage());

        }
        finally {
            return examInfo;
        }
    }

    @Override
    protected void onPostExecute(String examInfo) {
        loginFragment.dismissProgress();
        Intent intent = new Intent(loginFragment.getActivity(), ExamActivity.class).putExtra("exam_info", examInfo);
        loginFragment.startActivity(intent);

    }

    @Override
    protected void onPreExecute() {
        loginFragment.showProgress();
    }
}
