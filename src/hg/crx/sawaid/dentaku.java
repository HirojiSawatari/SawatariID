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
import android.view.Menu;
import android.view.MenuItem;
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

public class dentaku extends Activity{
	private float[] num;
	private String[] Symbol;
	private int m;
	private int n;
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
			Intent intent = new Intent(dentaku.this,SawatariIDActivity.class);
			dentaku.this.startActivity(intent);
			dentaku.this.finish();
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
		setContentView(R.layout.dentaku);
		Symbol = new String[50];
		num = new float[50];
		m = 0; //数字个数
		n = 0; //尾结点
		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
		final TextView textField = (TextView) findViewById(R.id.textView1);
		textField.setTextColor(Color.BLACK);
		final Button Button1=(Button) findViewById(R.id.button12);
		final Button Button2=(Button) findViewById(R.id.button13);
		final Button Button3=(Button) findViewById(R.id.button14);
		final Button Button4=(Button) findViewById(R.id.button2);
		final Button Button5=(Button) findViewById(R.id.button5);
		final Button Button6=(Button) findViewById(R.id.button11);
		final Button Button7=(Button) findViewById(R.id.button7);
		final Button Button8=(Button) findViewById(R.id.button6);
		final Button Button9=(Button) findViewById(R.id.button3);
		final Button Button10=(Button) findViewById(R.id.button17);
		final Button Button11=(Button) findViewById(R.id.button8);
		final Button Button12=(Button) findViewById(R.id.button4);
		final Button Button13=(Button) findViewById(R.id.button1);
		final Button Button14=(Button) findViewById(R.id.button9);
		final Button Button15=(Button) findViewById(R.id.button15);
		final Button Button16=(Button) findViewById(R.id.button16);
		final Button Button17=(Button) findViewById(R.id.button10);
		final TextView Text1=(TextView) findViewById(R.id.TextView01);
		if(lan_code == 1){
    		Text1.setText("  Sawatari™ 電卓");
    	}
		Button1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "1");
        	}
		});
		Button2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "2");
        	}
		});
		Button3.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "3");
        	}
		});
		Button4.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "4");
        	}
		});
		Button5.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "5");
        	}
		});
		Button6.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "6");
        	}
		});
		Button7.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "7");
        	}
		});
		Button8.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "8");
        	}
		});
		Button9.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "9");
        	}
		});
		Button10.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				textField.setText(calText + "0");
        	}
		});
		Button11.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				if(calText.length() == 0){
					;
				}
				else{
					String fcalText = calText.substring(n);
					num[m] = Float.parseFloat(fcalText);
					Symbol[m] = "+";
					n = calText.length() + 1;
					m ++;
					textField.setText(calText + "+");
				}
			}
		});
		Button12.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				if(calText.length() == 0){
					;
				}
				else{
					String fcalText = calText.substring(n);
					num[m] = Float.parseFloat(fcalText);
					Symbol[m] = "-";
					n = calText.length() + 1;
					m ++;
					textField.setText(calText + "-");
				}
        	}
		});
		Button13.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				if(calText.length() == 0){
					;
				}
				else{
					String fcalText = calText.substring(n);
					num[m] = Float.parseFloat(fcalText);
					Symbol[m] = "*";
					n = calText.length() + 1;
					m ++;
					textField.setText(calText + "*");
				}
        	}
		});
		Button14.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				if(calText.length() == 0){
					;
				}
				else{
					String fcalText = calText.substring(n);
					num[m] = Float.parseFloat(fcalText);
					Symbol[m] = "/";
					n = calText.length() + 1;
					m ++;
					textField.setText(calText + "/");
				}
        	}
		});
		Button15.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String calText = textField.getText().toString();
				int indexa = calText.lastIndexOf("+");
				int indexb = calText.lastIndexOf("-");
				int indexc = calText.lastIndexOf("*");
				int indexd = calText.lastIndexOf("/");
				int indexp = calText.lastIndexOf(".");
				if((indexp <= indexa || indexp <= indexb || indexp <= indexc || indexp <= indexd) && calText.length() != 0){
					textField.setText(calText + ".");
				}
        	}
		});
		Button16.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String finalCalText = textField.getText().toString();
        		int count = finalCalText.length();
        		char lastSym = finalCalText.charAt(count - 1);
        		if(m == 0){
					;
				}
        		else if(lastSym == '+' || lastSym == '-' || lastSym == '*' || lastSym == '/'){
        			;
        		}
				else{
					float finalNum = num[0];
					String fcalText = finalCalText.substring(n);
					num[m] = Float.parseFloat(fcalText);
					for(int i = 0;i < m ;i ++){	
						if(Symbol[i] == "+"){
							finalNum = finalNum + num[i + 1];
						}
						else if(Symbol[i] == "-"){
							finalNum = finalNum - num[i + 1];
						}
						else if(Symbol[i] == "*"){
							finalNum = finalNum * num[i + 1];
						}
						else{
							finalNum = finalNum / num[i + 1];
						}
					}
					String fNum = new String();
					fNum =  finalNum + "";
					textField.setText(fNum);
					n = 0;
					m = 0;
					num = new float[100];
					Symbol = new String[100];
				}
        	}
		});
		Button17.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
				textField.setText("");
				n = 0;
				m = 0;
				num = new float[100];
				Symbol = new String[100];
        	}
		});
	}
}
