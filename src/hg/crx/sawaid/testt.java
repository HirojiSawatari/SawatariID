/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @since 1.0
 * @version 3.2.0
 * @author He Tao of Sawatari Corp.
 */

public class testt extends Activity {
	int [] image = new int []{
			R.drawable.bk,
			R.drawable.we,
			R.drawable.rd,
			R.drawable.gn,
			R.drawable.be,
			R.drawable.yw,
			R.drawable.pe,
			R.drawable.rn
			};
	int currentImg = 0;
	private long exitTime = 0;
	private static final int ITEM_1=Menu.FIRST;
	//菜单键样式
	public boolean onPrepareOptionsMenu(Menu menu){
		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
	    int lan_code = lan_info.getInt("lan_code",0);
    	menu.clear();
    	if(lan_code == 1){
    		menu.add(0,ITEM_1,0,"戻る").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	}
    	else{
    		menu.add(0,ITEM_1,0,"返回主界面").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	}
    	return super.onCreateOptionsMenu(menu);
    }
	//菜单键功能
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case ITEM_1:
			Intent intent = new Intent(testt.this,SawatariIDActivity.class);
			testt.this.startActivity(intent);
			testt.this.finish();
		}	
		return true;
	}
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
		setContentView(R.layout.test);
		LinearLayout main = (LinearLayout) findViewById(R.id.layout);
		final ImageView images = new ImageView(this);
		main.addView(images);
		images.setImageResource(image[0]);
		images.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				if(currentImg>=7){
					currentImg=-1;
				}
				images.setImageResource(image[++currentImg]);
			}
		});
	}
}