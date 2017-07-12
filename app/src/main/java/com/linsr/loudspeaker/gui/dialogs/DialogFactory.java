package com.linsr.loudspeaker.gui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;


/**
 * Created by Linsr on 2015/8/25.
 * 对话框工厂，所有对话框都在此创建
 *
 * @author Linsr
 */
public class DialogFactory {



    private static volatile DialogFactory mInstance;

    private DialogFactory() {
    }

    public static DialogFactory getInstance() {

        if (mInstance == null) {
            synchronized (DialogFactory.class) {
                if (mInstance == null) {
                    mInstance = new DialogFactory();
                }
            }
        }
        return mInstance;

    }

    public void dismissDialog(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(Dialog dialog) {
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}