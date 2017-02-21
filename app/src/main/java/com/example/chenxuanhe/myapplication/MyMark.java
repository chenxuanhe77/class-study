package com.example.chenxuanhe.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chenxuanhe.myapplication.utils.Info;
import com.example.chenxuanhe.myapplication.utils.MarkSerializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenxuanhe on 2016/4/16.
 */
public class MyMark extends AppCompatActivity {

    public static String termMark;

    private static String course;
    private static String mark;
    private static String mode;
    private static String credit;

    private String NoMark;
    private String NoMode;
    private String NoCourse;
    private String NoCredit;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymark_main);

        final Map<String, String> token = Info.getLoginInfo(MyMark.this);
        getMarkInfo(token.get("mToken"));

    }

    private void getMarkInfo(final String mtoken) {
        dialog = ProgressDialog.show(MyMark.this, "", "加载中~~");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().
                            url("http://api.13550101.com/main/score?token=" + mtoken).build();
                    Response response = client.newCall(request).execute();
                    if (response != null) {
                        String result = response.body().string();
                        try {
                            JSONTokener results = new JSONTokener(result);
                            JSONObject ddaa = (JSONObject) results.nextValue();
                            String message = ddaa.getString("message");
                            int error = ddaa.getInt("error");
                            switch (error) {
                                case 0:
                                    JSONObject ddaattaa = ddaa.getJSONObject("data");
                                    JSONObject data = ddaattaa.getJSONObject("data");
                                    Log.d("fuck", "" + data);//OK了
                                    termMark = data.toString().trim();
                                    dialog.dismiss();
                                    break;
                                case 1:
                                    setToast(message);
                                    break;
                                case 2:
                                    finish();
                                    doIntent(Login.class);
                                    setToast("信息有误，请重新登陆验证呦~");
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyMark.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doIntent(Class ss) {
        Intent intent = new Intent();
        intent.setClass(MyMark.this, ss);
        startActivity(intent);
    }


    public void oneTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2013-2014-1");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("A123", "aaaaaaa");
        String termtime = "2013-2014-1学期 ";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void twoTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2013-2014-2");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String termtime = "2013-2014-2学期";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void threeTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2014-2015-1");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String termtime = "2014-2015-1学期";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void fourTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2014-2015-2");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String termtime = "2014-2015-2学期";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void fiveTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2015-2016-1");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String termtime = "2015-2016-1学期";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void sixTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2015-2016-2");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String termtime = "2015-2016-2学期";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void sevenTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2016-2017-1");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String termtime = "2016-2017-1学期";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void eightTerm(View view) {
        ArrayList<MarkSerializable> aaa = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject AAA = (JSONObject) JS.nextValue();
            JSONArray oneterm = AAA.getJSONArray("2016-2017-2");
            for (int i = 0; i <= oneterm.length(); i++) {
                MarkSerializable markParcelable = new MarkSerializable();

                JSONObject abc = oneterm.getJSONObject(i);
                course = abc.getString("course");
                mark = abc.getString("mark");
                mode = abc.getString("mode");
                credit = abc.getString("credit");

                markParcelable.setCourse(course);
                markParcelable.setMark(mark);
                markParcelable.setMode(mode);
                markParcelable.setCredit(credit);

                aaa.add(markParcelable);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String termtime = "2016-2017-2学期";
        Intent intent = new Intent(MyMark.this, ShowMark.class);
        intent.putExtra("markInfo", aaa);
        intent.putExtra("termtime", termtime);
        startActivity(intent);
    }

    public void failedTerm(View view) {
        ArrayList<MarkSerializable> list = new ArrayList<>();
        try {
            JSONTokener JS = new JSONTokener(termMark);
            JSONObject data = (JSONObject) JS.nextValue();
            JSONArray oneTerm = data.getJSONArray("2013-2014-1");
            JSONArray twoTerm = data.getJSONArray("2013-2014-2");
            JSONArray threeTerm = data.getJSONArray("2014-2015-1");
            JSONArray fourTerm = data.getJSONArray("2014-2015-2");
            JSONArray fiveTerm = data.getJSONArray("2015-2016-1");
            JSONArray sixTerm = data.getJSONArray("2015-2016-2");
            JSONArray sevenTerm = data.getJSONArray("2016-2017-1");
            //JSONArray eightTerm = data.getJSONArray("2016-2017-2");
            List<JSONArray> a = new ArrayList<>();
            a.add(oneTerm);
            a.add(twoTerm);
            a.add(threeTerm);
            a.add(fourTerm);
            a.add(fiveTerm);
            a.add(sixTerm);
            a.add(sevenTerm);
            //a.add(eightTerm);
            try {
                for (int i = 0; i < a.size() - 1; i++) {
                    JSONArray everyTerm = a.get(i);
                    for (int m = 0; m < everyTerm.length(); m++) {
                        JSONObject abc = everyTerm.getJSONObject(m);
                        NoMark = abc.getString("mark");
                        NoCourse = abc.getString("course");
                        NoMode = abc.getString("mode");
                        NoCredit = abc.getString("credit");
                        try {
                            MarkSerializable marksz = new MarkSerializable();
                            if (NoMode.equals("等级制")) {
                                if (NoMark.equals("不及格")) {
                                    Log.d("BJG", "不及格科目" + NoMark + NoCourse + NoCredit + NoMode);

                                    marksz.setCourse(NoCourse);
                                    marksz.setMark(NoMark);
                                    marksz.setMode(NoMode);
                                    marksz.setCredit(NoCredit);

                                    list.add(marksz);
                                }
                                /**
                                 * 这段代码检测挂科科目是否经过补考过关。
                                 */
                           /*     else if(NoMark.equals("及格")||NoMark.equals("优")||NoMark.equals("良")
                                        ||NoMark.equals("中")){
                                    for (int x = 0; x<list.size();x++){
                                        if(list.get(x).getCourse().equals(NoCourse)){
                                            list.remove(x);
                                        }
                                    }
                                }*/

                            } else if (NoMode.equals("分数制")) {
                                int marks = Integer.parseInt(NoMark);
                                if (marks < 60) {
                                    Log.d("BJG", "不及格的科目:" + NoMark + NoCourse + NoCredit + NoMode);
                                    // MarkSerializable marksz = new MarkSerializable();
                                    marksz.setCourse(NoCourse);
                                    marksz.setMark(NoMark);
                                    marksz.setMode(NoMode);
                                    marksz.setCredit(NoCredit);

                                    list.add(marksz);
                                }
                                /**
                                 *这段代码检测是否存在挂科但是补考过了的。
                                 */
                               /*  else if (marks >= 60) {
                                 for (int n = 0; n < list.size() ; n++) {
                                 if (list.get(n).getCourse().equals(NoCourse)) {
                                 Log.d("QWER", "已经补考过关的是:" + list.get(n).getCourse()+NoMark);
                                 list.remove(n);
                                 }

                                 }
                                 }*/

                            }
                        } catch (NumberFormatException e) {
                            Log.d("BJG", "String转换int发生异常");
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                Log.d("BJG", "这是倒数第二个trycatch块");
                e.printStackTrace();
            }
        } catch (JSONException e) {
            Log.d("BJG", "这是最外层的try catch块");
            e.printStackTrace();
        }
        Intent intent = new Intent(MyMark.this, FailedActivity.class);
        intent.putExtra("NoMarkInfo", list);
        startActivity(intent);
    }
}
