package cz.adevcamp.lsd.scheduler;

import cz.adevcamp.lsd.tools.Http;
import cz.adevcamp.lsd.tools.ScheduleModel;
import cz.adevcamp.lsd.gson.ScheduleResponse;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;
import com.google.gson.Gson;

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
	public static final String API_URL = "http://adevcamp.pematon.com/SaportaMasta/";

	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		Log.d(LOG_TAG, "event received in service: " + new Date().toString());
		
		// Stahne data na pozadi.
        new AsyncJsonLoader().execute();
		
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

	/**
	 * Asynchronous JSON loader.
	 */
    private class AsyncJsonLoader extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... v) {
			try {
				Log.d(LOG_TAG, "downloading schedules");

				// Download and parse JSON.
				Gson gson = new Gson();
		        String json = Http.downloadText(API_URL + "schedule");
		        ScheduleResponse response = gson.fromJson(json, ScheduleResponse.class);

		        // Put schedule into database.
		        ScheduleModel model = new ScheduleModel(getApplicationContext());
		        model.insertSchedule(response.getItems());

			} catch (Exception e) {
				Log.e(LOG_TAG, "error loading JSON: schedule");
				Http.logError(e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			notifyChange();
		}
    }
}
