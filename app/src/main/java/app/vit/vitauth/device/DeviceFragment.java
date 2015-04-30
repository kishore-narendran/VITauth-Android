package app.vit.vitauth.device;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import app.vit.corewise.asynctask.AsyncFingerprint;
import app.vit.corewise.asynctask.AsyncFingerprint.OnDownCharListener;
import app.vit.corewise.asynctask.AsyncFingerprint.OnGenCharListener;
import app.vit.corewise.asynctask.AsyncFingerprint.OnGetImageListener;
import app.vit.corewise.asynctask.AsyncFingerprint.OnMatchListener;
import app.vit.corewise.asynctask.AsyncM1Card;
import app.vit.corewise.asynctask.AsyncM1Card.OnReadAtPositionListener;
import app.vit.corewise.logic.M1CardAPI;
import app.vit.corewise.utils.ToastUtil;
import app.vit.data.ExamInfo;
import app.vit.data.Student;
import app.vit.vitauth.MainApplication;
import app.vit.vitauth.R;
import app.vit.vitauth.StudentListAdapter;

public class DeviceFragment extends Fragment {

    private final String LOG_TAG = DeviceFragment.class.getSimpleName();

    private final String readPosition = "16";
    private final int readKeyType = M1CardAPI.KEY_A;

    private final String intentExtraClassNumber = "class_number";
    private final String intentExtraListPosition = "list_position";

    private final int defaultClassNumber = 1000;
    private final int defaultListPosition = 0;

    private MainApplication application;

    private View rootView;
    private ProgressDialog progressDialog;
    private Button scanButton;

    private StudentListAdapter studentListAdapter;

    private int classNumber;
    private int examListPosition;
    private int classListPosition;

    private ExamInfo examInfo;
    private Student student;

    private AsyncFingerprint scanFingerprint;
    private AsyncM1Card scanRfid;

    public DeviceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_device, container, false);
        setHasOptionsMenu(true);

        initData();
        initDevice();
        initView();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_device, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_upload:
                uploadData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        Gson gson = new Gson();

        application = (MainApplication) getActivity().getApplicationContext();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        String examInfoStr = sharedPreferences.getString("exam_info", null);

        examInfo = gson.fromJson(examInfoStr, ExamInfo.class);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(intentExtraClassNumber) && intent.hasExtra(intentExtraListPosition)) {
            classNumber = intent.getIntExtra(intentExtraClassNumber, defaultClassNumber);
            examListPosition = intent.getIntExtra(intentExtraListPosition, defaultListPosition);
        }

        student = null;
        classListPosition = -1;
    }

    private void initDevice() {
        scanRfid = new AsyncM1Card(application.getHandlerThread().getLooper(), application.getChatService());
        scanFingerprint = new AsyncFingerprint(application.getHandlerThread().getLooper(), application.getChatService());

        scanRfid.setOnReadAtPositionListener(new OnReadAtPositionListener() {

            @Override
            public void onReadAtPositionSuccess(String num, byte[] data) {
                cancelProgressDialog();
                if (data != null && data.length != 0) {
                    String regNo = parseRfidData(data);
                    Log.v(LOG_TAG, "RFID RegNo read: " + regNo);
                    Student[] students = examInfo.getClasses()[examListPosition].getStudents();

                    boolean foundStudent = false;
                    for (int i = 0; i < students.length; ++i) {
                        if (regNo.equals(students[i].getRegisterNumber())) {
                            student = students[i];
                            classListPosition = i;
                            foundStudent = true;
                            break;
                        }
                    }

                    if (foundStudent) {
                        ToastUtil.showToast(getActivity(), R.string.rfid_student_found);
                        showProgressDialog(R.string.press_finger);
                        scanFingerprint.PS_GetImage();
                    } else {
                        ToastUtil.showToast(getActivity(), R.string.rfid_student_notfound);
                    }
                }

            }

            @Override
            public void onReadAtPositionFail(int comfirmationCode) {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.rfid_read_fail);

                student = null;
                classListPosition = -1;
            }
        });

        scanFingerprint.setOnGetImageListener(new OnGetImageListener() {
            @Override
            public void onGetImageSuccess() {
                cancelProgressDialog();
                showProgressDialog(R.string.fingerprint_processing);
                scanFingerprint.PS_GenChar(1);
            }

            @Override
            public void onGetImageFail() {
                scanFingerprint.PS_GetImage();
            }
        });
        scanFingerprint.setOnGenCharListener(new OnGenCharListener() {
            @Override
            public void onGenCharSuccess(int bufferId) {
                byte[] model = Base64.decode(student.getFingerprint(), Base64.DEFAULT);
                scanFingerprint.PS_DownChar(model);
            }

            @Override
            public void onGenCharFail() {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.fingerprint_processing_fail);

                student = null;
                classListPosition = -1;
            }
        });
        scanFingerprint.setOnDownCharListener(new OnDownCharListener() {
            @Override
            public void onDownCharSuccess() {
                scanFingerprint.PS_Match();
            }

            @Override
            public void onDownCharFail() {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.fingerprint_processing_fail);

                student = null;
                classListPosition = -1;
            }
        });
        scanFingerprint.setOnMatchListener(new OnMatchListener() {
            @Override
            public void onMatchSuccess() {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.fingerprint_verify_success);

                student.setAttendance(true);
                examInfo.getClasses()[examListPosition].getStudents()[classListPosition] = student;

                updateData();
                updateView();
            }

            @Override
            public void onMatchFail() {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.fingerprint_verify_fail);

                student = null;
                classListPosition = -1;
            }
        });
    }

    private void initView() {
        Student[] students = examInfo.getClasses()[examListPosition].getStudents();
        ArrayList<Student> studentsArrayList = new ArrayList<>(Arrays.asList(students));

        for (int i = 0; i < studentsArrayList.size(); ++i) {
            if (!studentsArrayList.get(i).isAttendance()) {
                studentsArrayList.remove(i);
            }
        }

        studentListAdapter = new StudentListAdapter(getActivity(), studentsArrayList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
        listView.setAdapter(studentListAdapter);

        scanButton = (Button) rootView.findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(R.string.rfid_reading);
                scanRfid.read(Integer.parseInt(readPosition), readKeyType, null);
            }
        });
    }

    private void updateData() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("exam_info", gson.toJson(examInfo));
        editor.apply();

        student = null;
        classListPosition = -1;
    }

    private void updateView() {
        studentListAdapter.clear();

        Student[] students = examInfo.getClasses()[examListPosition].getStudents();
        ArrayList<Student> studentsArrayList = new ArrayList<>(Arrays.asList(students));

        for (int i = 0; i < studentsArrayList.size(); ++i) {
            if (!studentsArrayList.get(i).isAttendance()) {
                studentsArrayList.remove(i);
            }
        }

        studentListAdapter.addAll(studentsArrayList);
    }

    void showProgressDialog(int resId) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(resId));
        progressDialog.show();
    }

    void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
            progressDialog = null;
        }
    }

    private String parseRfidData(byte[] data) {
        String rfidData = new String(data);
        char[] regNoCharArray = new char[9];
        for (int i = 0; rfidData.charAt(i) != '\0'; ++i) {
            if (i <= 9) {
                regNoCharArray[i] = rfidData.charAt(i);
            }
            else {
                break;
            }
        }
        return new String(regNoCharArray);
    }

    private void uploadData() {
        // TODO Upload Execute
    }

}
