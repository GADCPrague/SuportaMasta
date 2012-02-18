package cz.adevcamp.lsd.scheduler;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Servisa, ktera pravidelne tika a vykonova kontrolu dat
 * @author kovi
 *
 */
public class TickService extends Service {
	/**
	 * Jmeno logovaciho tagu pro TickServicus
	 */
	public static final String LOG_TAG = "SM-TickService";

	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		Log.d(LOG_TAG, "event received in service: " + new Date().toString());
		
		
		//TODO: sem pridat tahani
		
		//NOTE: notifikace zmen
		notifyChange();
		
		return Service.START_NOT_STICKY;
	}
	
	public static final String NOTIFICATION_INTENT_STRING= "cz.adevcamp.lsd.scheduler.TickReceiver.NotifyChange";
	/**
	 * Notifikace widgetu a aktivity
	 */
	private void notifyChange(){
		
		Intent notifyChanged = new Intent(NOTIFICATION_INTENT_STRING);
		sendBroadcast(notifyChanged);
	}


}
