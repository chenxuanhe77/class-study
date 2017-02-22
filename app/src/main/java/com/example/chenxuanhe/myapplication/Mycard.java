package com.example.chenxuanhe.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.Netget;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by chenxuanhe on 2016/4/16.
 */
public class Mycard extends AppCompatActivity {

    private ImageView card_Avatar;
    private TextView card_Status;
    private TextView card_Id;
    private TextView card_Time;
    private TextView card_Name;
    private TextView card_Banlance;
    private TextView card_back_massage;
    private ListView card_Listview;
    private ProgressDialog mDialog;
    private SwipeRefreshLayout mswipeLayout;
    private HashMap<String, Object> CardInfo;
    private List<HashMap<String, Object>> CardInfos;

    private FrameLayout bigFrameLayout;
    private FrameLayout FrameLayout1;
    private FrameLayout FrameLayout2;
    private AnimatorSet mLeftInSet;
    private AnimatorSet mRightOutSet;
    private boolean mIsShowBack;

    private List<String> a = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycard_main);

        //    a.add("0");//List从0开始，随机数不包括12，所以随机数12以内，0-11，为12个随机数，对于list数据
        a.add("今天运气无可阻挡，好运值爆棚呦！");
        a.add("多注意异性，说不定桃花就来了！");
        a.add("今天有笔横财要发，嚯嚯！");
        a.add("学会保持微笑，好运就来了！");
        a.add("今天可得小心呦，容易发生尴尬的事情！");
        a.add("今天是3000年来你最好运的一天~");
        a.add("今天适宜逃单，厉害了，咻咻~");
        a.add("不要装酷，对妹子才有亲和力~");
        a.add("今天考试无敌逢考必过~");
        a.add("今天怕是要翻水水呦~");
        a.add("今天吃喝玩不花钱，我说的！");
        a.add("学会帮助他人，好运接踵而至呦！");


        setTitle("校园卡消费查询");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        card_Avatar = (ImageView) findViewById(R.id.card_Avatar);
        card_Status = (TextView) this.findViewById(R.id.card_Status);
        card_Id = (TextView) this.findViewById(R.id.card_Id);
        card_Name = (TextView) findViewById(R.id.card_Name);
        card_Time = (TextView) this.findViewById(R.id.card_Time);
        card_Banlance = (TextView) findViewById(R.id.card_Balance);
        card_back_massage = (TextView) this.findViewById(R.id.card_back_message);
        //   card_Listview = (ListView) findViewById(R.id.card_Listview);
        mswipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        bigFrameLayout = (FrameLayout) this.findViewById(R.id.main_fl_container);
        FrameLayout1 = (FrameLayout) this.findViewById(R.id.main_fl_card_back);
        FrameLayout2 = (FrameLayout) this.findViewById(R.id.main_fl_card_front);

        //initAvatar();


        final Map<String, String> getToken = Info.getLoginInfo(Mycard.this);
        getInfo(getToken.get("mToken"));


        /**
         * OnScrollListener的回调方法
         * 在收藏夹关于回调的方法
         * */
      /*  card_Listview.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        });*/


        /**
         * 下拉刷新的功能
         * 关于SwipeRefreshLayout的下拉刷新
         * */
        mswipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfo(getToken.get("mToken"));
                mswipeLayout.setRefreshing(false);    // 设置刷新加载的icon是否继续显示 那必须为false啦。
            }
        });
        mswipeLayout.setColorSchemeResources(R.color.yellow, R.color.red, R.color.blue);//下拉icon三色变换

        setAnimation();//设置动画
        setCameraDistance();//设置镜头距离，在这里不是太懂
    }

    private void setAnimation() {
        //mLeftInSet是左边进入的动画
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);
        //mRightOutSet是右边出去的动画
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);

        //点击事件
        //通过ListenerAdapter就不需重写所有方法，只需写需要写的方法
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            //动画开始时候
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                bigFrameLayout.setClickable(false);
            }
        });
        //动画结束的时候
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bigFrameLayout.setClickable(true);//主布局中framelayouy的就允许你去点击了
            }
        });

    }

    //一直不是很懂的设置镜头距离，
