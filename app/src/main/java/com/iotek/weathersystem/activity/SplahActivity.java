package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.iotek.weathersystem.R;

public class SplahActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 1000; //延迟三秒
    
    @Override   
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.splah_start);
        new Handler().postDelayed(new Runnable(){   
    
         @Override   
         public void run() {   
             Intent mainIntent = new Intent(SplahActivity.this,MainTab.class);
             SplahActivity.this.startActivity(mainIntent);   
             SplahActivity.this.finish();   
         }   
              
        }, SPLASH_DISPLAY_LENGHT);   
    }   
  
}  