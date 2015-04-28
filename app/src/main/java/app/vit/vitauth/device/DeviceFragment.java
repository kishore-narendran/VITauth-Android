package app.vit.vitauth.device;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.vit.corewise.asynctask.AsyncFingerprint;
import app.vit.corewise.asynctask.AsyncFingerprint.OnDownCharListener;
import app.vit.corewise.asynctask.AsyncFingerprint.OnGenCharListener;
import app.vit.corewise.asynctask.AsyncFingerprint.OnGetImageListener;
import app.vit.corewise.asynctask.AsyncFingerprint.OnMatchListener;
import app.vit.corewise.asynctask.AsyncM1Card;
import app.vit.corewise.asynctask.AsyncM1Card.OnReadAtPositionListener;
import app.vit.corewise.logic.M1CardAPI;
import app.vit.corewise.utils.DataUtils;
import app.vit.corewise.utils.ToastUtil;
import app.vit.data.ClassStudent;
import app.vit.vitauth.MainApplication;
import app.vit.vitauth.R;

public class DeviceFragment extends Fragment {

    private final String LOG_TAG = DeviceFragment.class.getSimpleName();

    private final String readPosition = "16";
    private final int readKeyType = M1CardAPI.KEY_A;

    private MainApplication application;

    private ClassStudent classStudent;

    private View rootView;
    private ProgressDialog progressDialog;
    private Button scanButton;

    private AsyncFingerprint scanFingerprint;
    private AsyncM1Card scanRfid;

    public DeviceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_device, container, false);

        initData();
        initDevice();
        initView();

        return rootView;
    }

    private void initData() {
        application = (MainApplication) getActivity().getApplicationContext();
        classStudent = null;
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
                    // TODO
                    // classStudent =
                    ToastUtil.showToast(getActivity(), R.string.rfid_read_success);
                }

            }

            @Override
            public void onReadAtPositionFail(int comfirmationCode) {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.rfid_read_fail);
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
                byte[] model = DataUtils.hexStringTobyte(classStudent.getFingerprint());
                scanFingerprint.PS_DownChar(model);
            }

            @Override
            public void onGenCharFail() {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.fingerprint_processing_fail);
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
            }
        });
        scanFingerprint.setOnMatchListener(new OnMatchListener() {
            @Override
            public void onMatchSuccess() {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.fingerprint_verify_success);
            }

            @Override
            public void onMatchFail() {
                cancelProgressDialog();
                ToastUtil.showToast(getActivity(), R.string.fingerprint_verify_fail);
            }
        });
    }

    private void initView() {
        // TODO scanButton = (Button) rootView.findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanRfid.read(Integer.parseInt(readPosition), readKeyType, null);
            }
        });
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
        char[] regNoCharArray = new char[10];
        for (int i = 0; rfidData.charAt(i) != '\0'; ++i) {
            regNoCharArray[i] = rfidData.charAt(i);
        }
        return new String(regNoCharArray);
    }

}
