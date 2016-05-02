package com.example.chenxuanhe.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by chenxuanhe on 2016/4/16.
 */
public class Myclass extends AppCompatActivity {

    private TextView textView;
    private TextView one;
    private TextView sss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myclass_main);
        setTitle("怎么又是上课呢~");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        textView = (TextView) findViewById(R.id.idd);
        one = (TextView)findViewById(R.id.one);
        sss = (TextView)findViewById(R.id.sss);

        final Map<String,String> getToken = Info.getLoginInfo(Myclass.this);
        getInfo(getToken.get("mToken"));

    }


    /**
     * 用token发起得到数据
     * 然后根据得到的 week比对
     * 当week等于现在的时间则拉取数据到activity
     * 时间不相等则
     * */
    public void getInfo(final String mToken){
        new Thread(){
            @Override
            public void run(){
                final String result = Netget.getClassInfo(mToken);
                if(result!=null){
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                        int error = jsonObject.getInt("error");
                        String message = jsonObject.getString("message");
                        switch (error){
                            case 0:
                                JSONObject jsb = jsonObject.getJSONObject("data");
                                JSONObject info = jsb.getJSONObject("info");
                                JSONObject infos = jsb.getJSONObject("data");
                                JSONObject ones = infos.getJSONObject("1");
                                //取得当前的日期及时间
                                int mYear,mMonth,mDay;
                                Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH)+1;
                                mDay = c.get(Calendar.DAY_OF_MONTH);
                                //在TextView中显示日期及时间
                                textView.setText(new StringBuilder().append(mYear).append("-")
                                        .append(mMonth).append("-")
                                        .append(mDay).append(" "));
                                final String remarks =infos.getString("2");
                                sss.setText(remarks);



                        }

                    }catch (Exception e){
                        e.printStackTrace();}
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


