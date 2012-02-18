package cz.adevcamp.lsd.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TickReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context ctx, final Intent intent) {
		Log.d(TickService.LOG_TAG, "TickReceiver.onReceive() called");
		Intent eventService = new Intent(ctx, TickService.class);
		ctx.startService(eventService);
	}

}