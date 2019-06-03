package com.didi.pwd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbHelper extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME="user.db";
    private static  final int DATABASSE_VERSION = 1;

    public myDbHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASSE_VERSION);
    }
    //数据库第一次创建的时候onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo"+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,"+
                "domain VARCHAR,"+
                "password VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
