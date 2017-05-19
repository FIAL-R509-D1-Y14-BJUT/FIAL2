package com.example.administrator.lvf_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView lv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*lv2 = (ListView)findViewById(R.id.myArrayList);
        ArrayList<HashMap<String,Object>> ListItem = new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<10;i++){

            HashMap<String, Object> obj1 = new HashMap<String, Object>();
            obj1.put("ItemTitle", "第" + i + "条记录");
            obj1.put("ItemImage", "11111");
            obj1.put("ItemMoney", "+" + "20");//getMoney,返回String
            String s = Integer.toString(i);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            ListItem.add(obj1);}
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,ListItem,R.layout.test_listview,new String[]{"ItemTitle","ItemImage","ItemMoney"},new int[]
                {R.id.tv1,R.id.im1,R.id.tv2});
        lv2.setAdapter(mSimpleAdapter);*/
        Intent intent = new Intent(MainActivity.this,Money.class);
        startActivity(intent);
    }
}
