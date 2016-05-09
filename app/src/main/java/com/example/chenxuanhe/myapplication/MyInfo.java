package com.example.chenxuanhe.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;
import com.example.chenxuanhe.myapplication.utils.StatusBarCompat;
import java.util.Map;

/**
 * Created by chenxuanhe on 2016/4/22.
 */
public class MyInfo extends AppCompatActivity{

    private TextView myName;
    private  TextView myId;
    private TextView myQQ;
    private TextView myTel;
    private ImageView myAvatar;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_main);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myAvatar  = (ImageView)findViewById(R.id.info_Avatar);
        myName = (TextView) findViewById(R.id.info_Name);
        myId = (TextView) findViewById(R.id.info_Id);
        myQQ = (TextView) findViewById(R.id.info_QQ);
        myTel = (TextView) findViewById(R.id.info_Tel);

        StatusBarCompat.setStatusBarColor(this);
        pushInfo();
    }

    /**
     * 用于ACT显示个人信息
     * 从Info工具类里面拉去缓存信息
     * */
    public void pushInfo(){
        final Map<String,String> userinfo = Info.getUserInfo(MyInfo.this);
        myId.setText(userinfo.get("infoID"));
        myName.setText(userinfo.get("infoName"));
        myQQ.setText(userinfo.get("infoQQ"));
        myTel.setText(userinfo.get("infoTell"));

      /**
       * 在新线程联网获取图片
       * 将图片取下来放到bipmap里面
       * */
        new Thread(){
            public void run(){
                try{
                    byte [] Avatar = Netget.getUserAvatar(userinfo.get("infoAvatar"));
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(Avatar,0,Avatar.length);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myAvatar.setImageBitmap(bitmap);
                        }
                    });
                }catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyInfo.this,"图片读取失败，请重新登录（可能是网络问题喔~）",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }.start();

    }

    /**
     * 用于界面返回按钮
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
