package com.example.jinhyeok.whattoday;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //Toolbar Bind
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    //Layout Bind & Layout parameter Setting
    @BindView(R.id.alarmLayout)
    LinearLayout alarmLayout;
    LinearLayout.LayoutParams paramText = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

    //Button Bind
    @BindView(R.id.startAlarm)
    Button startAlarmButton;
    @BindView(R.id.stopAlarm)
    Button stopAlarmButton;

    //AlarmManger declaration
    public static AlarmManager mAlarmManger = null;
    public static PendingIntent mAlarmIntent = null;

    //Alarm Count
    int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Use Toolbar
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("하루 메인화면");


    }

    //추가된 소스, ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        Intent settingIntent = new Intent(this, SettingActivity.class);

        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                startActivity(settingIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    //알람 설정
    @OnClick(R.id.startAlarm)
    public void registerAlarm() {
        //알람 매너저 생성
        mAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // startAlarm 실행 Debug용 Toast
        Toast.makeText(this, "알람 시작", Toast.LENGTH_SHORT).show();
        // Device를 깨운 후 시스템 시간 기준 1초 후 부터 alarmIntent 실행 , 50초 단위로 반복 실행
        mAlarmManger.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1000,
                50000, alarmIntent());
    }

    //알람 해제
    @OnClick(R.id.stopAlarm)
    public void unregisterAlarm() {
        // startAlarm 실행 Debug용 Toast
        Toast.makeText(this, "알람 해제 버튼", Toast.LENGTH_SHORT).show();

        if (mAlarmIntent != null) {
            Toast.makeText(this, "알람 해제", Toast.LENGTH_SHORT).show();

            //알람 매너저 생성
            mAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            mAlarmManger.cancel(alarmIntent());
            mAlarmIntent.cancel();

            mAlarmManger = null;
            mAlarmIntent = null;
        }

    }


    private PendingIntent alarmIntent() {

        // alarmIntent 실행 Debug용 Toast
        Toast.makeText(this, "인텐트 실행", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, AlarmService_Service.class);

        intent.putExtra("data", "Test Popup");
        intent.putExtra("requestCode", 8820);
        mAlarmIntent = PendingIntent.getBroadcast(
                MainActivity.this, 8820, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return mAlarmIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 8820) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                TextView tv = new TextView(this);
                tv.setText(count + " : " + data.getStringExtra("result"));
                tv.setLayoutParams(paramText);
                alarmLayout.addView(tv);

                count++;
            }
        }


    }
}

//    //Toolbar 선언
//    Toolbar myToolbar;
//    private AlarmManager mAlarmManger;
//
//    long mNow;
//    Date mDate;
//    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//    int timeCount = 0;
//
//    String username;
//
//    Button mRefreshBtn;
//    Button textBtn;
//
//    LinearLayout layout;
//    LinearLayout textlayout;
//    LinearLayout.LayoutParams paramText = new LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        //Toolbar 생성
//        myToolbar = findViewById(R.id.my_toolbar);
//
//        //시간을 나타내는 버튼 view bind
//        mRefreshBtn = findViewById(R.id.timeRefreshButton);
//        layout = findViewById(R.id.timeLayout);
//        textlayout = findViewById(R.id.alarmLayout);
//
//
//        //bind listener
//        mRefreshBtn.setOnClickListener(this);
//        textBtn.setOnClickListener(this);
//
//        setSupportActionBar(myToolbar);
//
//        getSupportActionBar().setTitle("하루 메인화면");
//
////        getAlarm();
//
//    }
//
//    public void getAlarm() {
//        //알람 매너저 취득(?)
//        mAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        mAlarmManger.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 1000,
//                50000, alarmIntent());
//
//    }
//
//    //추가된 소스, ToolBar에 menu.xml을 인플레이트함
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //return super.onCreateOptionsMenu(menu);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//
//    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        //return super.onOptionsItemSelected(item);
//        Intent settingIntent = new Intent(this, SettingActivity.class);
//
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                // User chose the "Settings" item, show the app settings UI...
//                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
//                startActivity(settingIntent);
//                return true;
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.timeRefreshButton:
//                Toast.makeText(this, "Time Refresh", Toast.LENGTH_SHORT).show();
//                TextView tv = new TextView(this);
//                tv.setText(timeCount + " : " + getTime());
//                tv.setLayoutParams(paramText);
//                layout.addView(tv);
//
//                timeCount++;
//                break;
//
//            case R.id.textbutton:
//                Toast.makeText(this, "Textbutton", Toast.LENGTH_SHORT).show();
//                TextView ttv = new TextView(this);
//                ttv.setText("inputText : " + username);
//                ttv.setLayoutParams(paramText);
//                textlayout.addView(ttv);
//
//                break;
//
//            default:
//                break;
//
//        }
//    }
//
//    private String getTime() {
//        mNow = System.currentTimeMillis();
//        mDate = new Date(mNow);
//        return mFormat.format(mDate);
//    }
//
//    private PendingIntent alarmIntent() {
//        Toast.makeText(this, "인텐트 실행", Toast.LENGTH_SHORT).show();
//
////        Intent intent = new Intent(this, AlarmService_Service.class);
//        Intent intent = new Intent(this, TextPopupActivity.class);
//
//        intent.putExtra("data", "Test Popup");
//
////        intent.putExtra("currentTime", getTime());
////        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        return pi;
//    }
//
//    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        if(requestCode==0){
////            if(resultCode==RESULT_OK){
////                //데이터 받기
////                TextView tv = new TextView(this);
////                tv.setText(timeCount + " : " + getTime());
////                tv.setLayoutParams(paramText);
////                textlayout.addView(tv);
////
////                timeCount++;
////            }
////        }
//    }
