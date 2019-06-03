package com.didi.pwd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteDBManager {
    private myDbHelper helper;
    private SQLiteDatabase db;

     public SQLiteDBManager(Context Context){
         helper=new myDbHelper(Context);
         db=helper.getWritableDatabase();
     }
     public void add(String name,String password,String domain){
         ContentValues values=new ContentValues();
         values.put("name",name);
         values.put("password",password);
         values.put("domain",domain);
         db.insert("userinfo",null,values);
     }
    public List<String> querylistdata(){
        List<String> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM userinfo",null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String password=cursor.getString(cursor.getColumnIndex("password"));
            String domain=cursor.getString(cursor.getColumnIndex("domain"));
            String passwordstring="";
            for(int i=0;i<password.length();i++){
                passwordstring+="*";
            }
            String msg;
            if(domain.isEmpty()){
                msg="用户名:"+name+"\n密 码   :"+passwordstring;
            }else {
                msg="地  址  :"+domain+"\n用户名:"+name+"\n密  码  :"+passwordstring;
            }
            list.add(msg);
        }
        list.add("已经到底了Σ(ŎдŎ|||)ﾉﾉ");
        cursor.close();
        return list;
    }public List<String> querylistid(){
        List<String> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM userinfo",null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String msg=String.valueOf(id);
            list.add(msg);
        }
        cursor.close();
        return list;
    }
    public void deletequery(String id){
         db.delete("userinfo","id= ?",new String[]{id});
    }
    public void update(String name,String password,String id){
         ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("password",password);
        Log.d("updaye!!!", "update:start");
        String whereClause="id=?";
        String[] whereArgs = new String[] {id};
       int re= db.update("userinfo", values, whereClause, whereArgs);
       String sql="update userinfo set name='" +name+"'" +",password="+password+"'where id="+id;
        //db.execSQL(sql);
        Log.d("updaye!!!", "update:sql"+sql);
    }
    public List<String> queryNameData(String id){
        List<String> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM userinfo",null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            Integer iddata=cursor.getInt(cursor.getColumnIndex("id"));
            if (iddata.toString().equals(id)){
            String msg=name;
            list.add(msg);
            }
        }
        cursor.close();
        return list;
    }
public List<String> querydomainData(String id){
        List<String> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM userinfo",null);
        while (cursor.moveToNext()){
            String domain=cursor.getString(cursor.getColumnIndex("domain"));
            Integer iddata=cursor.getInt(cursor.getColumnIndex("id"));
            if (iddata.toString().equals(id)){
            String msg=domain;
            list.add(msg);
            }
        }
        cursor.close();
        return list;
    }
    public List<String> querypwdData(String id){
        List<String> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM userinfo",null);
        while (cursor.moveToNext()){
            String password=cursor.getString(cursor.getColumnIndex("password"));
            Integer iddata=cursor.getInt(cursor.getColumnIndex("id"));
            if (iddata.toString().equals(id)){
                String msg=password;
                list.add(msg);
            }
        }
        cursor.close();
        return list;
    }

}

