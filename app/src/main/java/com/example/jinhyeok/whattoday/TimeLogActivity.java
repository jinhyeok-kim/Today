package com.example.jinhyeok.whattoday;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TimeLogActivity extends AppCompatActivity {

    LinearLayout layout;
    LinearLayout.LayoutParams paramText = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
    int timeCount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("되니ㅠ","돼줘ㅠㅠ");
        layout = findViewById(R.id.alarmLayout);

        Toast.makeText(this,"로그 클래스 실행", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();

        String time = intent.getExtras().getString("currentTime");

        TextView tv = new TextView(this);
        tv.setText(timeCount + " : " + time);
        tv.setLayoutParams(paramText);
        layout.addView(tv);

        timeCount++;

    }
}
