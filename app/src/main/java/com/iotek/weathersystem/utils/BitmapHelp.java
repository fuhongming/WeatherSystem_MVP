package com.iotek.weathersystem.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.iotek.weathersystem.R;
import com.lidroid.xutils.BitmapUtils;

public class BitmapHelp {

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 *
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(Context appContext) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(appContext);
			bitmapUtils.configDefaultLoadingImage(R.drawable.defult);
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.defult);
			bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);

//          bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(this).scaleDown(3));
//			bigPicDisplayConfig = new BitmapDisplayConfig();
//			bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用, 图片太大时容易OOM。
//			bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(this));
		}
		return bitmapUtils;
	}
}
