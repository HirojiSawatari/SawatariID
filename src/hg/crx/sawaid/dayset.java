/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class dayset extends Activity {
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
			Intent intent = new Intent(dayset.this,SawatariIDActivity.class);
			dayset.this.startActivity(intent);
			dayset.this.finish();
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
		setContentView(R.layout.dayset);
		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
		final EditText Text=(EditText) findViewById(R.id.editText1);
		final DatePicker dp=(DatePicker) findViewById(R.id.datePicker1);
        final Button Button1=(Button) findViewById(R.id.button1);
        final TextView Text1=(TextView) findViewById(R.id.textView1);
        final TextView Text2=(TextView) findViewById(R.id.textView2);
        final TextView Text3=(TextView) findViewById(R.id.textView3);
        if(lan_code == 1){
    		Text1.setText("\nカウントダウン設定");
    		Text2.setText("\nメモリアルデーを入力");
    		Text3.setText("\n\n日付を入力");
    		Button1.setText("決定");
    	}
        Button1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
                int lan_code = lan_info.getInt("lan_code",0);
        		String SharePreference_name = "DAY";
				SharedPreferences shp = getSharedPreferences(SharePreference_name, Context.MODE_WORLD_READABLE);
				SharedPreferences.Editor editor = shp.edit();
				String ne = Text.getText().toString();
				int yr = dp.getYear();
				int mh = dp.getMonth();
				int dy = dp.getDayOfMonth();
				editor.putString("NAME",ne);
				editor.putInt("YEAR",yr);
				editor.putInt("MONTH",mh);
				editor.putInt("DAY",dy);
				editor.commit();
				if(lan_code == 1){
					Toast.makeText(getApplicationContext(), "設定完了", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "纪念日设置信息已保存，将在桌面组件重新加载后生效", Toast.LENGTH_SHORT).show();
				}
        	}
        });
	}
}