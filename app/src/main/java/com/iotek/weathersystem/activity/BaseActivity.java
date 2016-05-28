package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 基类
 * @author user
 *
 */
@SuppressWarnings("ALL")
public class BaseActivity extends Activity {
	protected static final String TAG = "Peter";
	protected static final int TV_WIDTH = 1280, TV_HEIGHT = 720;
	protected static final int SOUND_BUTTON = 1;
	protected static final int SOUND_RIGHT = 2;
	protected static final int SOUND_ERROR = 3;
	protected static final int SOUND_WONDERFUL = 4;
	
	long startTime;
	int mScreenWidth, mScreenHeight, countRightNum, currentIndex;
	static float xScale, yScale;
	float mdensity;
	boolean isQuesInit, stopThread;
	
	Class<?> activity;
	SharedPreferences settings;
	RelativeLayout layout;
	View num1, num2, num3;
	Random random;
	static AnimationDrawable animPoint, animNumber, animHari, animStar1, animStar2, animStar3;

	MediaPlayer mediaPlayer;
	SoundPool soundPool;
	Thread thread;
	Toast toast;

	long lastTime;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - lastTime < 2000) {
				finish();
			} else {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT)
						.show();
			}
			lastTime = System.currentTimeMillis();
		}
		return true;
	}

