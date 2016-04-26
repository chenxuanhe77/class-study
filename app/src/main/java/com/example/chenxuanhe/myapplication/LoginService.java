package com.example.chenxuanhe.myapplication;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by chenxuanhe on 2016/4/19.
 */
public class LoginService {
    //使用get方法提交数据
    public static String loginByGet(String username,String password){
        try{
            String visit ="http://api.13550101.com/login/token?username="
                    + URLEncoder.encode(username,"UTF-8")
                    +"&password="
                    +URLEncoder.encode(password,"UTF-8");
            URL url = new URL(visit);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code=conn.getResponseCode();
            if(code==200){
                InputStream is = conn.getInputStream();
                String text = StreamTools.readInputStream(is);
                return text;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
     return null;
    }

}
