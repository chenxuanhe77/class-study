package com.example.chenxuanhe.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by chenxuanhe on 2016/4/16.
 */
public class StreamTools {
    public static  String readInputStream(InputStream is){
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int len=0;
            byte[] buffer = new byte[1024];
            while((len=is.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            is.close();
            baos.close();
            byte[] result = baos.toByteArray();
            String temp = new String(result);

            return temp;
        }catch (Exception e){
            e.printStackTrace();
            return "获取失败";
        }
    }

    public static byte[] readImageInputStream(InputStream is)throws Exception{

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len =0;
        while((len=is.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        is.close();
        return outputStream.toByteArray();


    }


}
