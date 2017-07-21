package com.linsr.loudspeaker.net;

import com.linsr.loudspeaker.utils.log.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description
 *
 * @author linsenrong on 2017/7/20 17:42
 */

public class NetReqManager {

    //服务器路径
    private static final String HOST = "http://10.12.128.226:8090";

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    private static volatile NetReqManager mInstance;

    private NetReqManager() {
    }

    public static NetReqManager getInstance() {
        if (mInstance == null) {
            synchronized (NetReqManager.class) {
                if (mInstance == null) {
                    mInstance = new NetReqManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Log log) {
    }

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    public Retrofit getRetrofit() {

        if (null == mRetrofit) {

            if (null == mOkHttpClient) {
                mOkHttpClient = getOkHttpClient();
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(HOST + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }

        return mRetrofit;
    }

    OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }

}
