package com.example.chenxuanhe.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;
import com.example.chenxuanhe.myapplication.utils.StatusBarCompat;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by chenxuanhe on 2016/4/16.
 */
public class Mycard extends AppCompatActivity {

    private ImageView card_Avatar;
    private TextView card_Name;
    private TextView card_Banlance;
    private ListView card_Listview;
    private ProgressDialog mDialog;
    private SwipeRefreshLayout mswipeLayout;
    private HashMap<String,Object> CardInfo;
    private List<HashMap<String,Object>> CardInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycard_main);


        setTitle("校园卡消费查询");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        card_Avatar = (ImageView)findViewById(R.id.card_Avatar);
        card_Name = (TextView) findViewById(R.id.card_Name);
        card_Banlance = (TextView)findViewById(R.id.card_Balance);
        card_Listview = (ListView) findViewById(R.id.card_Listview);
        mswipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        initAvatar();


        final Map<String,String> getToken = Info.getLoginInfo(Mycard.this);
        getInfo(getToken.get("mToken"));


        /**
         * OnScrollListener的回调方法
         * 在收藏夹关于回调的方法
         * */
        card_Listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                View firstView = view.getChildAt(firstVisibleItem);  //指定刷新第一个view，getChildAT

                if(firstVisibleItem==0&&(firstView==null||firstView.getTop()==0)){
                    mswipeLayout.setEnabled(true);
                }else {
                    mswipeLayout.setEnabled(false);
                }
            }
        });


        /**
         * 下拉刷新的功能
         * 关于SwipeRefreshLayout的下拉刷新
         * */
        mswipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfo(getToken.get("mToken"));
                mswipeLayout.setRefreshing(false);    // 设置刷新加载的icon是否继续显示 那必须为false啦。
            }});
        mswipeLayout.setColorSchemeResources(R.color.yellow,R.color.red,R.color.blue);//下拉icon三色变换

    }


    /**
     * 通过token拉取消费记录
     * 返回error=0无错误
     * 返回error=1有错误
     * 返回error=2   token 有误 则重新登录
     * */
    public void getInfo(final String mToken){
        mDialog = ProgressDialog.show(Mycard.this,"","加载中，请稍候...");
        new Thread(){
            @Override
            public void run(){
                final String result = Netget.getCardInfo(mToken);
                if(result != null){
                    try{

                        JSONObject jsonObject = new JSONObject(result);
                        int error = jsonObject.getInt("error");
                        String message = jsonObject.getString("message");
                        switch (error){
                            case 0:
                                JSONObject jsb = jsonObject.getJSONObject("data");
                                JSONObject info = jsb.getJSONObject("info");
                                final String cardName = info.getString("name");
                                final String cardBalance = info.getString("balance");
                                JSONArray jsonArray =jsb.getJSONArray("data");
                                CardInfos = new ArrayList<>();
                                for (int i = 0;i<jsonArray.length();i++){

                                    JSONObject add = (JSONObject) jsonArray.get(i);
                                    String Trade = add.getString("trade");
                                    String Time = add.getString("time");

                                    CardInfo = new HashMap<>();
                                    CardInfo.put("Trade", "消费" + Trade);
                                    CardInfo.put("Time", "时间" + Time);

                                    CardInfos.add(CardInfo);
                                }
                                initDataCard(cardName,cardBalance);
                                mDialog.dismiss();
                                break;
                            case 1:
                                setToast(message);
                                break;
                            case 2:
                                setToast("登录数据出错，请重新登录~~");
                                Info.deleteUserInfo(Mycard.this);//删除并重新登录
                                final Intent intent =getPackageManager()
                                        .getLaunchIntentForPackage(getPackageName());//返回入口 就是最初的Activity
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//重新开启Activity并在该栈而不创新栈
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 不能再线程用Toast
     * setToast方法用于上面调用
     * @param message
     */
    public void setToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                Toast.makeText(Mycard.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 拉取card的姓名 余额
     * 还有listView上面的信息
     */
    public void initDataCard(final String cardName,final String cardBalance){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleAdapter simpleAdapter = new SimpleAdapter(Mycard.this, CardInfos, R.layout.item_card,
                        new String[]{"Trade", "Time"}, new int[]{R.id.card_Trade, R.id.card_Time});
                card_Name.setText(cardName);
                card_Banlance.setText(cardBalance);
                card_Listview.setAdapter(simpleAdapter);
            }
        });
    }


    /**
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
