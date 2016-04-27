package com.example.chenxuanhe.myapplication.utils;


import com.example.chenxuanhe.myapplication.StreamTools;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by chenxuanhe on 2016/4/22.
 */
public class Netget {

    final static String BaseURL = "http://api.13550101.com/";

    /**
     * 封装网络
     * 连接
     **
     * */
    public static String NetConn(String URL){
        try{
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.getResponseCode();
            conn.setConnectTimeout(5000);
            int code =conn.getResponseCode();
            if(code ==200){
                InputStream is = conn.getInputStream();
                return StreamTools.readInputStream(is);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }



    /**
     * 用于获取头像
     * @param URL
     * @return
     * @throws Exception
     */
    public static byte[] getUserAvatar(String URL) throws Exception {

        URL url = new URL(URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        int responseCode=conn.getResponseCode();
        if (responseCode == 200) {
            InputStream is = conn.getInputStream();
            return StreamTools.readImageInputStream(is);
        } else {
            return null;
        }
    }

    /**
     * 获取个人信息
     * @param mToken
     * @return
     */
    public static String getUserInfo(String mToken){
        try{
            String URL = BaseURL+"user/info?token="+
                    URLEncoder.encode(mToken, "UTF-8");
            return  Netget.NetConn(URL);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }






}
