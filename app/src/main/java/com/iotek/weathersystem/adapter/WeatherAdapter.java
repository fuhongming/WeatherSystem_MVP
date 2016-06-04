package com.iotek.weathersystem.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.model.GraphItem;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.utils.BitmapHelp;
import com.iotek.weathersystem.view.GraphView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class WeatherAdapter extends BaseAdapter {


    List<Result> result = new ArrayList<>();
    Context context;
    BitmapUtils bitmapUtils;


    public WeatherAdapter(Context context) {
        this.context = context;
        bitmapUtils = BitmapHelp.getBitmapUtils(context);
    }

    public void setData(List<Result> result) {
        this.result = result;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return result == null ? 0 : result.size();
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
    public int getViewTypeCount() {
        return  2;
    }

    public int getItemViewType(int position) {
        return position < 1 ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (getItemViewType(position) == 0) {
            convertView = LayoutInflater.from(context).inflate(R.layout.line_item, null);
            GraphView lineGraph = (GraphView) convertView.findViewById(R.id.graphLine);//折线图，自定义view
            line(lineGraph);

        } else {
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
            int index = position - 1;
            String week = result.get(index).getWeek();
            String weather = result.get(index).getWeather();
            String wind = result.get(index).getWind();
            String temperature = result.get(index).getTemperature();
            holder.tvWeek.setText(week);
            holder.tvWeather.setText(weather);
            holder.tvWind.setText(wind);
            holder.tvTemperature.setText(temperature);
            bitmapUtils.display(holder.imgWeather, result.get(index).getWeather_icon());
        }
        return convertView;
    }

    ArrayList<GraphItem> items;
    private void line(GraphView lineGraph) {
        if (result.size() ==0) {
            return;
        }
        items = new ArrayList<GraphItem>();

        String week1=result.get(0).getWeek();
        String week2=result.get(1).getWeek();
        String week3=result.get(2).getWeek();
        String week4=result.get(3).getWeek();
        String week5=result.get(4).getWeek();
        String week6=result.get(5).getWeek();
//        String week7=result.get(6).getWeek();

        int temp1_high = Integer.parseInt(result.get(0).getTemp_high());
        int temp2_high = Integer.parseInt(result.get(1).getTemp_high());
        int temp3_high = Integer.parseInt(result.get(2).getTemp_high());
        int temp4_high = Integer.parseInt(result.get(3).getTemp_high());
        int temp5_high = Integer.parseInt(result.get(4).getTemp_high());
        int temp6_high = Integer.parseInt(result.get(5).getTemp_high());
//        int temp7_high = Integer.parseInt(result.get(6).getTemp_high());

        items.add(new GraphItem(week1.substring(2,3), temp1_high));
        items.add(new GraphItem(week2.substring(2,3), temp2_high));
        items.add(new GraphItem(week3.substring(2,3), temp3_high));
        items.add(new GraphItem(week4.substring(2,3), temp4_high));
        items.add(new GraphItem(week5.substring(2,3), temp5_high));
        items.add(new GraphItem(week6.substring(2,3), temp6_high));
//        items.add(new GraphItem(week7.substring(2,3), temp7_high));

        int temp1_low = Integer.parseInt(result.get(0).getTemp_low());
        int temp2_low = Integer.parseInt(result.get(1).getTemp_low());
        int temp3_low = Integer.parseInt(result.get(2).getTemp_low());
        int temp4_low = Integer.parseInt(result.get(3).getTemp_low());
        int temp5_low = Integer.parseInt(result.get(4).getTemp_low());
        int temp6_low = Integer.parseInt(result.get(5).getTemp_low());
//        int temp7_low = Integer.parseInt(result.get(6).getTemp_low());

        items.add(new GraphItem(week1.substring(2,3), temp1_low));
        items.add(new GraphItem(week2.substring(2,3), temp2_low));
        items.add(new GraphItem(week3.substring(2,3), temp3_low));
        items.add(new GraphItem(week4.substring(2,3), temp4_low));
        items.add(new GraphItem(week5.substring(2,3), temp5_low));
        items.add(new GraphItem(week6.substring(2,3), temp6_low));
//        items.add(new GraphItem(week7.substring(2,3), temp7_low));

        lineGraph.setItems(items);
    }

    class ViewHolder {
        TextView tvWeek;
        TextView tvWeather;
        TextView tvWind;
        TextView tvTemperature;
        ImageView imgWeather;
    }
}

