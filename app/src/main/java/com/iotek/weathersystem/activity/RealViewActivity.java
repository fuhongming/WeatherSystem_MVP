package com.iotek.weathersystem.activity;

/**
 * Created by fhm on 2016/5/27.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.iotek.weathersystem.R;

public class RealViewActivity extends BaseActivity implements OnPageChangeListener {
    private String[] titles = {"白云山", "黄山", "嵩山", "华山", "衡山", "圣山", "雁荡山", "庐山", "黄果树瀑布", "西峡", "嵩山", "太昊陵", "老子故里",
            "三峡", "青藏高原", "香格里拉"};
    private int[] imageIds = {R.drawable.bg01, R.drawable.bg02, R.drawable.bg03, R.drawable.bg04, R.drawable.bg05,
            R.drawable.bg06, R.drawable.bg07, R.drawable.bg08, R.drawable.bg09, R.drawable.bg10, R.drawable.bg11,
            R.drawable.bg12, R.drawable.bg13, R.drawable.bg14, R.drawable.bg15};

    private ViewPager viewPager;
    private TextView tvTitle;
    private LinearLayout llPoints;
    private ImageView[] images; // 图片数据源
    private List<View> points = new ArrayList<View>();
    private int currentPos = 0;
    public static final int WHAT_AUTO_RUN = 1;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == WHAT_AUTO_RUN) {
                points.get(currentPos % images.length).setEnabled(false);
                viewPager.setCurrentItem(++currentPos);
                sendEmptyMessageDelayed(WHAT_AUTO_RUN, 2000);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realview);

        findViews();

        initImages();
        initPoints();

        // 默认显示第一个标题
        tvTitle.setText(titles[0]);

        // 设置缓存页面的个数
        // viewPager.setOffscreenPageLimit(1);

        MyAdapter adapter = new MyAdapter();
        viewPager.setAdapter(adapter);

        currentPos = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % images.length;
        viewPager.setCurrentItem(currentPos);

        bindListener();

        handler.sendEmptyMessageDelayed(WHAT_AUTO_RUN, 2000);
    }

    private void findViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llPoints = (LinearLayout) findViewById(R.id.ll_points);
    }

    private void initPoints() {
        View view = null;
        for (int i = 0; i < images.length; i++) {
            view = new View(this);
            // 设置布局属性
            LayoutParams params = new LayoutParams(6, 6);
            params.rightMargin = 5;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_point);
            view.setEnabled(false);
            points.add(view);
            // 添加到布局中
            llPoints.addView(view);
        }

        points.get(0).setEnabled(true);
    }

    private void bindListener() {
        viewPager.setOnPageChangeListener(this);
    }

    private void initImages() {
        images = new ImageView[imageIds.length];
        ImageView iv = null;
        for (int i = 0; i < imageIds.length; i++) {
            iv = new ImageView(this);
            iv.setImageResource(imageIds[i]);
            iv.setScaleType(ScaleType.FIT_XY);
            images[i] = iv;
        }
    }

    private class MyAdapter extends PagerAdapter {

        /**
         * 页数
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 创建页面
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images[position % images.length]);
            return images[position % images.length];
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 销毁页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images[position % images.length]);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        tvTitle.setText(titles[position % images.length]);
        points.get(currentPos % images.length).setEnabled(false);
        points.get(position % images.length).setEnabled(true);
        currentPos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE: // 空闲
                if (!handler.hasMessages(WHAT_AUTO_RUN)) {
                    handler.sendEmptyMessageDelayed(WHAT_AUTO_RUN, 2000);
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING: // 拖拽
                handler.removeMessages(WHAT_AUTO_RUN);
                break;
            case ViewPager.SCROLL_STATE_SETTLING: // 固定

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}

