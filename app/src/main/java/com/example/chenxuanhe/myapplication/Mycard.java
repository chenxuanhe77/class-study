package com.example.chenxuanhe.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;

import java.util.Map;

/**
 * Created by chenxuanhe on 2016/4/16.
 */
public class Mycard extends AppCompatActivity {

    private ImageView card_Avatar;
    private TextView card_Name;
    private TextView card_Banlance;
    private SwipeRefreshLayout swipeLayout;
    private ListView card_Listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycard_main);


        setTitle("校园卡消费查询");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initAvatar();

        card_Avatar = (ImageView)findViewById(R.id.card_Avatar);


    }


    /**
     * 姓名   显示 从本地拉取
     * 校园卡记录新现成获取头像
     * 放到bitmap中
     * */
    public void initAvatar(){
        final Map<String,String> userinfo = Info.getUserInfo(Mycard.this);
        new Thread(){
            @Override
            public void run(){
                try{
                    byte [] Avatar = Netget.getUserAvatar(userinfo.get("infoAvatar"));
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(Avatar,0,Avatar.length);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            card_Avatar.setImageBitmap(bitmap);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Mycard.this,"亲，拉取头像失败了呦，嚯嚯",Toast.LENGTH_SHORT).show();
                }
            }
        }.start();

    }

    /**
     * 用于actionbar返回页面功能
     *
     * */
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
