/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class splashScreen extends Activity {
    /**Called when the activity is first created. */
	@Override
	//界面样式
	public void onCreate(Bundle icicle){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.splashscreen);
		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
		if(lan_code == 1){
			this.getWindow().setBackgroundDrawableResource(R.drawable.loading_j);
		}
		else{
			this.getWindow().setBackgroundDrawableResource(R.drawable.loading);
		}
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent mainIntent=new Intent(splashScreen.this,SawatariIDActivity.class);
				splashScreen.this.startActivity(mainIntent);
				splashScreen.this.finish();
			}
		},2900);
	}
}
		
