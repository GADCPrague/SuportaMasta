package cz.adevcamp.lsd.scheduler;

import java.util.Calendar;

import cz.adevcamp.lsd.Configuration;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Postara se o nastartovani procesu tickani pro stahovani dat.
 * 
 * @author kovi
 */
public class SetupReceiver extends BroadcastReceiver {
	
	/**
	 * Tikani po x sekundach
	 */
	private static final int TICK_INTERVAL_IN_SEC = 5;
	
	private static final int EXEC_INTERVAL = 20 * 1000;

	@Override
	public void onReceive(final Context ctx, final Intent intent) {
		Log.d(Configuration.LogTags.TICK_SERVICE_TAG, "SetupReceiver.onReceive() called");
		AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(ctx, TickReceiver.class); // explicit intent
		PendingIntent intentExecuted = PendingIntent.getBroadcast(ctx, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, TICK_INTERVAL_IN_SEC);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), EXEC_INTERVAL, intentExecuted);
	}
}
