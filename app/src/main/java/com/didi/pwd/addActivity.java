package com.didi.pwd;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addActivity extends AppCompatActivity {
    EditText name;
    EditText pwd;
    EditText domain;
    Button btn;
    private myDbHelper helper;
    private SQLiteDBManager DBManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper=new myDbHelper(this);
        DBManager=new SQLiteDBManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name =findViewById(R.id.name);
        pwd=findViewById(R.id.pwd);
        domain=findViewById(R.id.domain);
        btn =findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString=name.getText().toString();
                String pwdString=pwd.getText().toString();
                String domainString=domain.getText().toString();
                if(!nameString.isEmpty()&&!pwdString.isEmpty()&&!domainString.isEmpty()){
                DBManager.add(nameString,pwdString,domainString);
                Toast.makeText(getApplicationContext(),"插入成功", Toast.LENGTH_SHORT).show();
                finish();
                }else {
                    Toast.makeText(getApplicationContext(), "想想你漏了什么(´-ι_-｀)",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
