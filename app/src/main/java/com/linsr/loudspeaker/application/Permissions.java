package com.linsr.loudspeaker.application;

import android.Manifest;

/**
 * Description
 *
 * @author linsenrong on 2016/10/13 18:54
 */

public interface Permissions {

    int REQUEST_STORAGE_MIC = 0;

    /**
     * 录音和存储权限
     */
    String[] PERMISSIONS_STORAGE_MIC = {Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    /**
     * 通讯录
     */
    String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS};
    /**
     * 日历
     */
    String[] PERMISSIONS_CALENDAR = {Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};
    /**
     * 摄像头
     */
    String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    /**
     * 地理位置
     */
    String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 存储空间
     */
    String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 存储空间和摄像头
     */
    String[] PERMISSIONS_CAMERA_AND_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    /**
     * 麦克风
     */
    String[] PERMISSIONS_MICROPHONE = {Manifest.permission.RECORD_AUDIO};
    /**
     * 身体传感器
     */
    String[] PERMISSIONS_SENSORS = {Manifest.permission.BODY_SENSORS};
    /**
     * 电话
     */
    String[] PERMISSIONS_PHONE = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
    };
    /**
     * 短信
     */
    String[] PERMISSIONS_SMS = {Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.ADD_VOICEMAIL
    };
}
