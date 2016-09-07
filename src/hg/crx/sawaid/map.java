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
	//������ʽ
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		mapView=(MapView)findViewById(R.id.myMapView1);
	}
	//�˵�����ʽ
	public boolean onCreateOptionsMenu(Menu menu) {
		SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
	    int lan_code = lan_info.getInt("lan_code",0);
		if(lan_code == 1){
			menu.add(0, 1, 0, "���`�।��").setIcon(android.R.drawable.ic_menu_zoom);
			menu.add(0, 2, 0, "���`�ॢ����").setIcon(android.R.drawable.ic_menu_search);
			menu.add(0, 3, 0, "�؇�ƽ��").setIcon(android.R.drawable.ic_menu_upload);
			menu.add(0, 4, 0, "�ץ�ѥƥ�").setIcon(android.R.drawable.ic_menu_agenda);
			menu.add(0, 5, 0, "���`��").setIcon(android.R.drawable.ic_menu_myplaces);
			menu.add(0, 6, 0, "����").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	}
		else{
			menu.add(0, 1, 0, "�Ŵ��ͼ").setIcon(android.R.drawable.ic_menu_zoom);
			menu.add(0, 2, 0, "��С��ͼ").setIcon(android.R.drawable.ic_menu_search);
			menu.add(0, 3, 0, "ƽ�Ƶ�ͼ").setIcon(android.R.drawable.ic_menu_upload);
			menu.add(0, 4, 0, "��ʾ����").setIcon(android.R.drawable.ic_menu_agenda);
			menu.add(0, 5, 0, "���Բ�ѯ").setIcon(android.R.drawable.ic_menu_myplaces);
			menu.add(0, 6, 0, "����������").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		}
		return super.onCreateOptionsMenu(menu);
	}
	//�˵�������
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
		    	.setTitle("�쥤��`���x�k")
		    	.setSingleChoiceItems(new String[]{"��·","���ɥީ`��"},0,
    				new DialogInterface.OnClickListener() {						
						public void onClick(DialogInterface dialog, int which) {
							if(which==0)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("��·������")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("�Q��", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("��·", null, "NAME like '%"+ str +"%'", null, null ,null);
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
							  	.setNegativeButton("����", null)
							  	.show();	
							}
							else if(which==1)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("���ɥީ`��������")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("�Q��", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("�ر�", null, "NAME like '%"+ str +"%'", null, null ,null);
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
								.setNegativeButton("����", null)
							  	.show();	
							}
						}
    			})
    			.setNegativeButton("����", null)
        		.show();
				break;
		    }
		    else{
		    	new AlertDialog.Builder(map.this)
		    	.setTitle("ѡ���ѯͼ��")
		    	.setSingleChoiceItems(new String[]{"��·","�ر�"},0,
    				new DialogInterface.OnClickListener() {						
						public void onClick(DialogInterface dialog, int which) {
							if(which==0)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("�����·��")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("ȷ��", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("��·", null, "NAME like '%"+ str +"%'", null, null ,null);
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
							  	.setNegativeButton("ȡ��", null)
							  	.show();	
							}
							else if(which==1)
							{
								dialog.dismiss();
								new AlertDialog.Builder(map.this)
								.setTitle("����ر���")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(et)
								.setPositiveButton("ȷ��", 
										new DialogInterface.OnClickListener() {										
											public void onClick(DialogInterface dialog, int which) {
												String str=et.getText().toString();
												FeatureInfo[] fis=mapView.getMapControl().searchFeature("�ر�", null, "NAME like '%"+ str +"%'", null, null ,null);
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
								.setNegativeButton("ȡ��", null)
							  	.show();	
							}
						}
    			})
    			.setNegativeButton("ȡ��", null)
        		.show();
				break;
		    }
		}
		return super.onOptionsItemSelected(item);
	}
	//˫�����ؼ��˳�
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        SharedPreferences lan_info = getSharedPreferences("lan_info", 0);
        int lan_code = lan_info.getInt("lan_code",0);
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
            if((System.currentTimeMillis()-exitTime) > 2000){
            	if(lan_code == 1){
            		Toast.makeText(getApplicationContext(), "�⤦һ��Ѻ���ȥץ��������ˤ��ޤ�", Toast.LENGTH_SHORT).show(); 
            	}
            	else{
            		Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();      
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