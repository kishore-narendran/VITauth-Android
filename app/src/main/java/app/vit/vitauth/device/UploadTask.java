package app.vit.vitauth.device;

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
import app.vit.data.Result;
import app.vit.vitauth.R;

public class UploadTask extends AsyncTask<ExamInfo, Void, Boolean> {

    private final String LOG_TAG = UploadTask.class.getSimpleName();

    private DeviceFragment deviceFragment;

    public UploadTask(DeviceFragment deviceFragment) {
        this.deviceFragment = deviceFragment;
    }

    @Override
    protected Boolean doInBackground(ExamInfo... params) {

        if (params.length == 0) {
            return null;
        }

        Gson gson = new Gson();

        final String VITAUTH_BASE_URL = "https://vitauth.herokuapp.com";
        final String VITAUTH_API_SUBURL = "api";
        final String VITAUTH_CLIENT_SUBURL = "client";
        final String VITAUTH_SUBMITEXAMINFO_SUBURL = "submitExamReport";

        final String USER_AGENT = "Mozilla/5.0";

        ExamInfo ob = params[0];
        String json = gson.toJson(ob);
        byte[] postData = json.getBytes();
        int postDataLength = postData.length;

        HttpsURLConnection urlConnection = null;
        BufferedReader responseStreamReader = null;

        boolean uploadResult;

        try {
            Uri builtUri = Uri.parse(VITAUTH_BASE_URL).buildUpon()
                    .appendPath(VITAUTH_API_SUBURL)
                    .appendPath(VITAUTH_CLIENT_SUBURL)
                    .appendPath(VITAUTH_SUBMITEXAMINFO_SUBURL)
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

            uploadResult = result.getCode() == 1;

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error occured when attempting to upload data", e);
            uploadResult = false;
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
        return uploadResult;
    }

    @Override
    protected void onPostExecute(Boolean uploadResult) {
        if (uploadResult) {
            deviceFragment.cancelProgressDialog();
            ToastUtil.showToast(deviceFragment.getActivity(), R.string.upload_success);
        } else {
            deviceFragment.cancelProgressDialog();
            ToastUtil.showToast(deviceFragment.getActivity(), R.string.upload_fail);
        }
    }

    @Override
    protected void onPreExecute() {
        deviceFragment.showProgressDialog(R.string.upload_progress_message);
    }

}
