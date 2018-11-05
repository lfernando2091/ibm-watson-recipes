package com.media.doopy.Log4J;

import android.util.Log;

public class Logger {
    private static Class<?> obj;

    public static  void setClass(Class<?> ooo){
        obj = null;
        obj = ooo;
    }

    public static void print(Object msg){
        print(obj, "" + msg);
    }

    public static void error(Object msg){
        error(obj, "" + msg);
    }

    public static void warn(Object msg){
        warn(obj,"" + msg);
    }

    private static void print(Class<?> obj, String msg){
        Log.i(obj.getSimpleName(), msg);
    }

    private static void error(Class<?> obj, String msg){
        Log.e(obj.getSimpleName(), msg);
    }

    private static void warn(Class<?> obj, String msg){
        Log.w(obj.getSimpleName(), msg);
    }
}
