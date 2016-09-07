/*
 * Sawatari ID for Android
 * Copyright 2004-2012 Sawatari(C.R.X) Corp. All rights reserved.
 */

package hg.crx.sawaid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @since 1.0
 * @version 3.2.1
 * @author He Tao of Sawatari Corp.
 */

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {		// boot
			Intent intent2 = new Intent(context, SawatariIDActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
		}
	}
}