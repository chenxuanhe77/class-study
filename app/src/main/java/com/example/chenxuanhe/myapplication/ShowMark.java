package com.example.chenxuanhe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.chenxuanhe.myapplication.utils.MarkSerializable;
import com.example.chenxuanhe.myapplication.utils.MarkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tencent.android.tpush.XGPushManager.getContext;

/**
 * Created by ChenXuanHe on 2017/2/16.
 */

public class ShowMark extends AppCompatActivity {

    @BindView(R.id.mark_term)
    TextView markTerm;
    @BindView(R.id.recycler_Mymark)
    RecyclerView recyclerMymark;

    private RecyclerViewAdapter recyclerViewAdapter;
    private List<MarkUtil> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.show_mark);
        ButterKnife.bind(this);

        init();

        recyclerMymark.setLayoutManager(new LinearLayoutManager(this));
        recyclerMymark.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), info);
        recyclerMymark.setAdapter(recyclerViewAdapter);

    }

    private void init() {
        try {
            Intent intent = getIntent();
            String termtime = intent.getStringExtra("termtime");
            markTerm.setText(termtime);
            ArrayList<MarkSerializable> listInfo = (ArrayList<MarkSerializable>)
                    getIntent().getSerializableExtra("markInfo");
            for (int i = 0; i <= 10; i++) {
                MarkSerializable assa = listInfo.get(i);
                String course = assa.getCourse();
                String mark = assa.getMark();
                String mode = assa.getMode();
                String credit = assa.getCredit();
                info.add(new MarkUtil(course, mark, mode, credit));
                Log.d("A112233", "" + course + mark + mode + credit);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }


      /*   Intent intent = getIntent();
       final String course = intent.getStringExtra("course");
        final String mark = intent.getStringExtra("mark");
        final String mode = intent.getStringExtra("mode");
        final String credit  = intent.getStringExtra("credit");*/
        //    every.add(course);
        //    every.add(mark);
        //   every.add(mode);
        //   every.add(credit);
        //   info.add(every);

    }

}
