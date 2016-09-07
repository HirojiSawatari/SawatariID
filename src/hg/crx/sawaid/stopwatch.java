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
import android.os.Handler;  
import android.view.KeyEvent;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.TextView;  
import android.widget.Toast;

/**
 * @since 1.0
 * @version 3.2.0
 * @author He Tao of Sawatari Corp.
 */

public class stopwatch extends Activity {        
    private TextView minText;
    private long exitTime = 0;
    private TextView secText;
    private Button start;
    private Button stop;
    private boolean isPaused = false; 
    private int timeUsedInsec;        
    private Handler uiHandle = new Handler(){  
        public void handleMessage(android.os.Message msg) {  
            switch(msg.what){  
            case 1:  
                if(!isPaused) {  
                    addTimeUsed();  
                    updateClockUI();  
                }  
                uiHandle.sendEmptyMessageDelayed(1, 1000);  
                break;  
            default: break;  
            }  
        }  
    };        
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
        setContentView(R.layout.stopwatch);  
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
        final TextView Text=(TextView) findViewById(R.id.textView1);
        final Button Button1=(Button) findViewById(R.id.button1);
        minText = (TextView) findViewById(R.id.min);  
        secText = (TextView) findViewById(R.id.sec);  
        start = (Button) findViewById(R.id.start);  
        stop = (Button) findViewById(R.id.stop);  
        Text.setTextColor(Color.BLACK);
        if(lan_code == 1){
        	Text.setText("\n   Sawatari™ ストップウォッチ\n");
        	start.setText("開始");
        	stop.setText("終了");
        	Button1.setText("戻る");
        }
        minText.setTextColor(Color.BLACK);
        secText.setTextColor(Color.RED);
        Button1.setTextColor(Color.BLACK);
        start.setTextColor(Color.BLACK);
        stop.setTextColor(Color.BLACK);
        this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        start.setOnClickListener(new OnClickListener() {              
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                uiHandle.removeMessages(1);  
                startTime();  
                isPaused = false;  
            }  
        });  
        stop.setOnClickListener(new OnClickListener() {             
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                isPaused = true;  
                timeUsedInsec = 0;  
            }  
        });  
        Button1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent(stopwatch.this,SawatariIDActivity.class);
    			stopwatch.this.startActivity(intent);
    			stopwatch.this.finish();
        	}
        });
    }     
    @Override  
    //暂停计时
    protected void onPause() {  
        // TODO Auto-generated method stub  
        super.onPause();  
        isPaused = true;  
    }           
    @Override 
    //恢复计时
    protected void onResume() {  
        // TODO Auto-generated method stub  
        super.onResume();  
        isPaused = false;  
    }   
    //开始计时
    private void startTime(){  
        uiHandle.sendEmptyMessageDelayed(1, 1000);  
    } 
    //显示时间
    private void updateClockUI(){  
        minText.setText(getMin() + ":");  
        secText.setText(getSec());  
    }     
    //获取时间
    public void addTimeUsed(){  
        timeUsedInsec = timeUsedInsec + 1;  
    }  
    //分钟定义
    public CharSequence getMin(){  
        return String.valueOf(timeUsedInsec / 60);  
    }  
    //秒定义
    public CharSequence getSec(){  
        int sec = timeUsedInsec % 60;  
        return sec < 10? "0" + sec :String.valueOf(sec);  
    }  
   
}  