/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;

import cn.creable.gridgis.controls.MapControl;
import cn.creable.gridgis.controls.MapView;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class MyMapView extends MapView {
	public MyMapView(Context arg0, AttributeSet arg1) { 
		super(arg0, arg1);
		}
		@Override 
		//µÿÕºœ‘ æ
		protected void onSizeChanged(int arg0, int arg1, int arg2, int arg3) { 
			super.onSizeChanged(arg0, arg1, arg2, arg3);
			MapControl mapControl=getMapControl();
			mapControl.showScaleBar(8, getResources().getDisplayMetrics().xdpi/2.54f, 10, mapControl.getHeight()-10, Color.BLACK,Color.RED,3,20);
			if (mapControl.getMap()==null)
			{
				String path=Environment.getExternalStorageDirectory().getPath(); 
				mapControl.loadMap(path+"/SawaGIS/map.ini", (byte)0);
				mapControl.setPanTool(); 
		} 
	}
}