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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickImage(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,MyInfo.class);
        startActivity(intent);
    }

    public void getInfo(final String mToken){
        final ImageView mAvatar = (ImageView)findViewById(R.id.id_imageView);
        final TextView mId  = (TextView)findViewById(R.id.id_id);
        final TextView mName = (TextView)findViewById(R.id.id_name);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isAvailable()){
            Toast.makeText(getApplicationContext(),"网络不可用",Toast.LENGTH_SHORT).show();
        }else{
            new Thread(){
                public void  run(){
                    final String result = Info.getUserInfo(mToken);
                    if(result!=null){
                        try{
                            JSONTokener jsonTokener = new JSONTokener(result);
                            JSONObject jsonObject = (JSONObject)  jsonTokener.nextValue();
                            if(jsonObject.getInt("error")==0){
                                JSONObject object = new JSONObject(result).getJSONObject("data");
                                String Avatar = object.getString("avatar");
                                byte[] getUserAvatar = Netget.getUserAvatar();
                                final Bitmap bitmap = BitmapFactory.decodeByteArray(Avatar,0,Avatar.length());
                                boolean isSaveSuccess
                                if(isSaveSuccess){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mAvatar.setImageBitmap(bitmap);
                                            mId.setText(userInfo.get("id"));
                                            mName.setText(userInfo.get("Name"));

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
