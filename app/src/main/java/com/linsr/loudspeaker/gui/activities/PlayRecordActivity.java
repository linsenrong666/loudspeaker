package com.linsr.loudspeaker.gui.activities;

import android.media.MediaRecorder;

import com.linsr.loudspeaker.R;

/**
 * Description
 *
 * @author linsenrong on 2017/7/17 15:03
 */

public class PlayRecordActivity extends BaseActivity {

    MediaRecorder mMediaRecorder;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_play_record;
    }

    @Override
    protected void initView() {

    }
}
