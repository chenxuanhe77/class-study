package com.example.chenxuanhe.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        /**
         * 点击右下角fab事件
         * */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "大兄弟，你就不能不点这个么，阿西吧！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);


    Map<String,String> loginInfo = Info.getLoginInfo(MainActivity.this);
    if(loginInfo!=null){
        if (loginInfo.get("mToken")!=null){
            getInfo(loginInfo.get("mToken"));
            Toast.makeText(MainActivity.this,"亲，读到你的信息了呦！",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Login.class);
            startActivity(intent);
            finish();
        }
    }else {
        Toast.makeText(MainActivity.this,"未读取到任何信息，请重新登录喔i",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Login.class);
        startActivity(intent);
        finish();
    }}





    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
     /**
      * 用于创建MenuIItem
      * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 点击MenuItem的事件
     *
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.id_mycard) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Mycard.class);
            startActivity(intent);
        } else if (id == R.id.id_myclass) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Myclass.class);
            startActivity(intent);

        } else if (id == R.id.id_searchcard) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Searchcard.class);
            startActivity(intent);

        } else if (id == R.id.id_mysetting) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Mysetting.class);
            startActivity(intent);

        } else if (id == R.id.nav_logoff) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Login.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

           finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
        /**
         * 点击头像显示个人信息
         * QQ Tell Name Avatar
         */
    public void onClickImage(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,MyInfo.class);
        startActivity(intent);
    }
         /**
          * 侧滑框
          * 获得信息
          * */
    public void getInfo(final String mToken){
        View view = mNavigationView.inflateHeaderView(R.layout.nav_header_main);
        final ImageView mAvatar = (ImageView)view.findViewById(R.id.id_imageView);
        final TextView mId  = (TextView)view.findViewById(R.id.id_id);
        final TextView mName = (TextView)view.findViewById(R.id.id_name);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isAvailable()){
            Toast.makeText(getApplicationContext(),"网络不可用",Toast.LENGTH_SHORT).show();
            Map<String,String> userinfo = Info.getUserInfo(MainActivity.this);
        }else{
            new Thread(){
                public void  run(){
                    final String result = Netget.getUserInfo(mToken);
                    if(result!=null){
                        try{
                            JSONTokener jsonTokener = new JSONTokener(result);
                            JSONObject jsonObject = (JSONObject)  jsonTokener.nextValue();
                            if(jsonObject.getInt("error")==0){
                                JSONObject object = new JSONObject(result);
                                String infoAvatar = object.getString("avatar");
                                String infoID = object.getString("xuehao");
                                String infoQQ = object.getString("QQ");
                                String infoTell = object.getString("tel");
                                String infoName = object.getString("name");
                                boolean isSaveSuccess = Info.saveUserInfo(MainActivity.this, infoID,
                                        infoName,infoTell,infoAvatar,infoQQ);
                                byte[] Avatar = Netget.getUserAvatar(infoAvatar);
                                final Bitmap bitmap = BitmapFactory.decodeByteArray(Avatar,0,Avatar.length);
                                if(isSaveSuccess){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Map<String,String> userinfo = Info.getUserInfo(MainActivity.this);
                                            mAvatar.setImageBitmap(bitmap);
                                            mId.setText(userinfo.get("infoID"));
                                            mName.setText(userinfo.get("infoName"));
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "个人档案并没有保存成功嚯！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }catch (Exception e){e.printStackTrace();}

                    }
                }
            }.start();


        }


    }

}
