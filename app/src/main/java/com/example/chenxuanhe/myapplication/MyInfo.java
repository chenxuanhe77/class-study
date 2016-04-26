package com.example.chenxuanhe.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxuanhe on 2016/4/22.
 */
public class MyInfo extends AppCompatActivity{


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_main);

        ImageView head = (ImageView)findViewById(R.id.stu_avatar);
        TextView name = (TextView) findViewById(R.id.stu_name);
        TextView id = (TextView) findViewById(R.id.stu_id);
        TextView QQ = (TextView) findViewById(R.id.stu_QQ);
        TextView Tell = (TextView) findViewById(R.id.stu_TEL);
       
    }

    public static Map<String,String>  getMyinfo(Context context){
        SharedPreferences wc = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String id = wc.getString("infoID", null);
        String mQQ = wc.getString("infoQQ", null);
        String mTell = wc.getString("infoTell",null);
        String mName = wc.getString("infoName",null);
        String mAvatar = wc.getString("infoAvatar", null);
        Map<String,String> userMap = new HashMap<>();
        userMap.put("infoID",id);
        userMap.put("infoName", mName);
        userMap.put("infoQQ", mQQ);
        userMap.put("infoTell", mTell);
        userMap.put("infoAvatar",mAvatar);
        return userMap;
    }


}
