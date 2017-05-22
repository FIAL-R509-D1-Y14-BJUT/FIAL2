package com.example.administrator.lvf_10;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.PipedReader;
import java.io.PipedWriter;

public class BackPassword_2 extends AppCompatActivity {

    String response = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpassword_2);
        SysApplication.getInstance().addActivity(this);
        final TextView tv = (TextView)findViewById(R.id.passwordback_textView34);
        Button nbutton = (Button)findViewById((Integer) R.id.passwordback_button5);
        final EditText passwordback_editText = (EditText)findViewById(R.id.passwordback2_editText);
        Intent intent1=this.getIntent();
        final String Phonenumber = intent1.getStringExtra("PhoneNumber");//接收消息“手机号”


        nbutton.setOnClickListener(new View.OnClickListener() {//下一步键响应
            @Override
            public void onClick(View v) {//将验证码进行匹配
                if(passwordback_editText.getText().toString().equals(response)&&!(passwordback_editText.getText().toString().equals(""))){
                    //  验证码验证成功后跳转
                    Intent intent = new Intent(BackPassword_2.this,BackPassword_3.class);
                    intent.putExtra("Phonenumber",Phonenumber);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder normalDialog1 = new AlertDialog.Builder(BackPassword_2.this);
                    normalDialog1.setTitle("提示").setMessage("验证码验证错误");
                    normalDialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ...To-do
                        }
                    });
                    // 创建实例并显示
                    normalDialog1.show();
                }

            }
        });


        ImageButton image=(ImageButton)findViewById(R.id.imageButton13);
        image.setOnClickListener(new View.OnClickListener(){//后退按钮响应
            @Override
            public void onClick(View v){

                finish();
            }
        });
        tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String ip = getResources().getString(R.string.back_supporter_ip);
                response = SendRequest.sendRequest_getcode(ip);
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(BackPassword_2.this);
                normalDialog.setTitle("提示").setMessage("验证码为"+response);
                normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
                // 创建实例并显示
                normalDialog.show();
            }
        });
    }

}

