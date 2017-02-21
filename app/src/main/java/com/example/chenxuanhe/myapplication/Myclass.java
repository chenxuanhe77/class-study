package com.example.chenxuanhe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by neokree on 30/12/14.
 */
public class Myclass extends AppCompatActivity implements MaterialTabListener {

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;

    private List[] WeekCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myclass_main);

        setTitle("怎么又是上课呢~");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager );

        //  适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //当用户在选定的标签更改的重击
                tabHost.setSelectedNavigationItem(position);
                if(WeekCourse!=null){
                    adapter.update(position, WeekCourse[position]);
                }
            }
        });

        /**
         * 插入来自pagerAdapter数据的所有标签
         */
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        Calendar calendar = Calendar.getInstance();
        int temp = calendar.get(Calendar.DAY_OF_WEEK)-2;
        pager.setCurrentItem(temp);
        Map<String,String> LoginInfo = Info.getLoginInfo(Myclass.this);

        getLesson(LoginInfo.get("mToken"));
    }

    /**
     * 用于token发请求拉取信息
     * 得到返回的message
     * */
    public String  getLesson(final String mToken){
        new Thread(){
            public void run(){
                String result = Netget.getClassInfo(mToken);
                if(result!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int error = jsonObject.getInt("error");
                        String message = jsonObject.getString("message");
                        switch(error){
                            case 0:
                                getInfo(jsonObject);
                                break;
                            case 1:
                                setToast(message);
                                break;
                            case 2:
                                setToast("账号异常，请重新登录！");
                                relogin();
                                break;
                            default:
                                break;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    setToast("信息有误，请重新登录");
                }
            }
        }.start();
        return null;
    }

    /**
     * 用来获取课程表信息细节
     * 课程。时间。教室。节数
     * 用Json来解析数据
     * @param jsonObject
     */
    public void getInfo(JSONObject jsonObject){
        try{
            JSONObject object = jsonObject.getJSONObject("data");
            JSONObject data = object.getJSONObject("data");
            WeekCourse = new List[7];
            for(int i = 1; i <= 7 ;i ++){
                WeekCourse[i -1] = new ArrayList<>();
                JSONObject day = data.getJSONObject("" + i);
                List<HashMap  <String,Object>> DayClass = new ArrayList<>();
                for(int  n=1;n <=5;n ++){
                    JSONArray lesson = day.getJSONArray(""+n);

                    for(int m= 0; m<lesson.length();m++){
                        JSONObject datas = (JSONObject) lesson.get(m);
                        String name = datas.getString("course");
                        String time = datas.getString("time");
                        String room = datas.getString("classroom");

                        HashMap<String,Object> ClassInfo = new HashMap<>();
                        ClassInfo.put("Num",n);
                        ClassInfo.put("Name",name);
                        ClassInfo.put("Time",time);
                        ClassInfo.put("Room",room);

                        DayClass.add(ClassInfo);
                    }
                }
                WeekCourse[i-1] = DayClass;
            }

            Log.d("cxh","getInfo:data end");
            for (int i = 0;i <7;i++){
                final int finalI = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.update(finalI, WeekCourse[finalI]);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用于返回主线程并Toast的方法
     *
     */
    public void setToast(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Myclass.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 用于token出现错误，删除token并且重新登录获取token
     */
    public void relogin(){
      //  Info.deleteUserInfo(Myclass.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(Myclass.this,Login.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private FragmentLesson[] fragments;

        /**
         * 用于重复调用7个fragment
         * @param fm
         */
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new FragmentLesson[7];
            for(int i=0;i<7;i++){
                fragments[i] = new FragmentLesson();
            }
        }


        public Fragment getItem(int num) {
            return fragments[num];
        }

        @Override
        public int getCount() {
            return 7;
        }

        /**
         *position 位置 是从0开始  所以让星期+1
         */
        @Override
        public CharSequence getPageTitle(int position) {
            int temp = position+1;
            return "星期 " + temp;
        }
        /**
         * 用于更新数据
         * @param index
         * @param data
         */
        public void update(int index,List<HashMap<String,Object>> data){
            fragments[index].update(data);
        }

    }

    /**
     * 用于界面的左上角返回按钮
     * @param item
     * @return
     */
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