//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event)
//	{
//		switch (keyCode) {
//			case KeyEvent.KEYCODE_BACK:
//				replaceActivity();
//				break;
//		}
//		if (keyCode != KeyEvent.KEYCODE_DPAD_CENTER && keyCode != KeyEvent.KEYCODE_ENTER && soundPool !=null) {
//			soundPool.play(SOUND_BUTTON, 1, 1, 0, 0, 1);
//		}
//		return false;
//	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		layout = new RelativeLayout(this);
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
		if (mScreenHeight < 720) {
			mScreenHeight = 720;
		}
		xScale = (float) mScreenWidth / TV_WIDTH;
		yScale = (float) mScreenHeight / TV_HEIGHT;
		
		if (null == soundPool) {
			loadAudio();
		}
		
		DisplayMetrics dm = new DisplayMetrics();  
		dm = getResources().getDisplayMetrics(); 
		mdensity = dm.density;
	}
		
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		stopTimer();
	}

	public void releaseBgSound() {
		if (null == mediaPlayer) {
			return;
		}
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	public void releaseSoundPool() {
		if (null == soundPool) {
			return;
		}
		soundPool.release();
		soundPool = null;
	}
	
	public void loadAudio(int...ids)
	{
		for (int i = 0; i < ids.length; i++) {
			soundPool.load(this, ids[i], 1);
		}
	}

	
	public void addToast(String text) {
		if (null != toast) {
			toast.cancel();
		}
		toast = Toast.makeText(this, text, 0);
		toast.show();
	}
	
	/**
	 * 添加文字方法
	 * @param id
	 * @param color
	 * @param font	文字字体在assets/fonts中添加
	 * @param text
	 * @param x
	 * @param y
	 * @param size
	 * @return
	 */
	public TextView addText(int id, int color, String font, String text, float x, float y, int size) {
		TextView lable = new TextView(this);
		lable.setId(id);
		AssetManager mgr = getAssets();//得到AssetManager
		Typeface tf=Typeface.createFromAsset(mgr, "fonts/ttf.ttf");//根据路径得到Typeface
		lable.setTypeface(tf);//设置字体   
		lable.setText(text);
		lable.setTextSize(size * yScale / mdensity);
		lable.setTextColor(color);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins((int)(x * xScale), (int)(y * yScale), 0, 0);
		layout.addView(lable, lp);
		return lable;
	}
	
//	/**
//	 * 添加资源图片在SD卡的图片
//	 * @param id
//	 * @param picPath
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	public ImageView addPicByStorage(int id, String picPath, float x, float y)
//	{
//		ImageView iv = addPic(id, getBitmapByStorage(picPath), x, y);
//		return iv;
//	}
//
//	public Bitmap getBitmapByStorage(String picPath) {
//		Bitmap bitmap = BitmapFactory.decodeFile(CzDownLoadList.saveDir + picPath);
//		return bitmap;
//	}

	public ImageView addPic(int id, Bitmap bitmap, float x, float y)
	{
		ImageView iv = new ImageView(this);
		iv.setId(id);
		iv.setImageBitmap(bitmap);
		float bmW = bitmap.getWidth();
		float bmH = bitmap.getHeight();
		RelativeLayout.LayoutParams lp;
		lp = new RelativeLayout.LayoutParams((int)(xScale * bmW), (int)(yScale * bmH));
		lp.setMargins((int)(x * xScale), (int)(y * yScale), (int) ((x + bmW - 1280) * xScale), -720);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		layout.addView(iv, lp);
		return iv;
	}
	
	@SuppressWarnings("deprecation")
	public View addPic(int id, Drawable drawable, float x, float y, float w, float h)
	{
		View v = addPic(id, x, y, w, h);
		v.setBackgroundDrawable(drawable);
		return v;
	}
	
	public ImageView addPic(int id, float x, float y, float w, float h)
	{
		ImageView iv = new ImageView(this);
		iv.setId(id);
		RelativeLayout.LayoutParams lp;
		lp = new RelativeLayout.LayoutParams((int)(xScale * w), (int)(yScale * h));
		lp.setMargins((int)(x * xScale), (int)(y * yScale), 0, 0);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		layout.addView(iv, lp);
		return iv;
	}
	
	/**
	 * 参数最后的在最前
	 * @param ids
	 */
	public void setZorder(int...ids)
	{
		for (int i = 0; i < ids.length; i++) {
			findViewById(ids[i]).bringToFront();
		}
	}
	
	public void replaceActivity()
	{
		releaseSoundPool();
		releaseBgSound();
		stopThread = true;
		stopTimer();
		finish();
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	public void replaceActivity(Class<?> activity)
	{
		addLoading();
		this.activity = activity;
		handler.sendEmptyMessageDelayed(2, 1000);
		stopTimer();
	}

	private void startActivity()
	{
		Intent intent = new Intent();
		intent.setClass(this, activity);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	public void changeImagePosition(View image, float x, float y)
	{
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(image.getLayoutParams());
		lp.setMargins((int)(x * xScale), (int)(y * yScale), 0, 0);
		image.setLayoutParams(lp);
	}
	public ImageView addPic(int id, String picPath, float x, float y, float w, float h)
	{
		return addPic(id, picPath, x, y, true, w, h);
	}
	
	public ImageView addPic(String picPath, float x, float y)
	{
		return addPic(-1, picPath, x, y, true, 0, 0);
	}
	public ImageView addPic(int id, String picPath, float x, float y)
	{
		return addPic(id, picPath, x, y, true, 0, 0);
	}
	
	public Drawable getDrawable(String picPath)
	{
		Bitmap bitmap = getBitmap(picPath);
		ImageView ivCache = new ImageView(this);
		ivCache.setImageBitmap(bitmap);
		return ivCache.getDrawable();
	}
	
	public ImageView addPic(int id, String picPath, float x, float y, boolean setImageBitmap, float w, float h)
	{
		ImageView iv = new ImageView(this);
		iv.setId(id);
		Bitmap bitmap = getBitmap(picPath);
		if (setImageBitmap) {
			ImageView ivCache = new ImageView(this);
			ivCache.setImageBitmap(bitmap);
			iv.setBackgroundDrawable(ivCache.getDrawable());
		}
		float bmW = bitmap.getWidth();
		float bmH = bitmap.getHeight();
		RelativeLayout.LayoutParams lp;
		if (((xScale * bmW) != w || (yScale * bmH) != h) && w != 0) {
			lp = new RelativeLayout.LayoutParams((int)(xScale * w), (int)(yScale * h));

		}else{
			lp = new RelativeLayout.LayoutParams((int)(xScale * bmW), (int)(yScale * bmH));
		}
		lp.setMargins((int)(x * xScale), (int)(y * yScale), 0, -720);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		layout.addView(iv, lp);
		return iv;
	}
	
	public Bitmap getBitmap(String picPath)
	{
		InputStream assetFile = null;
		Bitmap bitmap = null;
		try {
			AssetManager as = getAssets();
			assetFile  = as.open(picPath);
			bitmap = BitmapFactory.decodeStream(assetFile);
			assetFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 获取文件夹中第一个文件的名称
	 * @param folder
	 * @return
	 */
	public String listFolder(String folder)
	{
		String[] listImage = null;
		AssetManager am = getAssets();
		try {
			listImage = am.list(folder);// 得到assets/folder/目录下的所有文件的文件名，以便后面打开操作时使用
		} catch (IOException e) {
			e.printStackTrace();
		}
		return folder+"/"+listImage[0];
	}
	
	public AnimationDrawable addAnimation(String animFolder, boolean isOneShot)
	{
		ImageView ivCache = new ImageView(this);
		AnimationDrawable frameAnim = new AnimationDrawable();
		String[] listImage = null;
		try {
			AssetManager am = getAssets();
			listImage = am.list(animFolder);
			InputStream af = null;
			for (int i = 0; i < listImage.length; i++) {
				af = am.open(animFolder+"/"+listImage[i]);
				ivCache.setImageBitmap(BitmapFactory.decodeStream(af));
				frameAnim.addFrame(ivCache.getDrawable(), 100);
			}
			af.close();
		} catch (IOException e) {
		}
		frameAnim.setOneShot(isOneShot);
		return frameAnim;
	}

	public AnimationDrawable addAnimation(int id, String animFolder, float x, float y, boolean isOneShot)
	{
		ImageView ivFrame = addPic(id, listFolder(animFolder), x, y, false, 0, 0);
		AnimationDrawable frameAnim = addAnimation(animFolder, isOneShot);
		ivFrame.setBackgroundDrawable(frameAnim);
		return frameAnim;
	}
	
	public int readIntData(String key)
	{
		settings = this.getSharedPreferences("peter", this.MODE_PRIVATE);
		return settings.getInt(key, 0);
	}
	
	public void writeIntData(String key,int value)
	{
		settings = this.getSharedPreferences("peter", this.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	int time = 120;
	Timer timer = new Timer();
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what) {
			case 1://倒计时
				--time;
//				Log.i(TAG, "time: "+time);
				if (time == -1) {
					isQuesInit = false;
					showWonderful();
					timer.cancel();
					timer = null;
				}
				if (time < 0) {
					stopTimer();
					return;
				}
				
				if (time == 59) {
					num1.setBackgroundDrawable(animNumber.getFrame(0));
				}
				if (time > 59) {
					num2.setBackgroundDrawable(animNumber.getFrame((time - 60) / 10));
					num3.setBackgroundDrawable(animNumber.getFrame((time - 60) % 10));
				} else {
					num2.setBackgroundDrawable(animNumber.getFrame((time) / 10));
					num3.setBackgroundDrawable(animNumber.getFrame((time) % 10));
				}

				break;
				
			case 2:
				startActivity();
				break;
				
				
			case 11:
				soundPool.play(SOUND_RIGHT, 1, 1, 0, 0, 1);
				playAnimaiton(animStar2, 416, -5, 450, 440);
				break;
				
			case 12:
				soundPool.play(SOUND_RIGHT, 1, 1, 0, 0, 1);
				playAnimaiton(animStar3, 595, 91, 330, 310);
				break;
				
			case 13:
				soundPool.play(SOUND_RIGHT, 1, 1, 0, 0, 1);
				playAnimaiton(animStar1, 351, 98, 330, 310);
				break;
				
			case 14:
				replaceActivity();
				break;
				
			case 21:
				if (null == findViewById(20003)) {
					return;
				}
				findViewById(20003).animate().translationX(280 * xScale).setDuration(1000).start();
				break;
			}
		}
	};
	
	/**
	 * 添加计时器
	 * @param timer
	 * @param handler
	 * @param delay
	 * @param what
	 * @param period
	 */
	public void addTimerTask(Timer timer, final Handler handler, long delay, final int what, long period)
	{
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				handler.sendEmptyMessage(what);
			}
		}, delay, period);
	}
	
	/**
	 * 添加倒计时
	 */
	public void addTimeProgress() {
		if (null == timer) {
			return;
		}
		if (null == animNumber) {
			animNumber = addAnimation("number", false);
		}
		addPic(101, "count_down_bg.png", 115, 11);
		num1 = addPic(102, "number/num1.png", 181, 37);
		num2 = addPic(103, "number/num5.png", 222, 37);
		num3 = addPic(104, "number/num9.png", 252, 37);
		addTimerTask(timer, handler, 0, 1, 1000);
	}
	
	/**
	 * 添加Loading条
	 */
	public void addLoading() {
		if (null == animPoint) {
			animPoint = addAnimation("loading/point", false);
		}
		addPic(20000, "loading/bg.png", 0, 0);
		addPic(20001, "loading/loading.png", 432, 400);
		playAnimaiton(20002, animPoint, 863, 417, 200, 50);
		addAnimation(20003, "loading/car", 408, 128, false).start();
		handler.sendEmptyMessageDelayed(21, 100);
	}
	
	/**
	 * 删除Loading条
	 */
	public void removeLoading() {
		for (int i = 20000; i < 20004; i++) {
			layout.removeView(findViewById(i));
		}
	}
	
	/**
	 * 加载Wonderful界面中的序列帧到内存
	 */
	public void loadAnim()
	{
		animPoint = addAnimation("loading/point", false);
		animNumber = addAnimation("number", false);
		loadStars();
	}

	private void loadStars() {
		animHari = addAnimation("wonderful/hari", true);
		animStar1 = addAnimation("wonderful/star1", true);
		animStar2 = addAnimation("wonderful/star2", true);
		animStar3 = addAnimation("wonderful/star3", true);
	}
	
	/**
	 * 播放序列帧
	 * @param id
	 * @param ad
	 */
	public void playAnimaiton(int id, AnimationDrawable ad) {
		findViewById(id).setBackgroundDrawable(ad);
		ad.stop();
		ad.start();
	}
	
	public void playAnimaiton(AnimationDrawable ad, float x, float y, float w, float h) {
		playAnimaiton(-1, ad, x, y, w, h);
	}
	
	public void playAnimaiton(int id, AnimationDrawable ad, float x, float y, float w, float h) {
		addPic(id, ad, x, y, w, h);
		ad.stop();
		ad.start();	
	}
	
	/**
	 * 游戏结束，显示Wonderful界面
	 */
	public void showWonderful()
	{
		releaseBgSound();
		String key = String.valueOf(getClass()).substring(28);
		writeIntData(key, readIntData(key) + 1);
		addPic(-1, "wonderful/bg.png", 0, 0, 1280, 720);
		addPic(-1, "wonderful/light.png", 0, 0, 1280, 720);
		if (null == animHari) {
			loadStars();
		}
		playAnimaiton(animHari, 451, 34, 300, 480);
		addPic(-1, "wonderful/star.png", 290, 87);
		addPic(-1, "wonderful/wonderful.png", 256, 128);

		handler.sendEmptyMessageDelayed(14, 5000);// 返回地图界面
		
		soundPool.play(SOUND_WONDERFUL, 1, 1, 0, 0, 1);

		if (countRightNum > 0) {
			handler.sendEmptyMessageDelayed(13, 800);// 第一颗星
		}
		if (countRightNum > 4) {
			handler.sendEmptyMessageDelayed(11, 1600);// 第二颗星
		}
		if (countRightNum > 10) {
			handler.sendEmptyMessageDelayed(12, 2400);// 第三颗星
		}
	}
	
	/**
	 * 获取版本号
	 * @param packageName
	 * @return
	 */
	public String getVersion(String packageName) {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packInfo = pm.getPackageInfo(packageName, 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
}
