package com.example.refrigerator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.icu.text.SimpleDateFormat;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Date;

public class FoodDBA extends SQLiteOpenHelper {

    public FoodDBA(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 DEMO_SQLITE이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 문자열 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */

        try {
            if (db == null) {
                System.out.println("데이터베이스를 먼저 생성하세요");
                return;
            }

            String query = "create table if not exists " + "food" + "(" +
                    "_id integer PRIMARY KEY autoincrement," +
                    "name text," +
                    "kind text," +
                    "expiration datetime)";
            db.execSQL(query);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name, String kind, String expiration) {
        SQLiteDatabase db = getWritableDatabase();
        String query ="";

        query = "insert into food" +
                "(name, kind, expiration)" +
                " values " + "('" + name + "','" + kind + "','" + expiration +"')";
        db.execSQL(query);

        db.close();
    }

    public void update(String item, String price) {
//        SQLiteDatabase db = getWritableDatabase();
//        // 입력한 항목과 일치하는 행의 가격 정보 수정
//        db.execSQL("UPDATE DEMO_SQLITE SET price=" + price +
//
//                " WHERE item='" + item + "';");
//        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM food WHERE _id='" + id + "';");
        db.close();
    }

    public void dayDelete() {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        String today = mFormat.format(date);

        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM food WHERE expiration <= datetime('" + today + "','+2 days');");
        db.close();
    }
    // 선택 식품의 결과를 화면에 렌더링
    public ListViewAdapter getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int _id;
        String name;
        String kind;
        String expiration;

        ListViewAdapter adapter = new ListViewAdapter();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("select * from food order by expiration ASC", null);
        while (cursor.moveToNext()) {
            _id = cursor.getInt(0);
            name = cursor.getString(1);
            kind = cursor.getString(2);
            expiration = cursor.getString(3);

            adapter.addItem(null, name, _id, kind, expiration);

        }
        return adapter;
    }
    // 하루 남은 제품의 결과를 화면에 렌더링
    public HomeListViewAdapter getDayResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int _id;
        String name;
        String kind;
        String expiration;

        HomeListViewAdapter adapter = new HomeListViewAdapter();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new Date();
        String today = mFormat.format(date);

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력 (1 days)
        Cursor cursor = db.rawQuery("select * from food where expiration <= datetime('" + today + "','+2 days') order by expiration ASC", null);
        while (cursor.moveToNext()) {
            _id = cursor.getInt(0);
            name = cursor.getString(1);
            kind = cursor.getString(2);
            expiration = cursor.getString(3);
            adapter.addItem(null, name, _id, kind, expiration);
        }
        return adapter;
    }
}
