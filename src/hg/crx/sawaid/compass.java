/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class compass extends Activity {
	private long exitTime = 0;
	private ImageView imageView;
	private SensorManager manager;
	private SensorListener listener = new SensorListener();
	//双击返回键退出
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
            if((System.currentTimeMillis()-exitTime) > 2000){
            	if(lan_code == 1){
            		Toast.makeText(getApplicationContext(), "もう一度押すとプログラムを终了します", Toast.LENGTH_SHORT).show(); 
            	}
            	else{
            		Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();      
            	}
                exitTime = System.currentTimeMillis();   
            } else {
                finish();
                System.exit(0);
            }
            return true;   
        }
        return super.onKeyDown(keyCode, event);
    }
	@Override
	//界面样式
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compass);
		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
		final Button Button1=(Button) findViewById(R.id.button1);
		final TextView Text=(TextView) findViewById(R.id.textView1);
		if(lan_code == 1){
    		Text.setText("Sawatari™  コンパス\n\n"); 
    		Button1.setText("戻る");
    	}
		imageView = (ImageView) this.findViewById(R.id.imageView);
		imageView.setKeepScreenOn(true);
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Text.setTextColor(Color.BLACK);
		this.getWindow().setBackgroundDrawableResource(R.drawable.background);
		Button1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent(compass.this,SawatariIDActivity.class);
    			compass.this.startActivity(intent);
    			compass.this.finish();
        	}
        });
	}
	@Override
	//恢复
	protected void onResume() {
		Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		manager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}
	@Override
	//暂停
	protected void onPause() {
		manager.unregisterListener(listener);
		super.onPause();
	}
	//指南功能
	private final class SensorListener implements SensorEventListener {
		private float predegree = 0;
		public void onSensorChanged(SensorEvent event) {
			float degree = event.values[0];
			RotateAnimation animation = new RotateAnimation(predegree, degree,
					Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
			animation.setDuration(2000);
			imageView.startAnimation(animation);
			predegree=-degree;
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}
}