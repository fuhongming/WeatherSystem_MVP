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
import com.iotek.weathersystem.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;


public class WeatherAdapter extends BaseAdapter {
   List<Result>  result =new ArrayList<>();
    Context context;
    BitmapUtils bitmapUtils;


    public WeatherAdapter(Context context) {
        this.context = context;
        bitmapUtils = BitmapHelp.getBitmapUtils(context);
    }

    public void setData( List<Result>  result) {
        this.result = result;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (result == null) {
            return 0;
        }
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_weather_item, null);
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
        String week =result.get(position).getWeek();
        String weather = result.get(position).getWeather();
        String wind = result.get(position).getWind();
        String temperature=result.get(position).getTemperature();
        holder.tvWeek.setText(week);
        holder.tvWeather.setText(weather);
        holder.tvWind.setText(wind);
        holder.tvTemperature.setText(temperature);
        bitmapUtils.display(holder.imgWeather, result.get(position).getWeather_icon());

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

