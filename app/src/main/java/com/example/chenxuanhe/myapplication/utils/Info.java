package com.example.chenxuanhe.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxuanhe on 2016/4/22.
 */
public class Info {

    /**
     * 用于删除token
     * 删除信息重新登录
     * */
    public static boolean deleteUserInfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = wc.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    /**
     * 保存成绩全部信息数据的方法
     * @param context
     * @param mark
     * @return
     */
    public static boolean saveMarkInfo(Context context,String mark){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = wc.edit();
        editor.putString("MarkInfo",mark);
        editor.apply();
        return true;
    }

    /***
     * 保存课表返回的数据，用于没有网络的时候去解析
     * @param context
     * @param classInfo
     * @return
     */
    public static boolean saveClassInfo(Context context,String classInfo){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = wc.edit();
        editor.putString("ClassInfo",classInfo);
        editor.apply();
        return true;
    }

    /**
     * 获得成绩信息的方法
     * 全部数据存在本地整条message
     * @param context
     * @return
     */
    public static Map<String, String> getMarkInfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String markInfo = wc.getString("MarkInfo",null);
        Map<String,String> map = new HashMap<>();
        map.put("MarkInfo",markInfo);
        return map;
    }

    /**
     * 获取课表信息，在没网络的时候去解析
     * @param context
     * @return
     */
    public static Map<String, String> getClassInfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String classInfo = wc.getString("ClassInfo",null);
        Map<String,String> map = new HashMap<>();
        map.put("ClassInfo",classInfo);
        return map;
    }



    /**
     * 读取登录的token实现不注销就免登录
     * @param context
     * @return
     */
    public static Map<String,String> getLoginInfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String mToken = wc.getString("mToken", null);
        Map<String,String> map = new HashMap<>();
        map.put("mToken", mToken);
        return map;
    }

    /**
     * 用于读取个人档案
     * */
    public static Map<String,String>getUserInfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String mName = wc.getString("infoName", null);
        String mCollger = wc.getString("infoCollger", null);
        String mMajor = wc.getString("infoMajor",null);
        String mCity = wc.getString("infoCity",null);
        String mId = wc.getString("infoId", null);
        Map<String,String> userMap = new HashMap<>();
        userMap.put("infoName", mName);
        userMap.put("infoCollger", mCollger);
        userMap.put("infoMajor", mMajor);
        userMap.put("infoCity", mCity);
        userMap.put("infoId", mId);
        return userMap;
    }

    /**
     * 保存个人档案
     *
     *
     * * */
    public static boolean saveUserInfo(Context context,String mName,String mCollger,
                                       String mMajor,String mCity,String mId){
        SharedPreferences wc = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = wc.edit();
        editor.putString("infoName", mName);
        editor.putString("infoCollger",mCollger);
        editor.putString("infoMajor", mMajor);
        editor.putString("infoCity", mCity);
        editor.putString("infoId", mId);
        editor.apply();
        return  true;
    }

}