//帖子上的注释写着：改变视角距离，贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        FrameLayout1.setCameraDistance(scale);//设置距离
        FrameLayout2.setCameraDistance(scale);//设置距离
    }

    public void flipCard(View view) {
        if (!mIsShowBack) {
            mRightOutSet.setTarget(FrameLayout2);
            mLeftInSet.setTarget(FrameLayout1);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = true;
            Random random = new Random();
            int suijishu = random.nextInt(12);
            card_back_massage.setText(a.get(suijishu));


        } else {
            mRightOutSet.setTarget(FrameLayout1);
            mLeftInSet.setTarget(FrameLayout2);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = false;
        }
    }


    /**
     * 通过token拉取消费记录
     * 返回error=0无错误
     * 返回error=1有错误
     * 返回error=2   token 有误 则重新登录
     */
    public void getInfo(final String mToken) {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            Toast.makeText(this, "当前网络不可用，数据未更新处理！仍可测试运气呦！", Toast.LENGTH_SHORT).show();
        } else {
            mDialog = ProgressDialog.show(Mycard.this, "", "加载中，请稍候...");
            new Thread() {
                @Override
                public void run() {
                    final String result = Netget.getCardInfo(mToken);
                    if (result != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            int error = jsonObject.getInt("error");
                            String message = jsonObject.getString("message");
                            switch (error) {
                                case 0:
                                    JSONObject jsb = jsonObject.getJSONObject("data");
                                    JSONObject info = jsb.getJSONObject("info");

                                    final String cardName = info.getString("name");
                                    final String cardBalance = info.getString("balance");
                                    final String cardTime = info.getString("startDate");
                                    final String cardStatus = info.getString("status");
                                    final String cardId = info.getString("cardId");
                           /*     JSONArray jsonArray =jsb.getJSONArray("data");

                                CardInfos = new ArrayList<>();
                                for (int i = 0;i<jsonArray.length();i++){
                                    JSONObject add = (JSONObject) jsonArray.get(i);
                                    String Trade = add.getString("trade");
                                    String Time = add.getString("time");

                                    CardInfo = new HashMap<>();
                                    CardInfo.put("Trade", "消费" + Trade);
                                    CardInfo.put("Time", "时间" + Time);
                                    CardInfos.add(CardInfo);
                                }*/
                                    initDataCard(cardName, cardBalance, cardTime, cardStatus, cardId);
                                    mDialog.dismiss();
                                    break;
                                case 1:
                                    setToast(message);
                                    break;
                                case 2:
                                    setToast("登录数据出错，请重新登录~~");
                                    //   Info.deleteUserInfo(Mycard.this);//删除并重新登录
                                    final Intent intent = getPackageManager()
                                            .getLaunchIntentForPackage(getPackageName());//返回入口 就是最初的Activity
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//重新开启Activity并在该栈而不创新栈
                                    startActivity(intent);
                                    break;
                                default:
                                    break;
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
     * 不能再线程用Toast
     * setToast方法用于上面调用
     *
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
    public void initDataCard(final String cardName, final String cardBalance,
                             final String cardTime, final String status, final String cardId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //        SimpleAdapter simpleAdapter = new SimpleAdapter(Mycard.this, CardInfos, R.layout.item_card,
                //               new String[]{"Trade", "Time"}, new int[]{R.id.card_Trade, R.id.card_Time});
                card_Name.setText(cardName);
                card_Banlance.setText(cardBalance);
                card_Time.setText(cardTime);
                card_Status.setText(status);
                card_Id.setText(cardId);
                //        card_Listview.setAdapter(simpleAdapter);
            }
        });
    }


    /**
     * 校园卡记录新现成获取头像
     * 放到bitmap中
     * */
  /* public void initAvatar(){
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

    }*/

    /**
     * 用于actionbar返回页面功能
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
