package com.iotek.weathersystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.model.Today;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;


public class MainAdapter extends BaseAdapter {
    Result result;
    Context context;
    BitmapUtils bitmapUtils;

    public MainAdapter(Context context) {
        this.context = context;
//        bitmapUtils = BitmapHelp.getBitmapUtils(context);
    }

    public void setData(Result result) {
        this.result = result;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (result == null) {
            return 0;
        }
        return result.getFuture().size();
    }

    @Override
    public Object getItem(int position) {
        return result.getFuture().get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            holder = new ViewHolder();
            // 得到各个控件的对象
            holder.tvWeek = (TextView) convertView.findViewById(R.id.tvWeek);
            holder.tvWeather = (TextView) convertView.findViewById(R.id.tvWeather);
            holder.tvWind = (TextView) convertView.findViewById(R.id.tvWind);
            holder.tvTemperature = (TextView) convertView.findViewById(R.id.tvTemperature);
            holder.imgWeather = (ImageView) convertView.findViewById(R.id.imgWeather);
            convertView.setTag(holder);// 绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
        }
        String week = result.getFuture().get(position).getWeek();
        String weather = result.getFuture().get(position).getWeather();
        String wind = result.getFuture().get(position).getWind();
        String temperature=result.getFuture().get(position).getTemperature();
        holder.tvWeek.setText(week);
        holder.tvWeather.setText(weather);
        holder.tvWind.setText(wind);
        holder.tvTemperature.setText(temperature);

        int resId = R.drawable.org3_ww18;
        if (weather.contains("雨")) {
            resId = R.drawable.org3_ww19;
        } else if(weather.contains("雨")) {

        } else if(weather.contains("雨")) {

        } else if(weather.contains("雨")) {

        } else if(weather.contains("雨")) {

        }
        holder.imgWeather.setImageResource(resId);
//        bitmapUtils.display(holder.imgWeather, "");

        return convertView;
    }

    class ViewHolder {
        TextView tvWeek;
        TextView tvWeather;
        TextView tvWind;
        TextView tvTemperature;
        ImageView imgWeather;
    }
}

