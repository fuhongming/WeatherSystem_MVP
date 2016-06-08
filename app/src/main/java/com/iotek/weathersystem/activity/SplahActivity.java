package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.iotek.weathersystem.R;

public class SplahActivity extends Activity {
    ImageView ivBg;
//	private final int SPLASH_DISPLAY_LENGHT = 1000; //延迟三秒
    
    @Override   
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.splah_start);
        ivBg= (ImageView) findViewById(R.id.ivBg);
        AlphaAnimation anima = new AlphaAnimation(0.1f, 1.0f);
//        TranslateAnimation anima=new TranslateAnimation(0,120,0,0);
        anima.setDuration(1000);// 设置动画显示时间
        ivBg.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());
        /*new Handler().postDelayed(new Runnable(){
    
         @Override   
         public void run() {   
             Intent mainIntent = new Intent(SplahActivity.this,MainTab.class);
             SplahActivity.this.startActivity(mainIntent);   
             SplahActivity.this.finish();   
         }   
              
        }, SPLASH_DISPLAY_LENGHT);   */
    }
    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
//            ivBg.setBackgroundResource(R.drawable.bg05);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip(); // 动画结束后跳转到别的页面
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    private void skip() {
        startActivity(new Intent(this, MainTab.class));
        finish();
    }

}  