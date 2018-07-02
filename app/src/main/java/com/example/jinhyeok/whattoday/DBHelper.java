package com.example.jinhyeok.whattoday;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     * Database가 존재하지 않을 때, 딱 한번 실행되어 DB를 만드는 역할.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE WORK_TABLE ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" TIME TEXT, ");
        sb.append(" CONTENT TEXT ) ");

        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());
        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();

    }

    /**
     * Application의 버전이 올라가서
     * Table 구조가 변경되었을 때 실행된다.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }

    public void testDB(){
        SQLiteDatabase db = getReadableDatabase();
    }

    public void addWork(DayWork dayWork){
        // 1. 쓸 수 있는 DB 객체를 가져옴
        SQLiteDatabase db = getWritableDatabase();

        // 2. DayWork Data를 Insert한다.
        // _id는 자동으로 증가하기 때문에 넣지 않음
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO WORK_TABLE ( ");
        sb.append(" TIME, CONTENT ) ");
        sb.append(" VALUES ( ?, ? ) ");

        db.execSQL(sb.toString(),
                new Object[]{
                dayWork.getTime(),
                dayWork.getContent()});

        Toast.makeText(context, "Insert 완료", Toast.LENGTH_SHORT).show();
    }

    public List getAllWorkData() {
        Toast.makeText(context, "data 가져오기", Toast.LENGTH_SHORT).show();

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, TIME, CONTENT FROM WORK_TABLE ");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        List works = new ArrayList();
        DayWork dayWork = null;
        // moveToNext 다음에 데이터가 있으면 true 없으면 false
        while (cursor.moveToNext()) {
            dayWork = new DayWork();
            dayWork.set_id(cursor.getInt(0));
            dayWork.setTime(cursor.getString(1));
            dayWork.setContent(cursor.getString(2));

            works.add(dayWork);
        }
        return works;
    }

    public List getDayWorkData(String today) {
        Toast.makeText(context, "하루 data 가져오기", Toast.LENGTH_SHORT).show();


        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT * FROM WORK_TABLE WHERE TIME LIKE \"" + today +"%\"");

        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        List works = new ArrayList();
        DayWork dayWork = null;
        // moveToNext 다음에 데이터가 있으면 true 없으면 false
        while (cursor.moveToNext()) {
            dayWork = new DayWork();
            dayWork.set_id(cursor.getInt(0));
            dayWork.setTime(cursor.getString(1));
            dayWork.setContent(cursor.getString(2));

            works.add(dayWork);
        }
        return works;
    }



}
