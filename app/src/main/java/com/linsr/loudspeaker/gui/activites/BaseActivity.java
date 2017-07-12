package com.linsr.loudspeaker.gui.activites;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.linsr.loudspeaker.application.ApplicationEx;
import com.linsr.loudspeaker.gui.dialogs.DialogFactory;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Description
 *
 * @author linsenrong on 2017/7/11 16:15
 */

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    protected abstract int getContentViewId();

    protected abstract void initView();

    protected Context mContext;
    protected DialogFactory mDialogFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        init();
        initView();
    }

    protected void init(){
        mContext = getApplicationContext();
        mDialogFactory = DialogFactory.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public View getRootView() {
        return getWindow().getDecorView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 获取权限成功
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        Snackbar.make(getRootView(), "获取权限成功", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 获取权限失败
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        Snackbar.make(getRootView(), "获取权限失败", Snackbar.LENGTH_INDEFINITE).setAction("去设置",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationInfo().packageName, null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }).show();
    }

}
