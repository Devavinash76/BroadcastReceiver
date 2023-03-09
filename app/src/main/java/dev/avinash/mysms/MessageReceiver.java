package dev.avinash.mysms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import dev.avinash.mysms.Retro.GetData;
import dev.avinash.mysms.Retro.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageReceiver extends BroadcastReceiver{
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        Object[] smsobj = (Object[]) bundle.get("pdus");

        for (Object obj : smsobj){
            SmsMessage message = SmsMessage.createFromPdu((byte[]) obj);

            String mobNo = message.getDisplayOriginatingAddress();
            String msg = message.getDisplayMessageBody();

            Log.d("MsgDetails", "MobNo"+ mobNo + ", Msg: "+ msg);
            Toast.makeText(context,mobNo+","+msg, Toast.LENGTH_LONG).show();
            Read();
        }

    }

        private void Read() {
        GetData getDataServices= RetrofitInstance.getRetrofitInstance().create(GetData.class);
        Call<JsonObject> call=getDataServices.login("Login"," ","","");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject  = new JSONObject(new Gson().toJson(response.body()));

                    String status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("true")) {

                        Log.d("MsgDetails", "Success");
//                        Toast.makeText(context, "success", Toast.LENGTH_LONG).show();

                    }
                    else {
//                        Toast.makeText(context, "failed", Toast.LENGTH_LONG).show();
                        Log.d("MsgDetails", "Failed");
//                        Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();


                    }
                }catch(Exception e){
                    Log.d("MsgDetails", "Exception");
//                    Toast.makeText(context,"Exception", Toast.LENGTH_LONG).show();
//                    No_network.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("MsgDetails", "Check Internet Service");
//                Toast.makeText(context, "Check Internet Service", Toast.LENGTH_LONG).show();
//                No_network.setVisibility(View.VISIBLE);
            }
        });
    }

}
