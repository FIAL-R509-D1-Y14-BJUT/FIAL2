package com.example.administrator.lvf_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class IndividualsAndSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuals_and_setting);
        Button but_wallet;
        Button but_order;
        Button but_update;
        Button but_aboutus;
        but_order = (Button)findViewById(R.id.indi_and_set_butorder);
        but_order.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    //turn to OrderActivity.class
                    Intent intent = new Intent(IndividualsAndSetting.this,MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
