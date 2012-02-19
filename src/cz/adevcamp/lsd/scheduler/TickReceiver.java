package cz.adevcamp.lsd.scheduler;

import cz.adevcamp.lsd.Configuration;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Tikne casovac a poslu ho dal na zpracovani.
 * 
 * @author kovi
 */
public class TickReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context ctx, final Intent intent) {
		Log.d(Configuration.LogTags.TICK_SERVICE_TAG, "TickReceiver.onReceive() called");
		Intent eventService = new Intent(ctx, TickService.class);
		ctx.startService(eventService);
	}
}