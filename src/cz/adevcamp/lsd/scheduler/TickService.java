package cz.adevcamp.lsd.scheduler;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TickService extends Service {
	public static final String LOG_TAG = "SmTickService";

	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		Log.d(LOG_TAG, "event received in service: " + new Date().toString());
		
		
		//TODO: sem pridat tahani
		
		
		return Service.START_NOT_STICKY;
	}


}
