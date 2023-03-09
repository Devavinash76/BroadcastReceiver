package dev.avinash.mysms.Retro;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface GetData {

    @POST("NewApi.php")
    @FormUrlEncoded
    Call<JsonObject>login(
            @Field("flag") String flag,
            @Field("mobile") String mobile,
            @Field("sender") String sender,
            @Field("message") String message
    );

}
