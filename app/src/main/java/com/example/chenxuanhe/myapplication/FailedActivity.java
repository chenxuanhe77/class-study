package com.example.chenxuanhe.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chenxuanhe.myapplication.utils.MarkSerializable;
import com.example.chenxuanhe.myapplication.utils.MarkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tencent.android.tpush.XGPushManager.getContext;

/**
 * Created by ChenXuanHe on 2017/2/21.
 */

public class FailedActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;
    private List<MarkUtil> info = new ArrayList<>();

    @BindView(R.id.recycler_Failed_mark)
    RecyclerView recyclerFailedMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faile_mark);
        ButterKnife.bind(this);
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
}
