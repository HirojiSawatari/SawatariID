/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class recorder extends Activity {
	private long exitTime = 0;
	private Button recordButton;
	private Button playButton;
	private MediaPlayer mPlayer=null;
	private MediaRecorder mRecorder=null;
	private String file=null;
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
        setContentView(R.layout.recorder);
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
        recordButton=(Button) findViewById(R.id.bt_Record);
        playButton=(Button) findViewById(R.id.bt_Playing);
        final Button Button1=(Button) findViewById(R.id.button1);
        final TextView Text=(TextView) findViewById(R.id.textView1);
        final TextView Text2=(TextView) findViewById(R.id.textView2);
        if(lan_code == 1){
        	Text.setText("Copyright © 2012 Sawatari(C.R.X) Corp. All rights reserved");
        	Text2.setText("\n           Sawatari™レコーダー\n");
        	recordButton.setText("レコード開始");
        	playButton.setText("放送開始");
        	Button1.setText("戻る");
        }
        this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        if(lan_code == 1){
        	recordButton.setOnClickListener(new OnClickListener() {
        		boolean isStartRecord=true;
        		public void onClick(View v) {				
        			if (isStartRecord) {
        				startRecording();
        				recordButton.setText("レコード終了");
        				}else {
        					stopRecording();
        					recordButton.setText("レコード開始");
        				}
        			isStartRecord=!isStartRecord;
        		}
        	});
        }
        else{
        	recordButton.setOnClickListener(new OnClickListener() {
        		boolean isStartRecord=true;
        		public void onClick(View v) {				
        			if (isStartRecord) {
        				startRecording();
        				recordButton.setText("停止录音");
        				}else {
        					stopRecording();
        					recordButton.setText("录音");
        				}
        			isStartRecord=!isStartRecord;
        		}
        	});
        }
        if(lan_code == 1){
        	playButton.setOnClickListener(new OnClickListener() {
			boolean isStartPlaying=true;
			public void onClick(View v) {
				if (isStartPlaying) {
					startPlay(file);
					playButton.setText("放送終了");
				}else{
					stopPlaying();
					playButton.setText("放送開始");
				}
				isStartPlaying=!isStartPlaying;
				}
        	});
        }
        else{
        	playButton.setOnClickListener(new OnClickListener() {
				boolean isStartPlaying=true;
				public void onClick(View v) {
					if (isStartPlaying) {
						startPlay(file);
						playButton.setText("停止播放");
					}else{
						stopPlaying();
						playButton.setText("播放");
					}
					isStartPlaying=!isStartPlaying;
				}
			});
        }
        Button1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent(recorder.this,SawatariIDActivity.class);
    			recorder.this.startActivity(intent);
    			recorder.this.finish();
        	}
        });
        Text.setTextColor(Color.BLACK);
        Text2.setTextColor(Color.BLACK);
    }
    //开始录音
    private void startRecording(){  	
    	mRecorder=new MediaRecorder();
    	mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
    	mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
    	mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);	
    	file=newFileName();
    	mRecorder.setOutputFile(file);
    	try {
			mRecorder.prepare();
		} catch (IOException e) {
			// TODO: handle exception
		}
    	mRecorder.start();
    }
    //停止录音
    private void stopRecording(){
    	mRecorder.stop();
    	mRecorder.release();
    	mRecorder=null;
    } 
    //放音
    private void startPlay(String file){
    	mPlayer=new MediaPlayer();
    	try {
			mPlayer.setDataSource(file);
			mPlayer.prepare();
			mPlayer.start();	
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    //停止放音
    private void stopPlaying(){
    	mPlayer.release();
    	mPlayer=null;
    }
    //定义录音名称
    private String newFileName(){
    	String mFileName=Environment.getExternalStorageDirectory().getAbsolutePath();
    	String s=new SimpleDateFormat("yyyy-MM-dd hhmmss").format(new Date());
		return mFileName+="/Sawarcd_"+s+".amr";	
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	if (mRecorder!=null) {
			mRecorder.release();
			mRecorder=null;
		}if (mPlayer!=null) {
			mPlayer.release();
			mPlayer=null;
		}
    }

}
