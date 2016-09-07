/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class anim extends Activity {
    private MediaPlayer mediaPlayer=null;
    /**Called when the activity is first created. */
	@Override
	//界面样式
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.anim);
		mediaPlayer=MediaPlayer.create(anim.this, R.raw.sawaformiku);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent mainIntent=new Intent(anim.this,splashScreen.class);
				anim.this.startActivity(mainIntent);
				anim.this.finish();
			}
		},3000);
	}
}
