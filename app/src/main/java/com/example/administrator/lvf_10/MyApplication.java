package com.example.administrator.lvf_10;

import android.app.Application;

/**
 * Created by 冰雪澜风 on 2017/5/13.
 */

public class MyApplication extends Application {

// 程序退出标记

    private static boolean isProgramExit = false;

    public void setExit(boolean exit) {

        isProgramExit = exit;

    }

    public boolean isExit() {

        return isProgramExit;

    }

}
