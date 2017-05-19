package com.example.administrator.lvf_10;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Money extends AppCompatActivity {


    ViewPager viewPager;
    ArrayList<View> pageview;
    TextView depositLayout;
    TextView costLayout;
    // 滚动条图片
    ImageView scrollbar;
    // 滚动条初始偏移量
    private int offset = 0;
    // 当前页编号
    private int currIndex = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;

    private ListView lv1;
    private ListView lv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        setCustomActionBar();
        if(Build.VERSION.SDK_INT>=21){
            try{
                getSupportActionBar().setElevation(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        viewPager = (ViewPager) findViewById(R.id.order_viewPager);
        //查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.order_deposit, null);
        View view2 = inflater.inflate(R.layout.order_cost, null);
        depositLayout = (TextView) findViewById(R.id.text_deposit);
        costLayout = (TextView) findViewById(R.id.text_cost);
        scrollbar = (ImageView) findViewById(R.id.scrollbar);
        // Intent intent = new Intent(Money.this,LoginActivity.class);
        //startActivity(intent);
        depositLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        costLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        pageview =new ArrayList<View>();
        //添加想要切换的界面
        pageview.add(view1);
        pageview.add(view2);
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter(){

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }
            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        //设置viewPager的初始界面为第一个界面
        viewPager.setCurrentItem(0);
        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.xueyuan_line_record).getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //计算出滚动条初始的偏移量
        offset = (screenW / 2 - bmpW) / 2;
        //计算出切换一个界面时，滚动条的位移量
        one = offset * 2 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbar.setImageMatrix(matrix);

        //ListView Part
        /*当交易请求完成时，返回signal，通过监听signal变化自动生成记录数据*/
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String nowtime = format.format(new Date());
        int signal = 0;
        int count = 1;
        lv1 = (ListView)view1.findViewById(R.id.order_list1);
        ArrayList<HashMap<String,Object>> ListItem1 = new ArrayList<HashMap<String,Object>>();
        // signal = getsignal();
        //while(signal == 1) {
        HashMap<String, Object> obj1 = new HashMap<String, Object>();
        obj1.put("ItemTitle", "第" + count + "条记录");
        obj1.put("ItemDates", nowtime);
        obj1.put("ItemMoney", "+" + "20");//getMoney,返回String
        count++;
        ListItem1.add(obj1);
        // }
        SimpleAdapter mSimpleAdapter1 = new SimpleAdapter(this,ListItem1,R.layout.order_mylist,new String[]{"ItemTitle","ItemDates","ItemMoney"},new int[]
                {R.id.order_num_tv,R.id.order_time_tv,R.id.order_money_tv});
        lv1.setAdapter(mSimpleAdapter1);


        /////
        int count2 = 1;
        lv2 = (ListView)view2.findViewById(R.id.order_list2);
        ArrayList<HashMap<String,Object>> ListItem2 = new ArrayList<HashMap<String,Object>>();
        // signal = getsignal();
        //while(signal == 1) {
        HashMap<String, Object> obj2 = new HashMap<String, Object>();
        obj2.put("ItemTitle", "第" + count2 + "条记录");
        obj2.put("ItemDates", nowtime);
        obj2.put("ItemMoney", "-" + "20");//getMoney,返回String
        count++;
        ListItem2.add(obj2);
        // }
        SimpleAdapter mSimpleAdapter2 = new SimpleAdapter(this,ListItem2,R.layout.order_mylist2,new String[]{"ItemTitle","ItemDates","ItemMoney"},new int[]
                {R.id.order_num_tv,R.id.order_time_tv,R.id.order_money_tv});
        lv2.setAdapter(mSimpleAdapter2);
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    break;
                case 1:
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    break;
            }
            //arg0为切换到的页的编码
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbar.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /*@Override
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.text_deposit:
                //点击"视频“时切换到第一页
                viewPager.setCurrentItem(0);
                break;
            case R.id.text_cost:
                //点击“音乐”时切换的第二页
                viewPager.setCurrentItem(1);
                break;
        }
    }*/
    private void setCustomActionBar(){
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.order_actionbar, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);//error
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        Toolbar parent =(Toolbar)mActionBarView.getParent();
        parent.setContentInsetsAbsolute(0,0);
    }
    private int getsignal(){
        return 0;
    }
}
