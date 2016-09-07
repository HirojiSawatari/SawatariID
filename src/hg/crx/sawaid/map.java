/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import cn.creable.gridgis.controls.App;
import cn.creable.gridgis.controls.IInfoToolListener;
import cn.creable.gridgis.controls.InfoTool;
import cn.creable.gridgis.controls.MapControl;
import cn.creable.gridgis.geodatabase.FeatureInfo;
import cn.creable.gridgis.geodatabase.IFeature;
import cn.creable.gridgis.geometry.IPoint;
import cn.creable.gridgis.geometry.Point;
import cn.creable.gridgis.mapLayer.IFeatureLayer;
import cn.creable.gridgis.mapLayer.Layer;
import cn.creable.gridgis.controls.MapView;
import cn.creable.gridgis.geometry.IEnvelope;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class map extends Activity {
	MapView mapView;
	ProgressDialog dlg;
	Activity act;
	private long exitTime = 0;
	//界面样式
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		mapView=(MapView)findViewById(R.id.myMapView1);
	}
	//菜单键样式
	public boolean onCreateOptionsMenu(Menu menu) {
		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
	    int lan_code = lan_info.getInt("lan_code",0);
		if(lan_code == 1){
			menu.add(0, 1, 0, "ズームイン").setIcon(android.R.drawable.ic_menu_zoom);
			menu.add(0, 2, 0, "ズームアウト").setIcon(android.R.drawable.ic_menu_search);
			menu.add(0, 3, 0, "地図平移").setIcon(android.R.drawable.ic_menu_upload);
			menu.add(0, 4, 0, "プロパティ").setIcon(android.R.drawable.ic_menu_agenda);
			menu.add(0, 5, 0, "サーチ").setIcon(android.R.drawable.ic_menu_myplaces);
			menu.add(0, 6, 0, "戻る").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	}
		else{
			menu.add(0, 1, 0, "放大地图").setIcon(android.R.drawable.ic_menu_zoom);
			menu.add(0, 2, 0, "缩小地图").setIcon(android.R.drawable.ic_menu_search);
			menu.add(0, 3, 0, "平移地图").setIcon(android.R.drawable.ic_menu_upload);
			menu.add(0, 4, 0, "显示属性").setIcon(android.R.drawable.ic_menu_agenda);
			menu.add(0, 5, 0, "属性查询").setIcon(android.R.drawable.ic_menu_myplaces);
			menu.add(0, 6, 0, "返回主界面").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		}
		return super.onCreateOptionsMenu(menu);
	}
	//菜单键功能
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
		case 6:
			Intent intent = new Intent(map.this,SawatariIDActivity.class);
			map.this.startActivity(intent);
			map.this.finish();
			break;
		case 1:	
			mapView.getMapControl().setZoomInTool();
			break;
		case 2:	
			mapView.getMapControl().setZoomOutTool();
			break;
		case 3:
			mapView.getMapControl().setPanTool();
			break;
		case 4:
			mapView.getMapControl().setCurrentTool(new InfoTool(mapView.getMapControl(),new IInfoToolListener(){
				public void notify(MapControl mapControl, IFeatureLayer flayer,
						IFeature ft, String[] fields, String[] values){
					mapControl.flashFeatures(flayer, ft.getOid());
					StringBuilder sb=new StringBuilder();
					for(int i=1;i<fields.length;++i)
					{
						sb.append(fields[i]);
						sb.append(":");
						sb.append(values[i]);
						sb.append("\n");
					}
					sb.deleteCharAt(sb.length()-1);
					Toast.makeText(App.getInstance(), sb.toString(), Toast.LENGTH_SHORT).show();
					sb=null;
				}
			}));
			break;
		case 5: 
			final EditText et=new EditText(map.this);
			SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
		    int lan_code = lan_info.getInt("lan_code",0);
		    if(lan_code == 1){
		    	new AlertDialog.Builder(map.this)
		    	.setTitle("レイヤーを選択")
		    	.setSingleChoiceItems(new String[]{"道路","ランドマーク"},0,
    				new DialogInterface.OnClickListener() {						
						public void onClick(DialogInterface dialog, int which) {
							if(which==0)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("道路を入力")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("決定", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("道路", null, "NAME like '%"+ str +"%'", null, null ,null);
												if(fis!=null)
												{
													float current_scale=mapView.getMapControl().getDisplay().getDisplayTransformation().getZoom();
													Layer searchlayer=(Layer)mapView.getMapControl().getMap().getLayer(1);
													float max_scale=searchlayer.getMaximumScale();
													if(current_scale>max_scale)
													{
														mapView.getMapControl().getDisplay().getDisplayTransformation().setZoom(max_scale/2);
													}
													IEnvelope extent=mapView.getMapControl().getExtent();
													IPoint pt=new Point((fis[0].xmin+fis[0].xmax)/2, (fis[0].ymin+fis[0].ymax)/2);
													extent.centerAt(pt);
													pt=null;
													mapView.getMapControl().refresh(extent);
													mapView.getMapControl().flashFeature(fis[0].layerID,fis[0].objectID);
													}
											}
								})
							  	.setNegativeButton("戻る", null)
							  	.show();	
							}
							else if(which==1)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("ランドマークを入力")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("決定", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("地标", null, "NAME like '%"+ str +"%'", null, null ,null);
												if(fis!=null)
												{
													float current_scale=mapView.getMapControl().getDisplay().getDisplayTransformation().getZoom();
													Layer searchlayer=(Layer)mapView.getMapControl().getMap().getLayer(2);
													float max_scale=searchlayer.getMaximumScale();
													if(current_scale>max_scale)
													{
														mapView.getMapControl().getDisplay().getDisplayTransformation().setZoom(max_scale/2);
													}
													IEnvelope extent=mapView.getMapControl().getExtent();
													IPoint pt=new Point((fis[0].xmin+fis[0].xmax)/2, (fis[0].ymin+fis[0].ymax)/2);
													extent.centerAt(pt);
													pt=null;
													mapView.getMapControl().refresh(extent);
													mapView.getMapControl().flashFeature(fis[0].layerID,fis[0].objectID);
													}
											}
								})
								.setNegativeButton("戻る", null)
							  	.show();	
							}
						}
    			})
    			.setNegativeButton("戻る", null)
        		.show();
				break;
		    }
		    else{
		    	new AlertDialog.Builder(map.this)
		    	.setTitle("选择查询图层")
		    	.setSingleChoiceItems(new String[]{"道路","地标"},0,
    				new DialogInterface.OnClickListener() {						
						public void onClick(DialogInterface dialog, int which) {
							if(which==0)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("输入道路名")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("确定", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("道路", null, "NAME like '%"+ str +"%'", null, null ,null);
												if(fis!=null)
												{
													float current_scale=mapView.getMapControl().getDisplay().getDisplayTransformation().getZoom();
													Layer searchlayer=(Layer)mapView.getMapControl().getMap().getLayer(1);
													float max_scale=searchlayer.getMaximumScale();
													if(current_scale>max_scale)
													{
														mapView.getMapControl().getDisplay().getDisplayTransformation().setZoom(max_scale/2);
													}
													IEnvelope extent=mapView.getMapControl().getExtent();
													IPoint pt=new Point((fis[0].xmin+fis[0].xmax)/2, (fis[0].ymin+fis[0].ymax)/2);
													extent.centerAt(pt);
													pt=null;
													mapView.getMapControl().refresh(extent);
													mapView.getMapControl().flashFeature(fis[0].layerID,fis[0].objectID);
													}
											}
								})
							  	.setNegativeButton("取消", null)
							  	.show();	
							}
							else if(which==1)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("输入地标名")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("确定", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("地标", null, "NAME like '%"+ str +"%'", null, null ,null);
												if(fis!=null)
												{
													float current_scale=mapView.getMapControl().getDisplay().getDisplayTransformation().getZoom();
													Layer searchlayer=(Layer)mapView.getMapControl().getMap().getLayer(2);
													float max_scale=searchlayer.getMaximumScale();
													if(current_scale>max_scale)
													{
														mapView.getMapControl().getDisplay().getDisplayTransformation().setZoom(max_scale/2);
													}
													IEnvelope extent=mapView.getMapControl().getExtent();
													IPoint pt=new Point((fis[0].xmin+fis[0].xmax)/2, (fis[0].ymin+fis[0].ymax)/2);
													extent.centerAt(pt);
													pt=null;
													mapView.getMapControl().refresh(extent);
													mapView.getMapControl().flashFeature(fis[0].layerID,fis[0].objectID);
													}
											}
								})
								.setNegativeButton("取消", null)
							  	.show();	
							}
						}
    			})
    			.setNegativeButton("取消", null)
        		.show();
				break;
		    }
		}
		return super.onOptionsItemSelected(item);
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
}