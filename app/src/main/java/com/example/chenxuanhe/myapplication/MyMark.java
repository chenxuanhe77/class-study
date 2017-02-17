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
        intent.putExtra("termtime",termtime);
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
        intent.putExtra("termtime",termtime);
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
        intent.putExtra("termtime",termtime);
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
        intent.putExtra("termtime",termtime);
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
        intent.putExtra("termtime",termtime);
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
        intent.putExtra("termtime",termtime);
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
        intent.putExtra("termtime",termtime);
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
        intent.putExtra("termtime",termtime);
        startActivity(intent);
    }
}
