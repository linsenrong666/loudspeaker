package com.linsr.loudspeaker.utils.log;

/**
 * Created by linsr on 14/12/29.
 *
 * @author Linsr
 */
public class LogImpl implements Log{

    private static final String THREAD_ID = "T[%d]: %s";
    private boolean mLogToLogCat = false;
    private static volatile LogImpl mInstance;

    public static Log getInstance() {

        if (mInstance == null) {
            synchronized (LogImpl.class) {
                if (mInstance == null) {
                    mInstance = new LogImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void setLogToLogCat(boolean logToLogCat) {
        mLogToLogCat = logToLogCat;
    }

    @Override
    public int d(String tag, Object msg) {
        if (mLogToLogCat) {
            return android.util.Log.d(tag, getFormattedMsg(msg));
        }
        return 0;
    }


    private String getFormattedMsg(Object msg) {
        long threadId = Thread.currentThread().getId();
        return String.format(THREAD_ID, threadId, msg);
    }

    private String getFormattedMsg(String msg, Object... params) {
        long threadId = Thread.currentThread().getId();
        String format = String.format(THREAD_ID, threadId, msg);
        return String.format(format, params);
    }

    @Override
    public int d(String tag, String msg, Object... params) {
        if (mLogToLogCat) {
            return android.util.Log.d(tag, getFormattedMsg(msg, params));
        }
        return 0;
    }

    @Override
    public int i(String tag, Object msg) {
        if (mLogToLogCat) {
            return android.util.Log.i(tag, getFormattedMsg(msg));
        }
        return 0;
    }

    @Override
    public int i(String tag, String msg, Object... params) {
        if (mLogToLogCat) {
            return android.util.Log.i(tag, getFormattedMsg(msg, params));
        }
        return 0;
    }

    @Override
    public int v(String tag, Object msg) {
        if (mLogToLogCat) {
            return android.util.Log.v(tag, getFormattedMsg(msg));
        }
        return 0;
    }


    @Override
    public int v(String tag, String msg, Object... params) {
        if (mLogToLogCat) {
            return android.util.Log.v(tag, getFormattedMsg(msg, params));
        }
        return 0;
    }

    @Override
    public int w(String tag, Object msg) {
        if (mLogToLogCat) {
            return android.util.Log.w(tag, getFormattedMsg(msg));
         }
        return 0;
    }


    @Override
    public int w(String tag, String msg, Object... params) {
        if (mLogToLogCat) {
            return android.util.Log.w(tag, getFormattedMsg(msg, params));
        }
        return 0;
    }

    @Override
    public int e(String tag, Object msg) {
        if(mLogToLogCat) {
            return android.util.Log.e(tag, getFormattedMsg(msg));
        }
        return 0;
    }

    @Override
    public int e(String tag, String msg, Object... params) {
        if (mLogToLogCat) {
            return android.util.Log.e(tag, getFormattedMsg(msg, params));
        }
        return 0;
    }

    @Override
    public int e(String tag, String msg, Throwable e) {
        if (mLogToLogCat) {
            long threadId = Thread.currentThread().getId();
            String formattedMsg = String.format(THREAD_ID, threadId, msg);
            return android.util.Log.e(tag, formattedMsg, e);
        }
        return 0;
    }
}
