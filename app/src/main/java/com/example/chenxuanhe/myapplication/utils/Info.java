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
