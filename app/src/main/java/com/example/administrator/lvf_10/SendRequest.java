package com.example.administrator.lvf_10;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by 冰雪澜风 on 2017/5/16.
 */

public class SendRequest {
    public static String sendRequest_signup(final String user_name, final String password, final String back_supporter_ip) {
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
                    HttpGet httpGet = new HttpGet("http://" + back_supporter_ip + ":8080/FIAL2_backSupporter/SignUp?tel="+user_name+"&"+"password="+password);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");

                        Gson gson = new Gson();
                        String res = gson.fromJson(response, String.class);
                        if (res != null) {
                            Log.d("getjson(signup)", res);
                            pipedWriter.write(res);
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
            String response = new String(buf, 0, len);
            Log.d("LoginReadBuffer(signup)", response);
            return response;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String sendRequest_getcode(final String back_supporter_ip){

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
                    HttpGet httpGet = new HttpGet("http://" + back_supporter_ip + ":8080/FIAL2_backSupporter/GetCode");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");

                        Gson gson = new Gson();
                        String res = gson.fromJson(response, String.class);
                        if (res != null) {
                            Log.d("getjson(signup)", res);
                            pipedWriter.write(res);
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
            String response = new String(buf, 0, len);
            Log.d("LoginReadBuffer(signup)", response);
            return response;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String sendrequest_retrievepassword(final String user_name, final String password, final String back_supporter_ip) {
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
                    HttpGet httpGet = new HttpGet("http://" + back_supporter_ip + ":8080/FIAL2_backSupporter/RetrievePassword?tel="+user_name+"&"+"password="+password);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");

                        Gson gson = new Gson();
                        String res = gson.fromJson(response, String.class);
                        if (res != null) {
                            Log.d("getjson(retrieve)", res);
                            pipedWriter.write(res);
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
            String response = new String(buf, 0, len);
            Log.d("LoginRead(retrieve)", response);
            return response;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String sendRequest_goods(final String user_tel, final String flo_name, final String back_supporter_ip) {
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
                    HttpGet httpGet = new HttpGet("http://" + back_supporter_ip + ":8080/FIAL2_backSupporter/BuyBuyBuy?tel=" + user_tel + "&" + "flo=" + flo_name);
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
