package com.example.chenxuanhe.myapplication;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by neokree on 16/12/14.
 */
public class FragmentLesson extends Fragment{

    private RecyclerView recyclerView;
    private List<HashMap<String,Object>> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.class_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DayCourseAdapter());
        return view;
    }


    class DayCourseAdapter extends RecyclerView.Adapter<CourseHolder>{
        private LayoutInflater inflater;

        DayCourseAdapter(){
            inflater = LayoutInflater.from(getContext());
        }
        /**
         * 创建新视图 由布局管理器调用
         */
        @Override
        public CourseHolder onCreateViewHolder(ViewGroup parent,int viewType){

            View view = inflater.inflate(R.layout.item_class,parent,false);
            TypedValue typedValue = new TypedValue();//这三行全是自定义Attr风格样式以及拂去
            getActivity().getTheme().resolveAttribute(R.attr.selectableItemBackground,typedValue,true);//*
            view.setBackgroundResource(typedValue.resourceId);//这三行全是自定义Attr风格样式以及拂去
            return  new CourseHolder(view);
        }

        /**
         * 更换视图的内容  Bind  绑定对应
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(CourseHolder holder,int position){

            HashMap<String,Object> course = data.get(position);
            if(course!=null){
                holder.num.setText(course.get("Num").toString());
                holder.name.setText(course.get("Time").toString());
                holder.room.setText(course.get("Room").toString());
                holder.time.setText(course.get("Time").toString());
            }
            holder.itemView.setClickable(true);
        }

        /**
         * 获得得到的总数目
         * @return
         */
        public int getItemCount(){
            return data == null ? null:data.size();
        }
    }

    /**
     * 提供一个合适的构造函数
     */
    public void updata(List<HashMap<String,Object>> data){
        this.data = data;
        if(recyclerView!=null){
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 给每个item的Id定义对象
     */
   class CourseHolder extends RecyclerView.ViewHolder{
       TextView num;
       TextView time;
       TextView name;
       TextView room;

       public CourseHolder(View itemView){
           super(itemView);
           num = (TextView) itemView.findViewById(R.id.class_num);
           name = (TextView) itemView.findViewById(R.id.class_name);
           time  = (TextView) itemView.findViewById(R.id.class_time);
           room = (TextView) itemView.findViewById(R.id.class_room);
       }
   }



}
