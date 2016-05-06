package com.example.chenxuanhe.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

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

                tabHost.setSelectedNavigationItem(position);

            }
        });

        /**
         * 插入来自pagerAdapter数据的所有标签
         */
        for (int i = 1; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }

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

        /**
         * 用于更新数据
         * @param index
         * @param data
         */
        public void updata(int index,List<HashMap<String,Object>> data){
           fragments[index].updata(data);
        }

        public Fragment getItem(int num) {
            return new FragmentLesson();
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int temp = position+1;
            return "星期 " + position;
        }

    }

    /**
     * 用于返回主线程并Toast的方法
     *
     */
    public void Toast(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Myclass.this, "读取信息失败，请重新登录", Toast.LENGTH_SHORT).show();
            }
        });
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
