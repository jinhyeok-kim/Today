package com.example.jinhyeok.whattoday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {

    //Toolbar Bind
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

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

    //DB
    private DBHelper dbHelper;

    String dbName = "time_content.db";

    //DB View
    @BindView(R.id.lvWorks)
    ListView lvWork;


    //Setting
    SharedPreferences sharedPref;

    //HorizontalCalendar
    private HorizontalCalendar horizontalCalendar;
    private String selectedDateStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        //Use Toolbar
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(formatter.format(System.currentTimeMillis()));

        //Setting
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //DB creation
        dbHelper = new DBHelper(
                MainActivity.this, //현재 화면의 제어권자
                dbName, //데이터베이스 이름
                null, //커서팩토리 - null 이면 표준 커서 사용
                1); //데이터베이스 버전

        dbHelper.testDB();



        /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);

        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);


        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();

        dayOfData(DateFormat.format("yyyy-MM-dd", defaultSelectedDate).toString());

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .addEvents(new CalendarEventsPredicate() {

                    Random rnd = new Random();
                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();
                        int count = rnd.nextInt(6);

                        for (int i = 0; i <= count; i++){
                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                        }

                        return events;
                    }
                })
                .build();


        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString());

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
//                selectedDateStr = DateFormat.format("EEE, MMM d, yyyy", date).toString();
                selectedDateStr = DateFormat.format("yyyy-MM-dd", date).toString();
                dayOfData(selectedDateStr);
                Toast.makeText(MainActivity.this, selectedDateStr + " selected!", Toast.LENGTH_SHORT).show();
                Log.i("onDateSelected", selectedDateStr + " - Position = " + position);
            }

        });

//        FloatingActionButton fab = findViewById(R.id.fab);


        registerAlarm();
//        AlarmAvailabeCheck();

    }

    //ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
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
            case R.id.today:
                Toast.makeText(getApplicationContext(), "오늘로 돌아감", Toast.LENGTH_LONG).show();
                horizontalCalendar.goToday(false);

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
                settingalarmIntervalTime(), alarmIntent());

        //realTime으로 수정 완료
//        mAlarmManger.setRepeating(AlarmManager.RTC_WAKEUP,
//                alarmTriggerTime(),
//                settingalarmIntervalTime(), alarmIntent());
    }


    //알람을 실행하는 시간 설정 함수
    private long alarmTriggerTime() {
        Calendar calendar = Calendar.getInstance();

        // 시간 단위
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        int timeFormat = Integer.parseInt(formatter.format(System.currentTimeMillis()));
        int alarmHour = timeFormat/100;
        int alarmMinute = timeFormat%100;

        // 1시간 단위 알람
//        alarmHour = alarmHour+1;
//        alarmMinute = 0;

        //30분 단위로 알람을 울릴 경우
        if(timeFormat%100 >= 30){
            alarmHour = alarmHour+1;
            alarmMinute = 0;
        }else{
            alarmMinute = 30;
        }

        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
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
        mAlarmIntent = PendingIntent.getBroadcast(
                MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return mAlarmIntent;
    }

    public int settingalarmIntervalTime(){

        int time = 0;

        Toast.makeText(this, "설정 들어감", Toast.LENGTH_SHORT).show();


        switch(sharedPref.getString("time_interval_list", "1분")){
            case "1분":
                time = 60000;
                break;
            case "2분":
                time = 120000;
                break;
            case "3분":
                time = 180000;
                break;
            case "4분":
                time = 240000;
                break;
            default:
                time = 60000;
        }

        Log.d("알람 설정 들어감", String.valueOf(time));

        return time;

    }

    // 사용자가 일시 중지된 상태에서 액티비티로 돌아오면 시스템은 액티비티를 재개하고
    // onResume() 메서드를 호출합니다.
    @Override
    protected void onResume() {
        super.onResume();
//        AlarmAvailabeCheck();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = formatter.format(System.currentTimeMillis());

        if(currentTime.equals(selectedDateStr)) {
            dayOfData(currentTime);
        }

    }

    public void dayOfData(String date){
        lvWork.setVisibility(View.VISIBLE);
        // DB Helper가 Null이면 초기화
        if( dbHelper == null){
            dbHelper = new DBHelper(MainActivity.this, //현재 화면의 제어권자
                    dbName, //데이터베이스 이름
                    null, //커서팩토리 - null 이면 표준 커서 사용
                    1); //데이터베이스 버전
        }

        List works = dbHelper.getDayWorkData(date);

        // 1. DayWork 데이터를 모두 가져온다.
//        List works = dbHelper.getAllWorkData();

        // 2. ListView에 DayWork 데이터를 모두 보여줌
        lvWork.setAdapter(new WorkListAdapter(works, MainActivity.this));
    }


    protected void AlarmAvailabeCheck(){
        Toast.makeText(getApplicationContext(), "이용이 제한된 시간입니다.", Toast.LENGTH_LONG).show();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        int timeFormat = Integer.parseInt(formatter.format(System.currentTimeMillis()));

        if(timeFormat >= 22 || timeFormat <= 6){
            unregisterAlarm();
        }
    }
}