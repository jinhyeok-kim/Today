package com.example.jinhyeok.whattoday;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity{
    //시간 설정을 위한 객체
    Calendar Time;

    //알람 설정을 위한 객체
    private Intent intent;
    private PendingIntent ServicePending;
    private AlarmManager alarmManager;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 mm분 ss초");

    TextView textView;

    DatePickerDialog.OnDateSetListener eDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Time.set(Calendar.YEAR, year);
            Time.set(Calendar.MONTH, month);
            Time.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        //타임 피커, 데이트 피커 리스너 및 아이디 등록
        Time = Calendar.getInstance();

        //알람 설정, 해제 버튼
        Button.OnClickListener bClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                }
            }
        };
    }
}
