package com.example.chenxuanhe.myapplication;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import com.example.chenxuanhe.myapplication.utils.MarkSerializable;
import com.example.chenxuanhe.myapplication.utils.MarkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tencent.android.tpush.XGPushManager.getContext;

/**
 * Created by ChenXuanHe on 2017/2/21.
 */

public class FailedActivity extends AppCompatActivity {

    @BindView(R.id.remove_remove)
    FloatingActionButton removeRemove;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<MarkUtil> info = new ArrayList<>();
    private FloatingActionButton fabRemove;

    @BindView(R.id.recycler_Failed_mark)
    RecyclerView recyclerFailedMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faile_mark);
        ButterKnife.bind(this);

        Toast toast = Toast.makeText(this,"点击我可以取消挂科信息的通知提醒呦！",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();

        getMarkInfo();

        recyclerFailedMark.setLayoutManager(new LinearLayoutManager(this));
        recyclerFailedMark.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), info);
        recyclerFailedMark.setAdapter(recyclerViewAdapter);

    }

    private void getMarkInfo() {
        try {
            ArrayList<MarkSerializable> list = (ArrayList<MarkSerializable>) getIntent().
                    getSerializableExtra("NoMarkInfo");
            for (int i = 0; i < list.size(); i++) {
                //  Log.d("QQQ", "" + list.get(i));
                MarkSerializable markSerializable = list.get(i);
                String course = markSerializable.getCourse();
                String mark = markSerializable.getMark();
                String mode = markSerializable.getMode();
                String credit = markSerializable.getCredit();
                info.add(new MarkUtil(course, mark, mode, credit));
            }

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.remove_remove)
    public void onClick() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
        Intent intent = new Intent(this,MyMarkService.class);
        stopService(intent);
    }
}
