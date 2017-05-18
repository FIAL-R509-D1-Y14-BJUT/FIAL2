package com.example.administrator.lvf_10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by 冰雪澜风 on 2017/5/13.
 */

public class BackPassword_3 extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpassword_3);
        final Intent intent2 = this.getIntent();
        final EditText resetpassword = (EditText)findViewById(R.id.resetpassword);
        final String Phonenumber = intent2.getStringExtra("Phonenumber");
        Button button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                String password = resetpassword.getText().toString();
                if(password.length()>16||password.length()<6){
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(BackPassword_3.this);
                    normalDialog.setTitle("提示").setMessage("密码长度错误（6<密码长度<16）");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // ...To-do
                                }
                            });
                    // 创建实例并显示
                    normalDialog.show();
                }
                else {
                    String response = SendRequest.sendRequest_signup(Phonenumber, password);
                    switch(response){
                        case "success": {
                            AlertDialog.Builder normalDialog = new AlertDialog.Builder(BackPassword_3.this);
                            normalDialog.setTitle("提示").setMessage("密码修改成功");
                            normalDialog.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // ...To-do
                                        }
                                    });
                            // 创建实例并显示
                            normalDialog.show();

                            finish();
                            break;
                        }
                        case "fail": {
                            Log.d("Sign Up Error!", "http response is unknown");
                            break;
                        }
                    }

                }
            }
        });
        ImageButton image = (ImageButton)findViewById(R.id.imageButtonback3);
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
