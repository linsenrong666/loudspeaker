package com.linsr.loudspeaker.gui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.linsr.loudspeaker.R;
import com.linsr.loudspeaker.application.Permissions;
import com.linsr.loudspeaker.gui.adapters.RecordsAdapter;
import com.linsr.loudspeaker.model.RecordModel;
import com.linsr.loudspeaker.net.NetReqManager;
import com.linsr.loudspeaker.net.SpeechService;
import com.linsr.loudspeaker.utils.AudioRecorderUtils;
import com.linsr.loudspeaker.utils.PopupWindowFactory;
import com.linsr.loudspeaker.utils.TimeUtils;
import com.linsr.loudspeaker.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements Permissions {

    @BindView(R.id.main_begin_btn)
    Button mButton;
    @BindView(R.id.main_content_rl)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.main_rv)
    EasyRecyclerView mRecyclerView;

    private ImageView mImageView;
    private TextView mTextView;
    private AudioRecorderUtils mAudioRecorderUtils;
    private PopupWindowFactory mPop;

    private List<RecordModel> mList;
    private RecordsAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    private MediaPlayer musicPlayer;

    SpeechService speechService;

    @Override
    protected void initView() {
        final View view = View.inflate(this, R.layout.layout_microphone, null);

        mPop = new PopupWindowFactory(this, view);

        //PopupWindow布局文件里面的控件
        mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        mTextView = (TextView) view.findViewById(R.id.tv_recording_time);

        mAudioRecorderUtils = new AudioRecorderUtils(this);

        //录音回调
        mAudioRecorderUtils.setOnAudioStatusUpdateListener(mOnAudioStatusUpdateListener);

        mList = new ArrayList<>();
        mAdapter = new RecordsAdapter(getApplicationContext(), mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                startActivity(new Intent(mContext, PlayRecordActivity.class));
//                File musicFile = new File(mList.get(position).getPath());
//                //判断文件是否存在
//                if (musicFile.exists()) {
//                    musicPlayer = MediaPlayer.create(mContext, Uri.parse(musicFile.getAbsolutePath()));
//                    musicPlayer.start();
//                } else {
//                    ToastUtils.toast(mContext, R.string.toast_file_not_exists);
//                }

//                //监听音频播放完的代码，实现音频的自动循环播放
//                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                    @Override
//                    public void onCompletion(MediaPlayer arg0) {
//                        musicPlayer.start();
//                        musicPlayer.setLooping(true);
//                    }
//                });
            }
        });

        getData();

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口高度
        screenHeight = dm.heightPixels;
        speechService = NetReqManager.getInstance().getRetrofit().create(SpeechService.class);

        //6.0以上需要权限申请
        requestPermissions();
    }

    int screenHeight;

    private void getData() {
        mAdapter.clear();
        File file = new File(AudioRecorderUtils.getRecordsPath(mContext));
        File files[] = file.listFiles();
        if (files != null) {
            for (File f : files) {
                RecordModel model = new RecordModel();
                model.setRecordName(f.getName());
                model.setPath(f.getPath());
                mAdapter.add(model);
            }
        }
        Collections.reverse(mList);
    }


    private AudioRecorderUtils.OnAudioStatusUpdateListener mOnAudioStatusUpdateListener =
            new AudioRecorderUtils.OnAudioStatusUpdateListener() {

                //录音中....db为声音分贝，time为录音时长
                @Override
                public void onUpdate(double db, long time) {
                    mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                    mTextView.setText(TimeUtils.long2String(time));
                }

                //录音结束，filePath为保存路径
                @Override
                public void onStop(String filePath) {
                    Toast.makeText(MainActivity.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();

                    RequestBody rate = RequestBody.create(MediaType.parse("multipart/form-data"), "8000");

                    File file = new File(filePath);
                    // 创建 RequestBody，用于封装构建RequestBody
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part  和后端约定好Key，这里的partName是用image
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    Call<ResponseBody> responseBodyCall = speechService.speechRecognition(rate, body);
                    responseBodyCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            logInfo("onResponse");
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            logErr("onFailure");
                        }
                    });

                    mTextView.setText(TimeUtils.long2String(0));
                }
            };

    @AfterPermissionGranted(REQUEST_STORAGE_MIC)
    protected void requestPermissions() {
        if (EasyPermissions.hasPermissions(this, PERMISSIONS_STORAGE_MIC)) {
            startListener();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tips_request_permissions),
                    REQUEST_STORAGE_MIC, PERMISSIONS_STORAGE_MIC);
        }
    }

    private boolean mIsSave;

    public void startListener() {
        //Button的touch监听
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!EasyPermissions.hasPermissions(mContext, PERMISSIONS_STORAGE_MIC)) {
                            EasyPermissions.requestPermissions(this, getString(R.string.tips_request_permissions),
                                    REQUEST_STORAGE_MIC, PERMISSIONS_STORAGE_MIC);
                            break;
                        }
                        mPop.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
                        mButton.setText("松开保存");
                        mAudioRecorderUtils.startRecord();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if (event.getRawY() < 2 * screenHeight / 3) {
                            mButton.setText("松开取消保存");
                            mIsSave = false;

                        } else {
                            mIsSave = true;
                            mButton.setText("松开保存");
                        }
//                        logErr("getY = %s , sHeight = %s ", event.getRawY(), 2 * screenHeight / 3);
                        break;
                    case MotionEvent.ACTION_UP:
                        mPop.dismiss();
                        mButton.setText("按住说话");
                        if (mIsSave) {
                            mAudioRecorderUtils.stopRecord("aaa");
//                            showDialog();
                        } else {
                            mAudioRecorderUtils.cancelRecord();    //取消录音（不保存录音文件）
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void showDialog() {
        final EditText editText = new EditText(this);
        Dialog dialog = mDialogFactory.createSimpleDialogBuilder(this)
                .setView(editText)
                .setTitle(R.string.dialog_create_name)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editText.getText().toString().trim();
                        if (!TextUtils.isEmpty(name)) {
                            dialogInterface.dismiss();
                            mAudioRecorderUtils.stopRecord(name);
                            getData();
                        } else {
                            ToastUtils.toast(mContext, "名称不能为空");
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("不保存", null)
                .create();
        mDialogFactory.showDialog(dialog);
    }


}
