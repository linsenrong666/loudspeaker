package com.linsr.loudspeaker.net;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Description
 *
 * @author linsenrong on 2017/7/20 17:57
 */

public interface SpeechService {

    @POST("/experience/speech/recognition")
    @Multipart
    Call<ResponseBody> speechRecognition(@Part("rate") RequestBody description,
                                         @Part MultipartBody.Part file);
}
