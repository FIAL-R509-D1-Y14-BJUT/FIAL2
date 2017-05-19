package com.example.administrator.lvf_10;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

// 花朵框架类（只有仅需变动的部分）

public class Shop extends AppCompatActivity {

    public static class DataItem
    {
        //花朵图片
        public Drawable drawable_flower;
        //花朵名称
        public Drawable drawable_name;
        //花朵价格
        public Drawable drawable_cost;

    }
    private ArrayList<DataItem> items = new ArrayList<>();
    // 创建LayoutInflater对象
    LayoutInflater inflater;
    // 记录当前正在显示第几屏的程序
    private int screenNo = -1;
    // 保存程序所占的总屏数
    private int screenCount = 2;

    ViewSwitcher switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setCustomActionBar();
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                getSupportActionBar().setElevation(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        inflater = LayoutInflater.from(Shop.this);

        for (int i = 0; i < 2; i++)
        {
            Drawable drawable_1 = getResources().getDrawable(
                    R.drawable.shop_pic_ziluolan);
            Drawable drawable_2 = getResources().getDrawable(
                    R.drawable.shop_text_ziluolan);
            Drawable drawable_3 = getResources().getDrawable(
                    R.drawable.shop_text_9);
            DataItem item = new DataItem();
            item.drawable_flower = drawable_1;
            item.drawable_name = drawable_2;
            item.drawable_cost = drawable_3;
            items.add(item);
        }

        switcher = (ViewSwitcher) findViewById(R.id.shop_viewSwitcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return inflater.inflate(R.layout.shop_view_switcher, null);
            }
        });
        next(null);

        /*Button button_confirm = (Button) findViewById(R.id.button_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this, Goods.class);
                startActivity(intent);
            }
        });*/
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shop.this.finish();
            }
        });
    }
public void jump(View v){
    Intent intent = new Intent(Shop.this, Goods.class);
    startActivity(intent);
}
    public void next(View v) {
        if (screenNo < screenCount - 1) {
            screenNo++;
            // 为ViewSwitcher的组件显示过程设置动画
            switcher.setInAnimation(this, R.anim.slide_in_right);
            // 为ViewSwitcher的组件隐藏过程设置动画
            switcher.setOutAnimation(this, R.anim.slide_out_left);
            // 控制下一屏将要显示的GridView对应的 Adapter
            ((GridView) switcher.getNextView()).setAdapter(adapter);
            // 点击右边按钮，显示下一屏，也可通过手势检测实现显示下一屏.
            switcher.showNext();
        }
    }

    public void prev(View v) {
        if (screenNo > 0) {
            screenNo--;
            // 为ViewSwitcher的组件显示过程设置动画
            switcher.setInAnimation(this, R.anim.slide_in_left);
            // 为ViewSwitcher的组件隐藏过程设置动画
            switcher.setOutAnimation(this, R.anim.slide_out_right);
            // 控制下一屏将要显示的GridView对应的 Adapter
            ((GridView) switcher.getNextView()).setAdapter(adapter);
            // 点击左边按钮，显示上一屏，也可通过手势检测实现显示上一屏.
            switcher.showPrevious();
        }
    }

    // 该BaseAdapter负责为每屏显示的GridView提供列表项
    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public DataItem getItem(int position) {
            // 根据screenNo计算第position个列表项的数据
            return items.get(screenNo + position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position
                , View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                // 加载R.layout.labelicon布局文件
                view = inflater.inflate(R.layout.shop_view_switcher_main_layout, null);
            }
            // 获取R.layout.labelicon布局文件中的ImageView组件，并为之设置图标
            ImageView imageView_1 = (ImageView)
                    view.findViewById(R.id.imageView_ZILUOLAN1);
            imageView_1.setImageDrawable(getItem(position).drawable_flower);
            ImageView imageView_2 = (ImageView)
                    view.findViewById(R.id.imageView_ZILUOLAN2);
            imageView_2.setImageDrawable(getItem(position).drawable_name);
            ImageView imageView_3 = (ImageView)
                    view.findViewById(R.id.imageView_ZILUOLAN3);
            imageView_3.setImageDrawable(getItem(position).drawable_cost);
            return view;
        }
    };

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.shop_action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }
}