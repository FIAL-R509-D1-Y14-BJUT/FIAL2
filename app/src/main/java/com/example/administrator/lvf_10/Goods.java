package com.example.administrator.lvf_10;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.PipedReader;
import java.io.PipedWriter;


public class Goods extends AppCompatActivity {

    private String user_tel = "150";
    private String flo_name = "ziluolan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        setCustomActionBar();
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(1);
        }
        Button goods_button = (Button) findViewById(R.id.goods_buttton);

        goods_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Goods.this)
                        .setTitle("购买提示")
                        .setMessage("您购买的 紫罗兰 需要花费" +
                                "1元")
                        .setPositiveButton("购买", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String response = sendRequest_goods("150", "ziluolan");


                                if (response == null) {
                                    Log.d("Sign Up Error!", "http response is null");
                                    return;
                                } else if (response.equals("success")) {

                                    Toast.makeText(Goods.this, getResources().getText(R.string.goods_request_success), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    intent.setClass(Goods.this, MainActivity.class);//从一个activity跳转到另一个activity
                                    startActivity(intent);
                                } else if (response.equals("NoFlower")) {
                                    Toast.makeText(Goods.this, getResources().getText(R.string.goods_request_NoFlower), Toast.LENGTH_SHORT).show();
                                } else if (response.equals("NoMoney")) {
                                    Toast.makeText(Goods.this, getResources().getText(R.string.goods_request_NoMoney), Toast.LENGTH_SHORT).show();
                                } else if (response.equals("fail")) {
                                    Toast.makeText(Goods.this, "request fialed!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Goods.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        Toolbar parent =(Toolbar)mActionBarView.getParent();
        parent.setContentInsetsAbsolute(0,0);

    }

    private String sendRequest_goods(final String user_tel, final String flo_name) {
        final PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        try {
            pipedWriter.connect(pipedReader);

        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    org.apache.http.client.HttpClient httpClient = new org.apache.http.impl.client.DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://172.16.16.59:8080/FIAL2_backSupporter/BuyBuyBuy?tel=" + user_tel + "&" + "flo=" + flo_name);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");

                        Gson gson = new Gson();
                        String res = gson.fromJson(response, String.class);
                        if (res != null) {
                            Log.d("getjson(goods)", res);
                            pipedWriter.write(res);
                        } else {
                            pipedWriter.write("null");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            char[] buf = new char[2048];
            int len = pipedReader.read(buf);
            String response = new String(buf, 0, len);
            Log.d("LoginReadBuffer(goods)", response);
            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
