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
    * 保存个人档案
     *
     *
     * * */

    public static boolean saveUserInfo(Context context,String mID,String mName,
                                       String mTell,String mAvatar,String mQQ){
        SharedPreferences wc = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = wc.edit();
        editor.putString("infoID",mID);
        editor.putString("infoName", mName);
        editor.putString("infoTell", mTell);
        editor.putString("infoQQ", mQQ);
        editor.putString("infoAvatar", mAvatar);
        editor.commit();
        return  true;
    }


    /**
     * 用于删除token
     * 删除信息重新登录
     * */
    public static boolean deleteUserInfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = wc.edit();
        editor.clear();
        editor.commit();
        return true;
    }


    /**
     * 用于读取个人档案
     * */
    public static Map<String,String>getUserInfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String mID = wc.getString("infoID", null);
        String mQQ = wc.getString("infoQQ", null);
        String mTell = wc.getString("infoTell",null);
        String mName = wc.getString("infoName",null);
        String mAvatar = wc.getString("infoAvatar", null);
        Map<String,String> userMap = new HashMap<>();
        userMap.put("infoID", mID);
        userMap.put("infoQQ", mQQ);
        userMap.put("infoTell", mTell);
        userMap.put("infoName", mName);
        userMap.put("infoAvatar", mAvatar);
        return userMap;
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



}
