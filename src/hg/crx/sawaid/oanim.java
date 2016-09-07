/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class oanim extends Activity {
	@Override
	//界面样式
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.oanim);
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent mainIntent=new Intent(oanim.this,oldanim.class);
				oanim.this.startActivity(mainIntent);
				oanim.this.finish();
			}
		},3000);
	}
}
