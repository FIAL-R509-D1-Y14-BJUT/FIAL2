package com.example.administrator.lvf_10;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.Gravity;
import android.widget.Toast;

public class IndividualsAndSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuals_and_setting);
       setCustomActionBar();
    if (Build.VERSION.SDK_INT >= 21){
        try{
            getSupportActionBar().setElevation(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        Button but_wallet;
        Button but_order;
        Button but_update;
        Button but_aboutus;
        Button button_back;

        but_order = (Button)findViewById(R.id.indi_and_set_butorder);
        but_order.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    //turn to OrderActivity.class
                    Intent intent = new Intent(IndividualsAndSetting.this,MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        button_back = (Button)findViewById(R.id.button1_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndividualsAndSetting.this.finish();
            }
        });

    }

 private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.ind_and_set_actionbar, null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);//error
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        Toolbar parent =(Toolbar)mActionBarView.getParent();
        parent.setContentInsetsAbsolute(0,0);
    }

}
