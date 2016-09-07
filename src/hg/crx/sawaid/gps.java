/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class gps extends Activity {
	private long exitTime = 0;
    private TextView locationInfoTextView = null;
    private Button startButton = null;
    private LocationClient locationClient = null;
    private static final int UPDATE_TIME = 5000;
    private static int LOCATION_COUTNS = 0;
    @Override
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
    //界面样式
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sawagps);
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        final int lan_code = lan_info.getInt("lan_code",0);
        final TextView Text=(TextView) findViewById(R.id.textView1);
        final TextView Text2=(TextView) findViewById(R.id.textView2);
        final Button Button1=(Button) findViewById(R.id.button1);
        this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        locationInfoTextView = (TextView) this.findViewById(R.id.tv_loc_info);
        startButton = (Button) this.findViewById(R.id.btn_start);
        locationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        
        option.setCoorType("bd09ll");       
        option.setPriority(LocationClientOption.NetWorkFirst);
        option.setProdName("LocationDemo"); 
        option.setScanSpan(UPDATE_TIME);    
        if(lan_code == 1){
        	Text.setText("\n            Sawatari™ロケータ\n");
        	Text2.setText("\n               ©2012 Baidu / Google / Sawatari\n\n");
        	Button1.setText("戻る");
        	startButton.setText("衛星測位開始");
        }
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDLocationListener() {
            public void onReceiveLocation(BDLocation location) {
            	if(lan_code == 1){
            		// TODO Auto-generated method stub
            		if (location == null) {
            			return;
            		}	
            		StringBuffer sb = new StringBuffer(256);
            		sb.append("時間：");
            		sb.append(location.getTime());
            		sb.append("\n緯度（N）：");
            		sb.append(location.getLatitude());
            		sb.append("\n経度（E）：");
            		sb.append(location.getLongitude());
            		sb.append("\n誤差範囲（m）：");
            		sb.append(location.getRadius());
            		if (location.getLocType() == BDLocation.TypeGpsLocation){
            			sb.append("\nスピード（km/h）");
            			sb.append(location.getSpeed());
            			sb.append("\n衛星：");
            			sb.append(location.getSatelliteNumber());
            		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
            			sb.append("\nアドレス：");
            			sb.append(location.getAddrStr());
            		}
            		LOCATION_COUTNS ++;
            		sb.append("\nリフレッシュレート：");
            		sb.append(String.valueOf(LOCATION_COUTNS));
            		locationInfoTextView.setText(sb.toString());
            	
            	}
            	else{
            		// TODO Auto-generated method stub
            		if (location == null) {
            			return;
            		}	
            		StringBuffer sb = new StringBuffer(256);
            		sb.append("时间：");
            		sb.append(location.getTime());
            		sb.append("\n纬度：北纬");
            		sb.append(location.getLatitude());
            		sb.append("\n经度：东经");
            		sb.append(location.getLongitude());
            		sb.append("\n误差范围（m）：");
            		sb.append(location.getRadius());
            		if (location.getLocType() == BDLocation.TypeGpsLocation){
            			sb.append("\n速度（km/h）");
            			sb.append(location.getSpeed());
            			sb.append("\n搜星数：");
            			sb.append(location.getSatelliteNumber());
            		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
            			sb.append("\n具体地址：");
            			sb.append(location.getAddrStr());
            		}
            		LOCATION_COUTNS ++;
            		sb.append("\n检查位置更新次数：");
            		sb.append(String.valueOf(LOCATION_COUTNS));
            		locationInfoTextView.setText(sb.toString());
            	}
            }
            public void onReceivePoi(BDLocation location) {
            }
        });
        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if(lan_code == 1){
            		if (locationClient == null) {
                        return;
                    }
                    if (locationClient.isStarted()) {
                        startButton.setText("衛星測位開始");
                        locationClient.stop();
                    }else {
                        startButton.setText("衛星測位終了");
                        locationClient.start();
                        locationClient.requestLocation();
                    }
            	}
            	else{
            		if (locationClient == null) {
                        return;
                    }
                    if (locationClient.isStarted()) {
                        startButton.setText("开始定位");
                        locationClient.stop();
                    }else {
                        startButton.setText("停止定位");
                        locationClient.start();
                        locationClient.requestLocation();
                    }
            	} 
            }
        });
        Button1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent(gps.this,SawatariIDActivity.class);
    			gps.this.startActivity(intent);
    			gps.this.finish();
        	}
        });
        startButton.setTextColor(Color.BLACK);
        locationInfoTextView.setTextColor(Color.BLACK);
        Text.setTextColor(Color.BLACK);
        Text2.setTextColor(Color.BLACK);
    }
    @Override
    //定位停止
    protected void onDestroy() {
        super.onDestroy();
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
        }
    }
}