package com.example.jinhyeok.whattoday;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmService_Service extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent receiveIntent = new Intent(context, TimeLogActivity.class);

        Log.d("서비스","작동하니?");
        receiveIntent.putExtra("currentTime", intent.getStringExtra("currentTime"));

        Log.d("시간", intent.getStringExtra("currentTime"));
        PendingIntent sender = PendingIntent.getActivity(context, 0, receiveIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            sender.send();
            Toast.makeText(context, "센드!", Toast.LENGTH_SHORT);
            Log.d("시간", "보내짐!");
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
