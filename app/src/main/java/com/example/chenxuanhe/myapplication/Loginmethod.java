package com.example.chenxuanhe.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by chenxuanhe on 2016/4/16.
 */
public class Loginmethod {

    public static String loginget(String username, String password) {
        try {
            String visit = "http://wifi.13550101.com/app/token?username="
                    + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8");
            URL url = new URL(visit);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String str = StreamTools.readInputStream(is);
                return str;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}