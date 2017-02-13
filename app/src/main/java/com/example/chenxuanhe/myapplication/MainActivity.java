package com.example.chenxuanhe.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;
import com.example.chenxuanhe.myapplication.utils.StatusBarCompat;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private String APPKEY = "12ae915419880";
    private static final int newTime = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        MobAPI.initSDK(this, APPKEY);

        StatusBarCompat.translucentStatusBar(MainActivity.this,true);

        //腾讯信鸽推送
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

        // 获取API实例，请求湘潭的所有有关数据
        Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
        api.queryByCityName("湘潭", new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                Object result = map.get("result").toString();
                //调用该方法，用于显示数据
                onWeatherDisplay(map);
                Log.i("xxx", "" + result);
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {
                Log.i("xx", "ERROR");
            }
        });


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
                this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        getTime();//显示时间

        //方法，使左边侧滑百分之40面积能感触到侧滑
        setDrawerLeftEdgeSize(MainActivity.this, mDrawerLayout, 0.4f);

        getInfomToken();


    }


    /**
     * 完成首页时间的的显示
     */
    public void getTime() {

        TextView mNowTime = (TextView) findViewById(R.id.nowTime);
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm:ss");
        mNowTime.setText(dateformat.format(date));
        new TimeThread().start();//开启这个线程
    }


    /**
     * 内部类，开启线程，实现时间刷新，每秒刷新一次
     */
    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.what = newTime;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    /**
     * 同上作用。一个handle传递刷新信息
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case newTime:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat sptime = new SimpleDateFormat("hh:mm:ss");
                    TextView mNowTime = (TextView) findViewById(R.id.nowTime);
                    mNowTime.setText(sptime.format(date));
                    break;
                default:
                    break;
            }
        }

    };


    /**
     * 用于提示是否得到token
     */
    public void getInfomToken() {
        Map<String, String> loginInfo = Info.getLoginInfo(MainActivity.this);
        if (loginInfo != null) {
            if (loginInfo.get("mToken") != null) {
                getInfo(loginInfo.get("mToken"));
                Toast.makeText(MainActivity.this, "亲，读到你的信息了呦！", Toast.LENGTH_SHORT).show();
            } else {
                doIntent(Login.class);
                finish();
            }
        } else {
            Toast.makeText(MainActivity.this, "未读取到任何信息，请重新登录喔~", Toast.LENGTH_SHORT).show();
           // Info.deleteUserInfo(MainActivity.this);
            doIntent(Login.class);
            finish();
        }

    }

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
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, R.string.what);
        return true;
    }

    /**
     * 点击MenuItem的事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 1) {
            Toast.makeText(MainActivity.this, "抱歉暂时想不到能用来点击干嘛喔~", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 用于每个Activity的跳转
     */
    @SuppressWarnings("StatementWithEmptyBody")//对警告保持缄默
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.id_mycard) {
            doIntent(Mycard.class);
        } else if (id == R.id.id_myclass) {
            doIntent(Myclass.class);
        } else if (id == R.id.id_searchcard) {
            doIntent(MyMark.class);
        } else if (id == R.id.id_mysetting) {
            doIntent(Mysetting.class);
        } else if (id == R.id.nav_logoff) {
            Info.deleteUserInfo(this);
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "请输入您的账号密码吧~咻", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            System.exit(0);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 用于跳转方法
     */
    public void doIntent(Class fbi) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, fbi);
        startActivity(intent);
    }


    /**
     * 点击头像显示个人信息
     * QQ Tell Name Avatar
     */
    public void onClickImage(View view) {
        doIntent(MyInfo.class);
    }

    /**
     * 侧滑框
     * 获得信息
     */
    public void getInfo(final String mToken) {
        View view = mNavigationView.inflateHeaderView(R.layout.nav_header_main);
      //  final ImageView mAvatar = (ImageView) view.findViewById(R.id.id_imageView);
        final TextView mId = (TextView) view.findViewById(R.id.id_id);
        final TextView mName = (TextView) view.findViewById(R.id.id_name);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            Toast.makeText(getApplicationContext(), "网络不可用~", Toast.LENGTH_SHORT).show();
        } else {

            /**
             * 开启一个新线程联网
             * 获取侧滑框的信息
             * */
            new Thread() {
                public void run() {
                    final String result = Netget.getUserInfo(mToken);
                    Log.d("AAA","adadadadadada");
                    if (result != null) {
                        try {
                            JSONTokener jsonTokener = new JSONTokener(result);
                            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();

                            if (jsonObject.getInt("error") == 0) {
                                JSONObject outData = jsonObject.getJSONObject("data");//获得外层data
                                JSONArray date = outData.getJSONArray("data");
                                for(int i = 0;i<date.length();i++){
                                    JSONObject data = date.getJSONObject(i);
                                    String name = data.getString("name");
                                    String college = data.getString("college");
                                    String major = data.getString("major");
                                    String city = data.getString("city");
                                    String id = data.getString("sid");

                                    boolean isSaveSuccess = Info.saveUserInfo(MainActivity.this, name,
                                            college, major, city, id);
                                    if (isSaveSuccess){
                                        /**返回主线程*/

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Map<String, String> userinfo = Info.getUserInfo(MainActivity.this);
                                           //     mAvatar.setImageBitmap(bitmap);
                                                mId.setText(userinfo.get("infoId"));
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
                             //   JSONObject object = new JSONObject(result);
                            //    String infoAvatar = object.getString("avatar");
                            //    String infoID = object.getString("xuehao");
                           //     String infoQQ = object.getString("QQ");
                             //   String infoTell = object.getString("tel");
                              //  String infoName = object.getString("name");
                           //     byte[] Avatar = Netget.getUserAvatar(infoAvatar);
                            //    final Bitmap bitmap = BitmapFactory.decodeByteArray(Avatar, 0, Avatar.length);

                            }else if(jsonObject.getInt("error")==1){
                                doIntent(Login.class);
                            }else if(jsonObject.getInt("error")==2){
                                doIntent(Login.class);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    /**
     * 用于TextView获取接口数据显示
     *
     * @param result
     */
    private void onWeatherDisplay(Map<String, Object> result) {
        TextView mWeek = (TextView) findViewById(R.id.week);
        TextView mDate = (TextView) findViewById(R.id.date);
        TextView mProvince = (TextView) findViewById(R.id.province);
        TextView mCity = (TextView) findViewById(R.id.city);
        TextView mDayTime = (TextView) findViewById(R.id.dayTime);
        TextView mNight = (TextView) findViewById(R.id.night);
        TextView mColdIndex = (TextView) findViewById(R.id.coldIndex);
        TextView mDressingIndex = (TextView) findViewById(R.id.dressingIndex);
        TextView mExerciseIndex = (TextView) findViewById(R.id.exerciseIndex);
        TextView mTemperature = (TextView) findViewById(R.id.temperature);
        TextView mHumidity = (TextView) findViewById(R.id.humidity);
        TextView mWind = (TextView) findViewById(R.id.wind);
        TextView mSunrise = (TextView) findViewById(R.id.sunrise);
        TextView mSunset = (TextView) findViewById(R.id.sunset);
        TextView mWeather = (TextView) findViewById(R.id.weather);
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) result.get("result");
        HashMap<String, Object> weather = results.get(0);

        ArrayList<HashMap<String, Object>> futures = (ArrayList<HashMap<String, Object>>) weather.get("future");
        HashMap<String, Object> day = futures.get(0);

        mDate.setText(com.mob.tools.utils.R.toString(weather.get("date")));
        mWeek.setText(com.mob.tools.utils.R.toString(weather.get("week")));
        mProvince.setText(com.mob.tools.utils.R.toString(weather.get("province")));
        mCity.setText(com.mob.tools.utils.R.toString(weather.get("city")));
        mColdIndex.setText(com.mob.tools.utils.R.toString(weather.get("coldIndex")));
        mDressingIndex.setText(com.mob.tools.utils.R.toString(weather.get("dressingIndex")));
        mExerciseIndex.setText(com.mob.tools.utils.R.toString(weather.get("exerciseIndex")));
        mHumidity.setText(com.mob.tools.utils.R.toString(weather.get("humidity")));
        mWind.setText(com.mob.tools.utils.R.toString(weather.get("wind")));
        mSunrise.setText(com.mob.tools.utils.R.toString(weather.get("sunrise")));
        mSunset.setText(com.mob.tools.utils.R.toString(weather.get("sunset")));
        mWeather.setText(com.mob.tools.utils.R.toString(weather.get("weather")));

        mDayTime.setText(com.mob.tools.utils.R.toString(day.get("dayTime")));
        mNight.setText(com.mob.tools.utils.R.toString(day.get("night")));
        mTemperature.setText(com.mob.tools.utils.R.toString(day.get("temperature")));

        String weath = mWeather.getText().toString();

        getweather(weath);
    }


    /**
     * 对不同天气类型给与不同图片显示
     *
     * @param weath
     */
    public void getweather(String weath) {
        ImageView mbjtu = (ImageView) findViewById(R.id.bjtu);

        if (weath != null) {
            switch (weath) {
                case "晴":
                    mbjtu.setImageResource(R.drawable.qing);
                    break;
                case "多云":
                    mbjtu.setImageResource(R.drawable.duoyun);
                    break;
                case "少云":
                    mbjtu.setImageResource(R.drawable.shaoyun);
                    break;
                case "阴":
                    mbjtu.setImageResource(R.drawable.yin);
                    break;
                case "小雨":
                    mbjtu.setImageResource(R.drawable.xiaoyu);
                    break;
                case "雨":
                    mbjtu.setImageResource(R.drawable.yu);
                    break;
                case "雷雨":
                    mbjtu.setImageResource(R.drawable.leiyu);
                    break;
                case "零散雷雨":
                    mbjtu.setImageResource(R.drawable.leiyu);
                    break;
                case "中雨":
                    mbjtu.setImageResource(R.drawable.zhongyu);
                    break;
                case "阵雨":
                    mbjtu.setImageResource(R.drawable.zhenyu);
                    break;
                case "零散阵雨":
                    mbjtu.setImageResource(R.drawable.zhenyu);
                    break;
                case "小雪":
                    mbjtu.setImageResource(R.drawable.xiaoxue);
                    break;
                case "雨夹雪":
                    mbjtu.setImageResource(R.drawable.yujiaxue);
                    break;
                case "阵雪":
                    mbjtu.setImageResource(R.drawable.zhenxue);
                    break;
                case "霾":
                    mbjtu.setImageResource(R.drawable.mai);
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(MainActivity.this, "没有接到到天气的数据类型喔0.0~", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 改变侧滑栏的触摸滑动范围
     * 三个参数
     */
    public static void setDrawerLeftEdgeSize(Activity activity,
                                             DrawerLayout drawerLayout,
                                             float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger,
                    Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (Exception e) {
            // ignore
        }
    }

}
