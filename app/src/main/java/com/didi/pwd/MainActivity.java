package com.didi.pwd;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private myDbHelper helper;
    private SQLiteDBManager DBManager;
    boolean isfirsttime=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isfirsttime)firsttime_alert_View();
        helper=new myDbHelper(this);
        DBManager=new SQLiteDBManager(this);
        List<String> datalist= DBManager.querylistdata();
        addList(datalist);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add:
                startActivity(new Intent(MainActivity.this,addActivity.class));
                break;
            case R.id.edit_query:
                Toast.makeText(getApplicationContext(), "懒得写...(´-ι_-｀)",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        helper = new myDbHelper(this);
        DBManager = new SQLiteDBManager(this);
        List<String> datalist = DBManager.querylistdata();
        addList(datalist);
    }
    private void adddList(List<Map> datalist){
        String [] cytpeStrings=new String[datalist.size()];
        for (int i=0;i<datalist.size();i++){
            Map map=datalist.get(2);
            String msg="用户名:"+map.get("name").toString()+"密码:"+map.get("password").toString();
            cytpeStrings[i]=msg;
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,cytpeStrings);
        listView=findViewById(R.id.list1);
        listView.setAdapter(adapter);
    }

    private void addList(final List<String> datalist){
        final String [] cytpeStrings=new String[datalist.size()];
        for (int i=0;i<datalist.size();i++){
            cytpeStrings[i]=datalist.get(i);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,cytpeStrings);
        listView=findViewById(R.id.list1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (cytpeStrings[position].equals("已经到底了Σ(ŎдŎ|||)ﾉﾉ")){
                    Toast.makeText(getApplicationContext(), "都说了到底了,别点了(◦`~´◦)",Toast.LENGTH_SHORT).show();
                }else {

                    alert_View(cytpeStrings[position], datalist);
                }
            }
        });

    }
    private void alert_View(final String data, final List datalist){
        new AlertDialog.Builder(this).setMessage(data)
                .setView(R.layout.alert_edit)
                .setNegativeButton("复制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager cm= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData myclipdata=ClipData.newPlainText("label",data);
                        cm.setPrimaryClip(myclipdata);
                        Toast.makeText(getApplicationContext(), "复制成功 y( ˙ᴗ. )耶~",Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id= getid(data,datalist);
                        DBManager.deletequery(id);
                        onResume();
                    }
                })
                .setPositiveButton("编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id= getid(data,datalist);
                        List namelist=DBManager.queryNameData(id);
                        List pwdlist=DBManager.querypwdData(id);
                        List domainlist=DBManager.querydomainData(id);
                        String name= (String) namelist.get(0);
                        String pwd= (String) pwdlist.get(0);
                        String domain= (String) domainlist.get(0);
                        Intent intent = new Intent(MainActivity.this,updatectivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("pwd",pwd);
                        intent.putExtra("id",id);
                        intent.putExtra("domain",domain);
                        startActivity(intent);
                    }
                })
                .show();
    }
    private String getid(final String data,final List datalist){
        List listiddata=DBManager.querylistid();
        int listid=datalist.indexOf(data);
        String id= (String) listiddata.get(listid);//获取点击选项在数据库里的id
        return id;
    }
    public void firsttime_alert_View(){
        String msg="大概就是脑子抽了\n觉得iCloud钥匙串很好用\n遂写一个用来记住记不住的密码的-_-||,\n它没有联网权限,大可放心—_—";
        String title="你问我这是干什么的？";
        new AlertDialog.Builder(this).setMessage(msg)
                .setTitle(title)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();
        isfirsttime=false;
    }
}
