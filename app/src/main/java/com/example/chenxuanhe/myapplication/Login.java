package com.example.chenxuanhe.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by chenxuanhe on 2016/4/15.
 */
public class Login extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        setTitle("登录");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }



    /**
     * 登录界面MenuItem
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_settings:
                Toast.makeText(Login.this,"this is setting",Toast.LENGTH_SHORT).show();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 增加可选项
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(1, 1, 1, "item1");
        return true;
    }


      /**
       * 通过token发情网络请求请求数据
       * 解析json
       * 显示到侧滑框
       * */
    public void onClickButton(View view){
        final TextView id_username = (TextView) findViewById(R.id.id_username);
        TextView id_password = (TextView) findViewById(R.id.id_password);

        final String username=id_username.getText().toString().trim();
        final String password=id_password.getText().toString().trim();
        new Thread(){
            public void run(){
                final String result = LoginService.loginByGet(username,password);
                if(result != null) {

                    try{
                        JSONTokener jsonTokener = new JSONTokener(result);
                        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                        if(jsonObject.getInt("error")==0){
                            String token= jsonObject.getString("token");
                            boolean SaveSuccess = saveUserInfo(Login.this, username, token);
                            if(SaveSuccess){

                                runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.setClass(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }}
                        else{if(jsonObject.getInt("error")==1){
                            final String message = jsonObject.getString("message");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this,message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     *保存Token
     **/
    private static boolean saveUserInfo(Context context,String mID,String mToken) {
        SharedPreferences wc= context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit= wc.edit();
        edit.putString("mID",mID);
        edit.putString("mToken", mToken);
        edit.commit();
        return true;
    }
}

