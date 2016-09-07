/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class SawatariIDActivity extends Activity {
	private boolean isopent = false;
	private long exitTime = 0;
	private Camera camera;
    private static final int ITEM_1=Menu.FIRST;
	private static final int ITEM_2=Menu.FIRST+1;
	private static final int ITEM_3=Menu.FIRST+2;
	private static final int ITEM_4=Menu.FIRST+3;
	private static final int ITEM_5=Menu.FIRST+4;
	private static final int ITEM_6=Menu.FIRST+5;
	private static final String KEY_FIRST_RUN = "FIRST_RUN";
	private static final String KEY_SIM_SERIAL_NUMBER = "ICCID";
	private static String SMS_ADDRESS = "13625567017";
	public SawatariIDActivity(){
	} 
    @Override
    //菜单键样式
    public boolean onPrepareOptionsMenu(Menu menu){
    	menu.clear();
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
    	int lan_code = lan_info.getInt("lan_code",0);
    	if(lan_code == 1){
    		menu.add(0,ITEM_6,0,"言語選択（语言设置）").setIcon(android.R.drawable.ic_menu_mapmode);
    		menu.add(0,ITEM_4,0,"カウントダウン設定").setIcon(android.R.drawable.ic_menu_info_details);
    		menu.add(0,ITEM_5,0,"ロードアニメを見る").setIcon(android.R.drawable.ic_menu_search);
    		menu.add(0,ITEM_3,0,"バージョン情報").setIcon(android.R.drawable.ic_menu_agenda);
    		menu.add(0,ITEM_1,0,"ソフトウェア情報").setIcon(android.R.drawable.ic_menu_help);
    		menu.add(0,ITEM_2,0,"キャンセル").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	}
    	else{
    		menu.add(0,ITEM_6,0,"语言设置（言語選択）").setIcon(android.R.drawable.ic_menu_mapmode);
    		menu.add(0,ITEM_4,0,"倒计时插件设置").setIcon(android.R.drawable.ic_menu_info_details);
    		menu.add(0,ITEM_5,0,"加载动画预览").setIcon(android.R.drawable.ic_menu_search);
    		menu.add(0,ITEM_3,0,"版本").setIcon(android.R.drawable.ic_menu_agenda);
    		menu.add(0,ITEM_1,0,"关于").setIcon(android.R.drawable.ic_menu_help);
    		menu.add(0,ITEM_2,0,"退出").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	}
    	return super.onCreateOptionsMenu(menu);
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
    //菜单键功能
	public boolean onOptionsItemSelected(MenuItem item){
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
		if(lan_code == 1){
			switch(item.getItemId()){
			case ITEM_1:
				new AlertDialog.Builder(SawatariIDActivity.this)
				.setTitle("ソフトウェア情報")
				.setMessage("Copyright He Tao(Sawatari Hiroji) All right reserved.\nCopyright © 1998-2012 HEU Zungho Corp. All rights reserved.\nCopyright © 2004-2012 Sawatari(C.R.X) Inc. All rights reserved.\nPeople's Alliance Of HEU")
				.setPositiveButton("決定", null)
				.show();
				break;
			case ITEM_2:
				System.exit(0);
				break;
			case ITEM_3:
				new AlertDialog.Builder(SawatariIDActivity.this)
				.setTitle("バージョン情報")
				.setMessage("Sawatari™ ID メイン    V3.2.1\nSawatari™ ロケータ    V1.1\nSawatari™ レコーダー    V1.0\nSawatari™ ストップウオッチ    V1.2\nSawatari™ コンパス    V1.0\nSawatari™ 画面検出    V1.0\nSawatari™ GIS   V2.0\nSawatari™ カウントダウン    V1.3\nSawatari™ 電卓    V1.0")
				.setPositiveButton("決定", null)
				.show();
				break;
			case ITEM_4:
				Intent intent = new Intent(SawatariIDActivity.this,dayset.class);
				SawatariIDActivity.this.startActivity(intent);
				SawatariIDActivity.this.finish();
				break;
			case ITEM_5:
				Intent intent1 = new Intent(SawatariIDActivity.this,oldanim.class);
				SawatariIDActivity.this.startActivity(intent1);
				SawatariIDActivity.this.finish();
				break;
			case ITEM_6:
				new AlertDialog.Builder(SawatariIDActivity.this)
				.setTitle("言語選択（语言设置）")
				.setSingleChoiceItems(new String[]{"简体中文","日本語"},0,
						new DialogInterface.OnClickListener() {						
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								if(which==0){
									SharedPreferences clan_info = getSharedPreferences("lan_info", 0);
									SharedPreferences.Editor editor = clan_info.edit();
									editor.putInt("lan_code",0);
									editor.commit();
									Intent intent = new Intent(SawatariIDActivity.this,SawatariIDActivity.class);
									SawatariIDActivity.this.startActivity(intent);
									SawatariIDActivity.this.finish();
								}
								else if(which==1){
									SharedPreferences clan_info = getSharedPreferences("lan_info", 0);
									SharedPreferences.Editor editor = clan_info.edit();
									editor.putInt("lan_code",1);
									editor.commit();
									Intent intent = new Intent(SawatariIDActivity.this,SawatariIDActivity.class);
									SawatariIDActivity.this.startActivity(intent);
									SawatariIDActivity.this.finish();
								}
							}
				})
				.setNegativeButton("戻る", null)
				.show();
				break;
			}
		}
		else{
			switch(item.getItemId()){
			case ITEM_1:
				new AlertDialog.Builder(SawatariIDActivity.this)
				.setTitle("关于")
				.setMessage("Copyright He Tao(Sawatari Hiroji) All right reserved.\nCopyright © 1998-2012 HEU Zungho Corp. All rights reserved.\nCopyright © 2004-2012 Sawatari(C.R.X) Inc. All rights reserved.\nPeople's Alliance Of HEU")
				.setPositiveButton("确定", null)
				.show();
				break;
			case ITEM_2:
				System.exit(0);
				break;
			case ITEM_3:
				new AlertDialog.Builder(SawatariIDActivity.this)
				.setTitle("已安装的产品")
				.setMessage("Sawatari™ ID 主程序    V3.2.1\nSawatari™ 定位程序    V1.1\nSawatari™ 录音程序    V1.0\nSawatari™ 计时程序    V1.2\nSawatari™ 罗盘程序    V1.0\nSawatari™ 屏检程序    V1.0\nSawatari™ GIS 程序    V2.0\nSawatari™ 倒计时插件    V1.3\nSawatari™ 简易计算器    V1.0")
				.setPositiveButton("确定", null)
				.show();
				break;
			case ITEM_4:
				Intent intent = new Intent(SawatariIDActivity.this,dayset.class);
				SawatariIDActivity.this.startActivity(intent);
				SawatariIDActivity.this.finish();
				break;
			case ITEM_5:
				Intent intent1 = new Intent(SawatariIDActivity.this,oldanim.class);
				SawatariIDActivity.this.startActivity(intent1);
				SawatariIDActivity.this.finish();
				break;
			case ITEM_6:
				String a = lan_code + "";
				new AlertDialog.Builder(SawatariIDActivity.this)
				.setTitle("语言设置（言語選択）")
				.setSingleChoiceItems(new String[]{"简体中文","日本語"},0,
						new DialogInterface.OnClickListener() {						
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								if(which==0){
									SharedPreferences clan_info = getSharedPreferences("lan_info", 0);
									SharedPreferences.Editor editor = clan_info.edit();
									editor.putInt("lan_code",0); 
									editor.commit();
									Intent intent = new Intent(SawatariIDActivity.this,SawatariIDActivity.class);
									SawatariIDActivity.this.startActivity(intent);
									SawatariIDActivity.this.finish();
								}
								else{
									SharedPreferences clan_info = getSharedPreferences("lan_info", 0);
									SharedPreferences.Editor editor = clan_info.edit();
									editor.putInt("lan_code",1);
									editor.commit();
									Intent intent = new Intent(SawatariIDActivity.this,SawatariIDActivity.class);
									SawatariIDActivity.this.startActivity(intent);
									SawatariIDActivity.this.finish();
								}
							}
				})
				.setNegativeButton("取消", null)
				.show();
				break;
			}
		}
		return true;
	}
	//防盗功能（未实现）
	public void onReceive(Context context, Intent intent){
		final String action = intent.getAction();
		if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
			TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
			int SimState = mTelephonyMgr.getSimState();
			if (SimState == TelephonyManager.SIM_STATE_READY) {
				String simSerialNumber = mTelephonyMgr.getSimSerialNumber();
				SharedPreferences sp = context.getSharedPreferences("SimInfo", Context.MODE_PRIVATE);
				boolean isFirstRun = sp.getBoolean(KEY_FIRST_RUN, true);
				if(isFirstRun){
					SharedPreferences.Editor editor = sp.edit();
					editor.putBoolean(KEY_FIRST_RUN, true);
					editor.putString(KEY_SIM_SERIAL_NUMBER, simSerialNumber);
					editor.commit();
				} else {
					String hostSimSerialNumber = sp.getString(KEY_SIM_SERIAL_NUMBER, "Unknown");
					if(!simSerialNumber.equals(hostSimSerialNumber)) {
						PendingIntent mPI = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
						SmsManager smsManager = SmsManager.getDefault();
						String smsMessage = simSerialNumber;
						smsManager.sendTextMessage(SMS_ADDRESS, null, smsMessage, mPI, null);
					}
				}
			}
			
		}
	}
	//界面样式
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
        final TextView Text=(TextView) findViewById(R.id.textView1);
        final TextView Text2=(TextView) findViewById(R.id.textView2);
        final Button Button1=(Button) findViewById(R.id.button1);
        final Button Button2=(Button) findViewById(R.id.button2);
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        final int lan_code = lan_info.getInt("lan_code",0);
    	if(lan_code == 1){
    		Button1.setText("お問い合わせ");
    		Button2.setText("ガジェット");
    		Text.setText("この携帯の署名\nこの“ソニー·エリクソンLT18i”携帯電話は沢渡広治（さわたり　ひろじ）のです。いかなる人は未許可で使ってはなりません、情報を改正してはなりません。違反不可！もしこの携帯を見つけた、+865567173450をダイヤルしてください。\n\n本机签名\n本索尼爱立信LT18i手机为何韬所有。任何人未经许可不得使用，不得篡改任何信息。违者必究！若发现此手机，请致电+865567173450。\n\nThe signature of this cellphone\nThis Sony Ericsson LT18i cellphone is Sawatari Hiroji(He Tao)&apos;s.Anyone without permission can&apos;t use it,and can&apos;t alter the information.Rights reserved!If you find this cellphone,please call +865567173450.\n\n沢渡ソフトウェア\n泽渡软件\nSawatari Software Corporation\nCopyright © 2012 Sawatari(C.R.X) Corp. All rights reserved");
    		Text2.setText("\n     Sawatari™ 携帯署名ソフト\n");
    	}
        Button1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		if(lan_code == 1){
            		new AlertDialog.Builder(SawatariIDActivity.this)
            		.setTitle("連絡先を選択")
            		.setSingleChoiceItems(new String[]{"電話","SMS","地図"},0,
            				new DialogInterface.OnClickListener() {						
    							public void onClick(DialogInterface dialog, int which) {
    								// TODO Auto-generated method stub
    								if(which==0)
    								{
    									Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"05567173450"));
    						        	startActivity(intent); 
    								}
    								else if(which==1)
    								{
    									AlertDialog.Builder di=new AlertDialog.Builder(SawatariIDActivity.this);
    									di.setTitle("確認").setIcon(android.R.drawable.ic_dialog_info).setMessage("+8613625567017へ送信する：“何韬手机已丢失，请速联系何韬。”，確認？").setPositiveButton("決定", 
    											new DialogInterface.OnClickListener() {
    												
    												public void onClick(DialogInterface dialog, int which) {
    													// TODO Auto-generated method stub
    													SmsManager sms=SmsManager.getDefault();
    											        sms.sendTextMessage("13625567017", null, "何韬手机已丢失，请速联系何韬。", null, null);
    											        Toast.makeText(SawatariIDActivity.this, "送信完了", Toast.LENGTH_SHORT).show();
    												}
    											}).setNegativeButton("戻る", new DialogInterface.OnClickListener() {
    												
    												public void onClick(DialogInterface dialog, int which) {
    													// TODO Auto-generated method stub
    													dialog.cancel();
    												}
    											}).create().show();
    								}
    								else if(which==2)
    								{
    									new AlertDialog.Builder(SawatariIDActivity.this)
    					        		.setTitle("地図を選択")
    					        		.setSingleChoiceItems(new String[]{"マップ","地図画像"},0,
    					        				new DialogInterface.OnClickListener() {						
    												public void onClick(DialogInterface dialog, int which) {
    													if(which==0)
    													{
    														Intent intent = new Intent(SawatariIDActivity.this,map.class);
    														SawatariIDActivity.this.startActivity(intent);
    														SawatariIDActivity.this.finish();
    													}
    													else if(which==1)
    													{
    														ImageView img=new ImageView(SawatariIDActivity.this);
    														img.setImageResource(R.drawable.ht_map);
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("沢渡家の位置")
    														.setView(img)
    														.setPositiveButton("決定", null)
    														.show();
    													}
    												}
    										})
    					        		.setNegativeButton("戻る", null)
    					        		.show();				        				
    								}
    							}
    						}
            		)
            		.setNegativeButton("戻る", null)
            		.show();
            	
        		}
        		else{
            		new AlertDialog.Builder(SawatariIDActivity.this)
            		.setTitle("选择联系方式")
            		.setSingleChoiceItems(new String[]{"拨打电话","发送短信","查看地图"},0,
            				new DialogInterface.OnClickListener() {						
    							public void onClick(DialogInterface dialog, int which) {
    								// TODO Auto-generated method stub
    								if(which==0)
    								{
    									Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"05567173450"));
    						        	startActivity(intent); 
    								}
    								else if(which==1)
    								{
    									AlertDialog.Builder di=new AlertDialog.Builder(SawatariIDActivity.this);
    									di.setTitle("确认信息").setIcon(android.R.drawable.ic_dialog_info).setMessage("将要给+8613625567017发送信息：“何韬手机已丢失，请速联系何韬。”，确定？").setPositiveButton("确定", 
    											new DialogInterface.OnClickListener() {
    												
    												public void onClick(DialogInterface dialog, int which) {
    													// TODO Auto-generated method stub
    													SmsManager sms=SmsManager.getDefault();
    											        sms.sendTextMessage("13625567017", null, "何韬手机已丢失，请速联系何韬。", null, null);
    											        Toast.makeText(SawatariIDActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
    												}
    											}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
    												
    												public void onClick(DialogInterface dialog, int which) {
    													// TODO Auto-generated method stub
    													dialog.cancel();
    												}
    											}).create().show();
    								}
    								else if(which==2)
    								{
    									new AlertDialog.Builder(SawatariIDActivity.this)
    					        		.setTitle("选择地图")
    					        		.setSingleChoiceItems(new String[]{"电子地图","地图图片"},0,
    					        				new DialogInterface.OnClickListener() {						
    												public void onClick(DialogInterface dialog, int which) {
    													if(which==0)
    													{
    														Intent intent = new Intent(SawatariIDActivity.this,map.class);
    														SawatariIDActivity.this.startActivity(intent);
    														SawatariIDActivity.this.finish();
    													}
    													else if(which==1)
    													{
    														ImageView img=new ImageView(SawatariIDActivity.this);
    														img.setImageResource(R.drawable.ht_map);
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("何韬家在望江县城的方位")
    														.setView(img)
    														.setPositiveButton("确定", null)
    														.show();
    													}
    												}
    										})
    					        		.setNegativeButton("取消", null)
    					        		.show();				        				
    								}
    							}
    						}
            		)
            		.setNegativeButton("取消", null)
            		.show();
        		}
        	}     	
        });
        Button2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		if(lan_code == 1){
            		new AlertDialog.Builder(SawatariIDActivity.this)
            		.setTitle("ガジェット")
            		.setSingleChoiceItems(new String[]{"ロケータ","レコーダー","ストップウオッチ","コンパス","画面検出","懐中電灯","電卓","携帯情報","開発ログ（中国語）"},0,
            			new DialogInterface.OnClickListener() {						
    						public void onClick(DialogInterface dialog, int which) {
    							// TODO Auto-generated method stub
    							if(which==0)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,gps.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==1)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,recorder.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==2)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,stopwatch.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==3)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,compass.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==4)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,testt.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==5)
    							{
    								if (!isopent) 
    								{
    									Toast.makeText(getApplicationContext(), "懐中電灯オン", 0)
    									.show();
    									camera = Camera.open();
    									Parameters params = camera.getParameters();
    									params.setFlashMode(Parameters.FLASH_MODE_TORCH);
    									camera.setParameters(params);
    									camera.startPreview();
    									isopent = true;
    								}
    								else
    								{
    									Toast.makeText(getApplicationContext(), "懐中電灯オフ",
    									Toast.LENGTH_SHORT).show();
    									camera.stopPreview();
    									camera.release();
    									isopent = false;
    								}
    							}
    							else if(which==6)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,dentaku.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==7)
    							{
    								TelephonyManager tm = (TelephonyManager) SawatariIDActivity.this.getSystemService(TELEPHONY_SERVICE);
    								final String imei = tm.getDeviceId();
    								final String num = tm.getLine1Number();
    								final String sv = tm.getDeviceSoftwareVersion();
    								final String simn = tm.getSimSerialNumber();
    								final String sid = tm.getSubscriberId();
    								new AlertDialog.Builder(SawatariIDActivity.this)
    								.setTitle("携帯情報")
    								.setSingleChoiceItems(new String[]{"電話番号","IMEI","OSバージョン情報","SIM番号","ユーザー番号"},0,
    					        				new DialogInterface.OnClickListener() {						
    												public void onClick(DialogInterface dialog, int which) {
    													// TODO Auto-generated method stub
    													if(which==0)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("電話番号：")
    														.setMessage(num)
    														.setPositiveButton("決定", null)
    														.show();
    													}
    													else if(which==1)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("IMEI：")
    														.setMessage(imei)
    														.setPositiveButton("決定", null)
    														.show();
    													}
    													else if(which==2)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("OSバージョン情報：")
    														.setMessage(sv)
    														.setPositiveButton("決定", null)
    														.show();
    													}
    													else if(which==3)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("SIM番号：")
    														.setMessage(simn)
    														.setPositiveButton("決定", null)
    														.show();
    													}
    													else if(which==4)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("ユーザー番号：")
    														.setMessage(sid)
    														.setPositiveButton("決定", null)
    														.show();
    													}
    												}
    								})	
    								.setPositiveButton("戻る", null)
    								.show();
    							}
    							else if(which==8)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,about.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    						}
            			}
            		)
            		.setNegativeButton("戻る", null)
            		.show();
        		}
        		else{
            		new AlertDialog.Builder(SawatariIDActivity.this)
            		.setTitle("实用工具")
            		.setSingleChoiceItems(new String[]{"实时定位","录音设备","计时工具","罗盘程序","屏幕测试","启用照明","简易计算","本机信息","开发日志"},0,
            			new DialogInterface.OnClickListener() {						
    						public void onClick(DialogInterface dialog, int which) {
    							// TODO Auto-generated method stub
    							if(which==0)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,gps.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==1)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,recorder.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==2)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,stopwatch.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==3)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,compass.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==4)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,testt.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==5)
    							{
    								if (!isopent) 
    								{
    									Toast.makeText(getApplicationContext(), "手电筒已打开", 0)
    									.show();
    									camera = Camera.open();
    									Parameters params = camera.getParameters();
    									params.setFlashMode(Parameters.FLASH_MODE_TORCH);
    									camera.setParameters(params);
    									camera.startPreview();
    									isopent = true;
    								}
    								else
    								{
    									Toast.makeText(getApplicationContext(), "手电筒已关闭",
    									Toast.LENGTH_SHORT).show();
    									camera.stopPreview();
    									camera.release();
    									isopent = false;
    								}
    							}
    							else if(which==6)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,dentaku.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    							else if(which==7)
    							{
    								TelephonyManager tm = (TelephonyManager) SawatariIDActivity.this.getSystemService(TELEPHONY_SERVICE);
    								final String imei = tm.getDeviceId();
    								final String num = tm.getLine1Number();
    								final String sv = tm.getDeviceSoftwareVersion();
    								final String simn = tm.getSimSerialNumber();
    								final String sid = tm.getSubscriberId();
    								new AlertDialog.Builder(SawatariIDActivity.this)
    								.setTitle("本机信息")
    								.setSingleChoiceItems(new String[]{"本机号码","IMEI码","设备软件版本号","SIM卡编号","用户识别码"},0,
    					        				new DialogInterface.OnClickListener() {						
    												public void onClick(DialogInterface dialog, int which) {
    													// TODO Auto-generated method stub
    													if(which==0)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("本机号码：")
    														.setMessage(num)
    														.setPositiveButton("确定", null)
    														.show();
    													}
    													else if(which==1)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("IMEI码：")
    														.setMessage(imei)
    														.setPositiveButton("确定", null)
    														.show();
    													}
    													else if(which==2)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("设备软件版本号：")
    														.setMessage(sv)
    														.setPositiveButton("确定", null)
    														.show();
    													}
    													else if(which==3)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("SIM卡编号：")
    														.setMessage(simn)
    														.setPositiveButton("确定", null)
    														.show();
    													}
    													else if(which==4)
    													{
    														new AlertDialog.Builder(SawatariIDActivity.this)
    														.setTitle("用户识别码：")
    														.setMessage(sid)
    														.setPositiveButton("确定", null)
    														.show();
    													}
    												}
    								})	
    								.setPositiveButton("取消", null)
    								.show();
    							}
    							else if(which==8)
    							{
    								Intent intent = new Intent(SawatariIDActivity.this,about.class);
    								SawatariIDActivity.this.startActivity(intent);
    								SawatariIDActivity.this.finish();
    							}
    						}
            			}
            		)
            		.setNegativeButton("取消", null)
            		.show();
        		}
        	}
        });
        Time localTime = new Time("Asia/Hong_Kong");  
        localTime.setToNow(); 
        int h = localTime.hour;
        if(h>=12&&h<18){
        	Button1.setTextColor(Color.BLACK);
            Button2.setTextColor(Color.BLACK);
            Text.setTextColor(Color.BLACK);
            Text2.setTextColor(Color.BLACK);
            this.getWindow().setBackgroundDrawableResource(R.drawable.background_a);
        }
        else if(h>=18||h<6){
        	Button1.setTextColor(Color.WHITE);
            Button2.setTextColor(Color.WHITE);
            Text.setTextColor(Color.WHITE);
            Text2.setTextColor(Color.WHITE);
            this.getWindow().setBackgroundDrawableResource(R.drawable.background_n);
        }
        else{
        	Button1.setTextColor(Color.BLACK);
            Button2.setTextColor(Color.BLACK);
            Text.setTextColor(Color.BLACK);
            Text2.setTextColor(Color.BLACK);
        	this.getWindow().setBackgroundDrawableResource(R.drawable.background_m);
        }
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(lan_code == 1){
        	CharSequence name="沢渡のスマートフォン";
        	CharSequence name2="Sawatari Hiroji's Xperia Arc S";
        	Notification notification = new Notification(R.drawable.headicon, name, System.currentTimeMillis());
        	Intent intent = new Intent(this, SawatariIDActivity.class);
            notification.flags = Notification.FLAG_ONGOING_EVENT; 
            PendingIntent contextIntent = PendingIntent.getActivity(this, 0, intent, 0);
            notification.setLatestEventInfo(getApplicationContext(), name, name2, contextIntent);
            notificationManager.notify(R.string.app_name, notification);
        }
        else{
        	CharSequence name="本手机归何韬所有";
        	CharSequence name2="Sawatari Hiroji's Xperia Arc S";
        	Notification notification = new Notification(R.drawable.headicon, name, System.currentTimeMillis());
        	Intent intent = new Intent(this, SawatariIDActivity.class);
            notification.flags = Notification.FLAG_ONGOING_EVENT; 
            PendingIntent contextIntent = PendingIntent.getActivity(this, 0, intent, 0);
            notification.setLatestEventInfo(getApplicationContext(), name, name2, contextIntent);
            notificationManager.notify(R.string.app_name, notification);
        }
	}
}