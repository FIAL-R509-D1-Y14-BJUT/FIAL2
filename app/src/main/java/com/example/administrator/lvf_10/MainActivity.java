package com.example.administrator.lvf_10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
//public class MainActivity extends AppCompatActivity{
    /*存储从数据库获取到的id情况*/
    private int[] flowerId;

    /**
     * ViewPager
     */
    private ViewPager viewPager;

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    /**
     * 图片资源id
     */
    private int[] imgIdArray ;
    String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton button = (ImageButton) findViewById(R.id.imageButton6);
        final ImageButton jiaoshui = (ImageButton)findViewById(R.id.jiaoshui);
        final ImageButton button1 = (ImageButton) findViewById(R.id.btA);
        final ImageButton button2 = (ImageButton) findViewById(R.id.btB);
        final ImageButton button3 = (ImageButton) findViewById(R.id.imageButton5);

        jiaoshui.setOnTouchListener(new View.OnTouchListener() {
            int n = 1;
            @Override
            public boolean  onTouch(View v, MotionEvent event) {   //监听floating按钮
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        //按下
                        jiaoshui.setImageDrawable(getResources().getDrawable(R.drawable.main_button_watering_selected));
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //移动
                        n = 0;
                        jiaoshui.setImageDrawable(getResources().getDrawable(R.drawable.main_button_watering));
                        break;

                    case MotionEvent.ACTION_UP:
                        //抬起
                        if(n == 1) {
                            jiaoshui.setImageDrawable(getResources().getDrawable(R.drawable.main_button_watering));
                        }
                        n=1;
                        break;

                }
                return true;
            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            int n = 1;
            @Override
            public boolean  onTouch(View v, MotionEvent event) {                      //监听floating按钮

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        //按下
                        if (button1.getVisibility() == View.VISIBLE) {
                            button.setImageDrawable(getResources().getDrawable(R.drawable.main_button_minus_selected));
                        }
                        else {
                            button.setImageDrawable(getResources().getDrawable(R.drawable.main_button_plus_selected));
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //移动
                        n = 0;
                        if (button1.getVisibility() == View.VISIBLE) {
                            button.setImageDrawable(getResources().getDrawable(R.drawable.main_button_minus));
                        }
                        else {
                            button.setImageDrawable(getResources().getDrawable(R.drawable.main_button_plus));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起
                        if(n == 1){
                        if (button1.getVisibility() == View.VISIBLE) {
                            button.setImageDrawable(getResources().getDrawable(R.drawable.main_button_plus));
                            button1.setVisibility(View.GONE);
                            button2.setVisibility(View.GONE);
                        } else {
                            button.setImageDrawable(getResources().getDrawable(R.drawable.main_button_minus));
                            button1.setVisibility(View.VISIBLE);
                            button2.setVisibility(View.VISIBLE);
                        }}
                        n=1;
                        break;
                }
                return true;
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener() {
            int n = 1;
            @Override
            public boolean  onTouch(View v, MotionEvent event) {   //监听floating按钮
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        //按下
                        button2.setImageDrawable(getResources().getDrawable(R.drawable.main_button_setting_selected));
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //移动
                        n = 0;
                        button2.setImageDrawable(getResources().getDrawable(R.drawable.main_button_setting));
                        break;

                    case MotionEvent.ACTION_UP:
                        //抬起
                        if(n == 1) {
                            button2.setImageDrawable(getResources().getDrawable(R.drawable.main_button_setting));
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, IndividualsAndSetting.class);
                            startActivity(intent);
                        }

                        n=1;
                        break;

                }
                return true;
            }
        });

        button1.setOnTouchListener(new View.OnTouchListener() {
            int n = 1;
            @Override
            public boolean  onTouch(View v, MotionEvent event) {   //监听floating按钮
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        //按下
                        button1.setImageDrawable(getResources().getDrawable(R.drawable.main_button_flower_selected));
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //移动
                        n = 0;
                        button1.setImageDrawable(getResources().getDrawable(R.drawable.main_button_flower));
                        break;

                    case MotionEvent.ACTION_UP:
                        //抬起
                        if(n == 1) {
                            button1.setImageDrawable(getResources().getDrawable(R.drawable.main_button_flower));
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, Shop.class);
                            intent.putExtra("user_tel", tel);
                            startActivity(intent);
                        }
                        n=1;
                        break;
                }
                return true;
            }
        });

        //调用接口并解析json数组
        Gson gson = new Gson();
        int arraySize = 0;//数组大小
        Bundle bundle = this.getIntent().getExtras();
        tel = bundle.getString("user_tel");
        String response = get_userflower(tel);//获取到String类型的json数组
        System.out.println(response);
        JsonParser parser = new JsonParser();//parse用于从一个字符串中解析出json对象
        JsonElement element = parser.parse(response);//解析出json对象
        JsonArray jsonArray = null;
        if (element.isJsonArray()) {//还可以是其他的很多种类型，不过这里肯定是JsonArray，以往万一判断了一下
            jsonArray = element.getAsJsonArray();//这里就已经是真正的json数组了
            arraySize = jsonArray.size();//json数组就可以获取数量了
            System.out.println(arraySize);
        }
        flowerDataStyle styflowerdata[] = new flowerDataStyle[arraySize];//根据刚才得到的size创建对象数组
        /*for (int i=0; i<arraySize; i++){//初始化对象数组，可不用
            styflowerdata[i].set...
        }*/
        Iterator it = jsonArray.iterator();//使用迭代器来获取json数组内容
        for (int i=0; it.hasNext()==true; i++) {//读取所有信息，一次是一个大小的flowerDataStyle.class
            JsonElement e = (JsonElement) it.next();//第一次是指向最开始的之前的，所以第一次也会先next一次
            styflowerdata[i] = gson.fromJson(e, flowerDataStyle.class);//每次获取一个对象保存
            System.out.println("Flower id: "+styflowerdata[i].getFlower_id() + ";" + styflowerdata[i].getFlower_name() + ";" + styflowerdata[i].getUser_tel());
        }
        ArrayList<flowerDataStyle> styflowerdataList = new ArrayList<>();//为了和之后代码连贯，将对象数组装入ArrayList
        for (int i=0; i<arraySize; i++) {
            styflowerdataList.add(styflowerdata[i]);//依次装入
        }


        //设置存储相关内容的空间变量
        //ArrayList<flowerDataStyle> styflowerdataList = styflowerdata.getArray();
        ArrayList<String> user_tel = new ArrayList<String>();
        ArrayList<String> flower_name = new ArrayList<String>();
        ArrayList<String> flower_id = new ArrayList<String>();
        //将得到的内容拆分存储
        for (int i = 0; i < styflowerdataList.size(); i++) {
            user_tel.add(styflowerdataList.get(i).getUser_tel());
            flower_name.add(styflowerdataList.get(i).getFlower_name());
            flower_id.add(styflowerdataList.get(i).getFlower_id());
        }
        //imgIdArray = new int[]{R.drawable.timg, R.drawable.timg2, R.drawable.timg3};
        //设置对应花朵大小的数组
        int[] imgIdArray = new int[styflowerdataList.size()];
        //存储花朵的信息，用于将其在主界面显示
        //载入图片资源ID
        for (int i = 0; i < styflowerdataList.size(); i++) {
            if (flower_name.get(i) == "ziluolan") {
                imgIdArray[i] = R.drawable.pic_ziluolan;
            } else if (flower_name.get(i) == "xiangrikui") {/////////////////////////////////////////////
                imgIdArray[i] = R.drawable.pic_ziluolan;
            } else {
                imgIdArray[i] = R.drawable.pic_ziluolan;//////////////////////////////////////
            }

        }
        if(imgIdArray.length == 0){
            imgIdArray[0] = R.drawable.main_toshop1;
        }
    /////////////////////////////xx/////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        ViewGroup group = (ViewGroup)findViewById(R.id.viewGroup);/////////////////////////////////////////////////////////////////////
        viewPager = (ViewPager) findViewById(R.id.viewPager);///////////////////////////////////////////////////////////////////////////

        //将点点加入到ViewGroup中
        tips = new ImageView[imgIdArray.length];
        for(int i=0; i<tips.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            tips[i] = imageView;
            if(i == 0){
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);
        }


        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }

        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((mImageViews.length) * 100);
    }

    /**
     *
     * @author xiaanming
     *
     */

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            //((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        @Override
        public Object instantiateItem(View container, int position) {
            //((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            //return mImageViews[position % mImageViews.length];
            try {
                ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            }catch(Exception e){
                //handler something
            }
            return mImageViews[position % mImageViews.length];
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % mImageViews.length);
    }

    /**
     * 设置选中的tip的背景
     * @param selectItems
     */

    private void setImageBackground(int selectItems){
        for(int i=0; i<tips.length; i++){
            if(i == selectItems){
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String get_userflower(final String user_name) {
        final PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        try {
            pipedWriter.connect(pipedReader);

        }catch (Exception e){
            e.printStackTrace();
        }

        //建立连接管道
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    org.apache.http.client.HttpClient httpClient = new org.apache.http.impl.client.DefaultHttpClient();
                    //HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/whatIsGoodFriend/Login?name="+user_name);
                    HttpGet httpGet = new HttpGet("http://" + getResources().getString(R.string.back_supporter_ip) + ":8080/FIAL2_backSupporter/GetUserFlower?tel="+user_name);//"http://172.16.16.59:8080/FIAL2_backSupporter/GetUserFlower?tel="
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        String flowerData = EntityUtils.toString(entity, "utf-8");

                        Gson gson = new Gson();

                        //String flowerData = gson.fromJson(response, String.class);
                        if (flowerData != null) {
                            Log.d("getjson", flowerData);
                            pipedWriter.write(flowerData);
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
            String flowerData = new String(buf, 0, len);
            Log.d("LoginReadBuffer", flowerData);
            return flowerData;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}