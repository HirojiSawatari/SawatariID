/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import java.util.Calendar;     
import java.util.Date;     
import java.util.GregorianCalendar;     
import java.util.Timer;     
import java.util.TimerTask; 

import android.appwidget.AppWidgetManager;     
import android.appwidget.AppWidgetProvider;     
import android.content.ComponentName;  
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;     

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class MyWidgetProvider extends AppWidgetProvider {   
	private SharedPreferences shp;
	private SharedPreferences lan_info;
    /** Called when the activity is first created. */    
    @Override    
    //组件显示内容
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,     
            int[] appWidgetIds) {  
        Timer timer = new Timer();     
        timer.scheduleAtFixedRate(new MyTime(context,appWidgetManager), 1, 60000);     
        super.onUpdate(context, appWidgetManager, appWidgetIds);     
    }   
    //倒计时计算
    private class MyTime extends TimerTask{     
        RemoteViews remoteViews;     
        AppWidgetManager appWidgetManager;     
        ComponentName thisWidget;   
        public MyTime(Context context,AppWidgetManager appWidgetManager){     
            this.appWidgetManager = appWidgetManager;     
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);     
        	String SharePreference_name = "DAY";
        	shp = context.getSharedPreferences(SharePreference_name, Context.MODE_WORLD_READABLE);     
        	lan_info = context.getSharedPreferences("lan_info", 0);
            thisWidget = new ComponentName(context,MyWidgetProvider.class);     
        }     
        public void run() {
        	int lan_code = lan_info.getInt("lan_code",0);
			String name = shp.getString("NAME","");
			int year = shp.getInt("YEAR",0);
			int month = shp.getInt("MONTH",0);
			int day = shp.getInt("DAY",0);
            Date date = new Date();     
            Calendar calendar = new GregorianCalendar(year,month,day);
            Calendar ca = Calendar.getInstance();
            long days = (((calendar.getTimeInMillis()-date.getTime())/1000))/86400;   
            int nowyear = ca.get(Calendar.YEAR);
            int nowmonth = ca.get(Calendar.MONTH);
            int nowday = ca.get(Calendar.DAY_OF_MONTH);
            if(lan_code == 1){
            	if(year==nowyear && month==nowmonth && day==nowday)
            	{
            		remoteViews.setTextViewText(R.id.textView1, "\n    "+ name + "は今日");
            	}
            	else if(days>=0)
            	{
            		days++;
            		remoteViews.setTextViewText(R.id.textView1, "\n    "+ name + "まであと" + days+"日");           
            	}
            	else
            	{
            		days=-days;
            		remoteViews.setTextViewText(R.id.textView1, "\n    "+ name + "以来もう" + days+"日");
            	}
            }
            else{
            	if(year==nowyear && month==nowmonth && day==nowday)
            	{
            		remoteViews.setTextViewText(R.id.textView1, "\n    "+ name + "就是今天");
            	}
            	else if(days>=0)
            	{
            		days++;
            		remoteViews.setTextViewText(R.id.textView1, "\n    距离"+ name + "还有" + days+"天");           
            	}
            	else
            	{
            		days=-days;
            		remoteViews.setTextViewText(R.id.textView1, "\n    距离"+ name + "已过去" + days+"天");
            	}
            }
            appWidgetManager.updateAppWidget(thisWidget, remoteViews);       
        }                
    }          
}    