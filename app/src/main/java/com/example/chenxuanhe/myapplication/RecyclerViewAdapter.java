package com.example.chenxuanhe.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenxuanhe.myapplication.utils.MarkUtil;

import java.util.List;

/**
 * Created by ChenXuanHe on 2017/2/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<MarkUtil> markInfo;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<MarkUtil> list) {
        this.mContext = context;
        this.markInfo = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mark_item, parent, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MarkUtil markUtil = markInfo.get(position);
        holder.item_course.setText(markUtil.getCourse());
        holder.item_mark.setText(markUtil.getMark());
        holder.item_mode.setText(markUtil.getMode());
        holder.item_credit.setText("学分: "+markUtil.getCredit()+"分");

    }

    @Override
    public int getItemCount() {
        return markInfo == null ? 0 : markInfo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView item_course;
        public TextView item_mark;
        public TextView item_mode;
        public TextView item_credit;


        public MyViewHolder(View v) {
            super(v);
            item_course = (TextView) v.findViewById(R.id.item_course);
            item_mark = (TextView)v.findViewById(R.id.item_mark);
            item_mode = (TextView)v.findViewById(R.id.item_mode);
            item_credit = (TextView)v.findViewById(R.id.item_credit);
        }
    }
}
