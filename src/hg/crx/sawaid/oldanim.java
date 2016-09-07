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
import android.widget.Toast;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class oldanim extends Activity {
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
			Intent intent = new Intent(oldanim.this,SawatariIDActivity.class);
			oldanim.this.startActivity(intent);
			oldanim.this.finish();
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
		setContentView(R.layout.oldanim);
		this.getWindow().setBackgroundDrawableResource(R.drawable.we);
		final ImageView images1 = (ImageView) findViewById(R.id.imageView1);
		final ImageView images2 = (ImageView) findViewById(R.id.imageView2);
		images1.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				Intent intent = new Intent(oldanim.this,oanim.class);
				oldanim.this.startActivity(intent);
				oldanim.this.finish();
			}
		});
		images2.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				Intent intent = new Intent(oldanim.this,nanim.class);
				oldanim.this.startActivity(intent);
				oldanim.this.finish();
			}
		});
	}
}