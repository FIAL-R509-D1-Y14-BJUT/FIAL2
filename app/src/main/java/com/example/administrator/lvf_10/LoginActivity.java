package com.example.administrator.lvf_10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    int Astate = 0;
    int Bstate = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        final Button button1 = (Button)findViewById(R.id.email_sign_in_button);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View  v){
                // button1.setBackgroundColor();
                EditText loginnum = (EditText)findViewById(R.id.text_login);
                String user_name = loginnum.getText().toString();
                EditText passwd = (EditText)findViewById(R.id.password);
                String text2 = passwd.getText().toString();
                String respondpassword = sendRequest_login(user_name);
                if(!respondpassword.equals("")){ Astate = 1;}
                else if(user_name.length()==0){ Astate = 0;}
                else { Astate = -1;}
                if(text2.length()==0){ Bstate = 0;}
                else if(text2.equals(respondpassword)){ Bstate = 1;}
                else { Bstate = -1;}
                if(Astate==1&&Bstate==1){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("user_tel", user_name);
                    startActivity(intent);
                }
                else if(Astate==0){
                    Toast.makeText(getApplicationContext(),"账号不能为空！",Toast.LENGTH_SHORT).show();
                }
                else if(Bstate==0){
                    Toast.makeText(getApplicationContext(),"请输入密码！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"账号或密码错误！",Toast.LENGTH_SHORT).show();
            }
        });

        final TextView forget = (TextView) findViewById(R.id.login_edittext_forget);
        forget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                    forget.setBackgroundColor(0xff727272);
                if (event.getAction()==MotionEvent.ACTION_UP)
                {
                    forget.setBackgroundColor(0xffffd513);
                    Intent intent = new Intent(LoginActivity.this,BackPassword_1.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        final TextView register = (TextView) findViewById(R.id.login_edittext_register);
        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN)
                    register.setBackgroundColor(0xff727272);
                if (event.getAction()==MotionEvent.ACTION_UP)
                {
                    register.setBackgroundColor(0xffffd513);
                    Intent intent = new Intent(LoginActivity.this,Retrieve_1.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }
    private String sendRequest_login(final String user_name) {
        final PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        try {
            pipedWriter.connect(pipedReader);

        }catch (Exception e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    org.apache.http.client.HttpClient httpClient = new org.apache.http.impl.client.DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://" + getResources().getString(R.string.back_supporter_ip) + ":8080/FIAL2_backSupporter/Login?tel="+user_name);
                    Log.d("gttp", "aa"+R.string.back_supporter_ip);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");

                        Gson gson = new Gson();
                        String password = gson.fromJson(response, String.class);
                        if (password != null) {
                            Log.d("getjson", password);
                            pipedWriter.write(password);
                        }else{
                            pipedWriter.write("null");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            char[] buf = new char[2048];
            int len = pipedReader.read(buf);
            String password = new String(buf, 0, len);
            Log.d("LoginReadBuffer", password);
            return password;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
