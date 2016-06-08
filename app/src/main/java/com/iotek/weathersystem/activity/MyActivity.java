package com.iotek.weathersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.iotek.weathersystem.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fhm on 2016/5/28.
 */

public class MyActivity extends BaseActivity {
    @ViewInject(R.id.lv)
    ListView lv;
    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ViewUtils.inject(this);
        list.add("设置");
        list.add("关于");
        list.add("发现");
        list.add("信箱");
        list.add("帮助");
        lv.setAdapter(new MyAdapter());

    }
    @OnItemClick(R.id.lv)
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


//这里添加你的操作
        if(position == 0){
//            Intent i=new Intent(MyActivity.this,MainTab.class);
//            startActivity(i);
        }else if(position==1){
//            Intent i=new Intent(MyActivity.this,MainTab.class);
//            startActivity(i);
//事件
        }
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_item, null);
                holder = new ViewHolder();
                /* 得到各个控件的对象 */
                holder.ivSet = (ImageView) convertView.findViewById(R.id.ivSet);
                holder.tvSet = (TextView) convertView.findViewById(R.id.tvSet);
                convertView.setTag(holder);// 绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }
            String nameSet = list.get(position);
            holder.tvSet.setText(nameSet);
            if(nameSet.equals("设置")){
                holder.ivSet.setImageResource(R.drawable.setting);
            }else if(nameSet.equals("关于")){
                holder.ivSet.setImageResource(R.drawable.about);
            }else if(nameSet.equals("发现")) {
                holder.ivSet.setImageResource(R.drawable.find);
            }else if(nameSet.equals("信箱")) {
                holder.ivSet.setImageResource(R.drawable.forum_msg);
            }else if(nameSet.equals("帮助")) {
                holder.ivSet.setImageResource(R.drawable.assistant);
            }
            return convertView;
        }

        class ViewHolder {
            TextView tvSet;
            ImageView ivSet;
        }

    }

}
