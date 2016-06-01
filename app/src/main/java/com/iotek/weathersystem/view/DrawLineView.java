package com.iotek.weathersystem.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.iotek.weathersystem.R;

/**
 * Created by fhm on 2016/6/1.
 */
public class DrawLineView extends GridView {
    private View child;
    private int count;
    private Paint paint1;

    public DrawLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint1 = new Paint();//新建画笔
        paint1.setColor(Color.WHITE);
        paint1.setAlpha(200);
        paint1.setStrokeWidth(4f);
        paint1.setTextSize(20f);
        paint1.setAntiAlias(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {//重新分发绘图任务的方法
        // TODO Auto-generated method stub
        super.dispatchDraw(canvas);//先调用父类方法分发任务，完成后再添加内容
        count = getChildCount();
        Point point1 = null;//用于缓存上一个点的信息
        Point point2 = null;//用于缓存上一个点的信息
        int i,max=10000,min=-10000;
        float ph;
        int height = 0;
        if(count>0){
            height=getChildAt(0).findViewById(R.id.blank).getHeight();
        }
        int temp1,temp2;
        //找一周最高温度和最低温度，计算倍率
        for (i = 0; i < count; i++) {//获取一周最高温度和最低温度
//            Holder holder = (Holder) getChildAt(i).getTag();
//            temp1 = holder.getMax();
//            temp2 = holder.getMin();
            if(i!=0){
//                if(max<temp1){
//                    max=temp1;
//                }
//                if(min>temp2){
//                    min=temp2;
//                }
            }else{
//                max=temp1;
//                min=temp2;
            }
        }
        if(max!=min){
            ph=height/(max-min);//计算屏占比
        }else{
            ph=1f;
        }
        //根据屏占比描点，画线
        for (i = 0; i < count; i++) {
            child = getChildAt(i);
            View v=child.findViewById(R.id.blank);
//            Holder holder = (Holder) child.getTag();
//            temp1=holder.getMax();
//            temp2=holder.getMin();
            float centerX=child.getWidth() / 2 + child.getLeft();
            float centerY=v.getBottom()-30;//y标准线
            float x1 = centerX;
//            float y1 = centerY - (temp1-min)*ph*0.8f;//先平移min，再扩大ph倍，再区间缩放0.8倍，再倒置，最后移动到y标准线
            float x2,y2;
            if (point1 == null) {
                x2 = x1;
//                y2 = y1;
            } else {
                x2 = point1.getX();
                y2 = point1.getY();
            }
            float x3 = centerX;
//            float y3 = centerY - (temp2-min)*ph*0.8f;
            float x4, y4;
            if (point1 == null) {
                x4 = x3;
//                y4 = y3;
            } else {
                x4 = point2.getX();
                y4 = point2.getY();
            }
//            canvas.drawCircle(x1, y1, 10, paint1);//画点
//            canvas.drawText(temp1 + " °C", x1 - 25, y1 - 20, paint1);//画字符
//            canvas.drawLine(x2, y2, x1, y1, paint1);//画线

//            canvas.drawCircle(x3, y3, 10, paint1);
//            canvas.drawText(temp2 + " °C", x3 - 25, y3 +40, paint1);
//            canvas.drawLine(x4, y4, x3, y3, paint1);
//            //缓存已经处理的两个点
//            point1 = new Point(x1, y1);
//            point2 = new Point(x3, y3);
        }
    }

    private class Point {
        float x, y;

        public Point(float x, float y) {
            super();
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }
}
