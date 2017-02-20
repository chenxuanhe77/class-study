package com.example.chenxuanhe.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxuanhe on 2016/4/15.
 */
public class Login extends AppCompatActivity {


    @BindView(R.id.login)
    CardView mlogin;
    @BindView(R.id.textView_login)
    TextView textView_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        ButterKnife.bind(this);
        setTitle("登录");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        init();
    }


    /**
     * 登录界面MenuItem
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_settings:
                Toast.makeText(Login.this, "this is setting", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 增加可选项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(1, 1, 1, "调皮~");
        return true;
    }

    /**
     * 更改登录按钮软键盘登录方法
     */
    public void init() {
        final EditText id_username = (EditText) findViewById(R.id.id_username);
        final EditText id_password = (EditText) findViewById(R.id.id_password);
        /**
         * 用户名软键盘enter键更改为：下一个
         */
        id_username.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        id_username.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || (keyEvent != null && keyEvent.
                        getKeyCode() == keyEvent.KEYCODE_ENTER)) {
                    id_password.setFocusable(true);            //
                    id_password.setFocusableInTouchMode(true);//
                    id_password.requestFocus();               //这三行，让密码框自动获取焦点
                    return true;
                }

                return false;
            }
        });

        /**
         * 密码软键盘enter键更改为：发送
         */
        id_password.setImeOptions(EditorInfo.IME_ACTION_SEND);
        id_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.
                        getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // onClickButton(mlogin);//这个方法之点击两次，但是没有点击音效
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(id_password.getWindowToken(), 0);
                    mlogin.performClick();  //这个方法就会点击两次
                    return true;
                }
                return false;
            }
        });

    }


    /**
     * 通过token发情网络请求请求数据
     * 解析json
     * 显示到侧滑框
     */
    public void onClickButton(View view) {

        final EditText id_username = (EditText) findViewById(R.id.id_username);
        final EditText id_password = (EditText) findViewById(R.id.id_password);
        final String username = id_username.getText().toString().trim();
        final String password = id_password.getText().toString().trim();


        //登陆按钮点击一次将不能点击。显示为Loading...
        mlogin.setClickable(false);
        textView_Login.setText("登陆中...");
        // mlogin.setText("loading...");

        new Thread() {
            public void run() {
                final String result = LoginService.loginByGet(username, password);
                if (result != null) {
                    try {
                        JSONTokener jsonTokener = new JSONTokener(result);
                        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                        final String message = jsonObject.getString("message");
                        if (jsonObject.getInt("error") == 0) {
                            final String token = jsonObject.getString("token");
                            boolean SaveSuccess = saveUserInfo(Login.this,token);
                            if (SaveSuccess) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(Login.this, ""+token, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.setClass(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        } else {
                                setToast(message);
                                mlogin.setClickable(true);
                                textView_Login.setText("登陆");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }


    /**
     * 退出按钮响应事件
     * onClickLogout
     */
    public void onClickLogout(View v) {
        //System.exit(0);

        finish();
    }

    /**
     * 封装的一个Toast方法
     * 可以返回主线程Toast
     */
    public void setToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 保存Token
     **/
    private static boolean saveUserInfo(Context context,  String mToken) {
        SharedPreferences wc = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = wc.edit();
        edit.putString("mToken", mToken);
        edit.apply();
        return true;
    }

}

