package com.didi.pwd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class updatectivity extends AppCompatActivity {
    private myDbHelper helper;
    private SQLiteDBManager DBManager;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent=getIntent();
        final String name = intent.getStringExtra("name");
        final String pwd = intent.getStringExtra("pwd");
        final String id = intent.getStringExtra("id");
        final String domain = intent.getStringExtra("domain");
        final EditText name_edittext=findViewById(R.id.update_name);
        final EditText pwd_edittext=findViewById(R.id.update_pwd);
        tv=findViewById(R.id.up_date_textView);
        tv.setText(domain);
        name_edittext.setText(name);
        pwd_edittext.setText(pwd);
        btn=findViewById(R.id.update_btn);
        helper=new myDbHelper(this);
        DBManager=new SQLiteDBManager(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String update_name=name_edittext.getText().toString();
                    String update_pwd=pwd_edittext.getText().toString();
                    if(!update_name.isEmpty()&&!update_pwd.isEmpty()){
                    DBManager.update(update_name,update_pwd,id);
                    Toast.makeText(getApplicationContext(), "修改成功",Toast.LENGTH_LONG).show();
                    finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "想想你漏了什么(´-ι_-｀)",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
