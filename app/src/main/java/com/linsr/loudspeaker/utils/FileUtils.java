package com.linsr.loudspeaker.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.linsr.loudspeaker.config.Constants;
import com.linsr.loudspeaker.utils.log.LogImpl;

import java.io.File;


/**
 * Created by Linsr on 2015/9/10.
 *
 * @author Linsr
 */
public class FileUtils {

    private static final String LOG_TAG = "FileUtils";
    private static final String CACHE_PATH = "cache";
    private static final String FILE_PATH = "files";

    /**
     * 判断sdcard是否挂载
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * get storage space path
     */
    public static String getWorkingDirectory(Context context) {
        String res;
        if (isSDCardMounted()) {
            res = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constants.FILE_DIR_NAME;
        } else {
            res = context.getFilesDir().getAbsolutePath() + "/";
        }
        if (!res.endsWith("/")) {
            res += "/";
        }
        File f = new File(res);
        if (!f.exists()) {
            boolean success = f.mkdirs();
            if (!success) {
                LogImpl.getInstance().e(LOG_TAG, "create file failed");
            }
        }
        return res;
    }

    public static boolean rename(String dir, String oldName, String newName) {
        File from = new File(dir, oldName);
        File to = new File(dir, newName);
        return from.renameTo(to);
    }

    public static boolean rename(File oldFile, String newName) {
        return rename(oldFile.getParent(),oldFile.getName(),newName);
    }

    public static String getImageCachePath(Context context) {
        return createImagePath(context, CACHE_PATH);
    }

    public static String getFilesPath(Context context) {
        return createImagePath(context, FILE_PATH);
    }

    private static String createImagePath(Context context, String name) {
        File f = new File(getWorkingDirectory(context) + "/" + name + "/");
        if (!f.exists()) {
            boolean success = f.mkdirs();
            if (!success) {
                LogImpl.getInstance().e(LOG_TAG, "create %s file failed", name);
                ToastUtils.toast(context, "可能是没有开启存储权限，请开启权限后再试", Toast.LENGTH_LONG);
            }
        }
        String res = f.getPath();
        if (!res.endsWith("/")) {
            res += "/";
        }
        return res;
    }

    /**
     * clear files
     *
     * @param directory directory
     */
    public static void clearDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                boolean success = item.delete();
                if (!success) {
                    LogImpl.getInstance().e(LOG_TAG, "file delete failed");
                }
            }
        }
    }

    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File f = new File(path);
        if (f.exists()) {
            return f.delete();
        } else {
            LogImpl.getInstance().e(LOG_TAG, "delete file failed, file path = %s ", path);
            return false;
        }
    }

}
