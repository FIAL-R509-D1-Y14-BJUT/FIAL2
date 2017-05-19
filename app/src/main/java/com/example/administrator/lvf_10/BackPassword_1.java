package com.example.administrator.lvf_10;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import static com.example.administrator.lvf_10.SendRequest.sendrequest_retrievepassword;

public class BackPassword_1 extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpassword_1);
        SysApplication.getInstance().addActivity(this);

        final EditText edittext = (EditText)findViewById(R.id.editText);
        Button button5 = (Button)findViewById(R.id.button5);
        final Bundle b = new Bundle();
        final String password = "";
        button5.setOnClickListener(new View.OnClickListener(){//找回密码键响应
            @Override
            public void onClick(View v) {
                //传递手机号给下一个界面
                String a = edittext.getText().toString();
                String retrievepassword = sendrequest_retrievepassword(a, password);
                if (a.length() == 11) {
                    if (retrievepassword.equals("ok")) {
                        Intent intent = new Intent(BackPassword_1.this, BackPassword_2.class);//设置界面跳转
                        intent.putExtra("PhoneNumber", a);//发送消息“手机号”
                        startActivity(intent);
                    } else if (retrievepassword.equals("null")) {
                        AlertDialog.Builder normalDialog = new AlertDialog.Builder(BackPassword_1.this);
                        normalDialog.setTitle("提示").setMessage("手机号不存在");
                        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ...To-do
                            }
                        });
                        // 创建实例并显示
                        normalDialog.show();
                    } else {
                        AlertDialog.Builder normalDialog1 = new AlertDialog.Builder(BackPassword_1.this);
                        normalDialog1.setTitle("提示").setMessage("未知错误请联系客服");
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
                else if (a.length() != 11){
                    AlertDialog.Builder normalDialog2 = new AlertDialog.Builder(BackPassword_1.this);
                    normalDialog2.setTitle("提示").setMessage("手机号长度错误");
                    normalDialog2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ...To-do
                        }
                    });
                    // 创建实例并显示
                    normalDialog2.show();
                }
                else{
                    AlertDialog.Builder normalDialog3 = new AlertDialog.Builder(BackPassword_1.this);
                    normalDialog3.setTitle("提示").setMessage("error！");
                    normalDialog3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ...To-do
                        }
                    });
                    // 创建实例并显示
                    normalDialog3.show();
                }
            }
        });
        ImageButton imagebutton = (ImageButton)findViewById(R.id.imageButton13);
        imagebutton.setOnClickListener(new View.OnClickListener(){//后退按钮响应
            @Override
            public void  onClick(View v){
                finish();//后退键关闭界面
            }
        });
        //  setCustomActionBar();
       /* if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(1);
        }*/
    }

   /* private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.retrieve_action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }*/

}