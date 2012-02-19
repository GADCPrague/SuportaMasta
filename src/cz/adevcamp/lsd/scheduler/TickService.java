package cz.adevcamp.lsd.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.adevcamp.lsd.Configuration;
import cz.adevcamp.lsd.bo.ScheduleItem;
import cz.adevcamp.lsd.bo.ScheduleModel;
import cz.adevcamp.lsd.gson.ScheduleDeserializer;
import cz.adevcamp.lsd.gson.ScheduleResponse;
import cz.adevcamp.lsd.tools.Http;

/**
 * Servisa, ktera pravidelne tika a vykonova kontrolu dat
 * 
 * @author kovi
 * 
 */
public class TickService extends Service {

	private static final LoaderType CURRENT_LOADER = LoaderType.Text;

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		Log.d(Configuration.LogTags.TICK_SERVICE_TAG, "event received in service: " + new Date().toString());

//		CURRENT_LOADER.getNewAsynsLoader(this).execute();		
		Random r = new Random();
		if (r.nextBoolean()){
			CURRENT_LOADER.getNewAsynsLoader(this).execute();
		} else {
			LoaderType.Json.getNewAsynsLoader(this).execute();	
		}

		return Service.START_NOT_STICKY;
	}

	public static final String NOTIFICATION_INTENT_STRING = "cz.adevcamp.lsd.scheduler.TickReceiver.NotifyChange";

	/**
	 * Notifikace widgetu a aktivity
	 */
	private void notifyChange() {

		Intent notifyChanged = new Intent(NOTIFICATION_INTENT_STRING);
		sendBroadcast(notifyChanged);
	}

	private void updateDb(ArrayList<ScheduleItem> loadeditems) {
		ScheduleModel model = null;
		// Put schedule into database.
		model = new ScheduleModel(getApplicationContext());
		model.openDatabase();
		model.insertSchedule(loadeditems);
		model.closeDatabase();

		notifyChange();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private enum LoaderType {
		Json, Text;

		public AsyncTask<Void, Void, ArrayList<ScheduleItem>> getNewAsynsLoader(TickService s) {
			switch (this) {
			case Json:
				return s.new AsyncJsonLoader();
			case Text:
				return s.new AsyncTextLoader();
			}

			Log.e(Configuration.LogTags.TICK_SERVICE_TAG, "getNewAsynsLoader(); Missing LoaderType: " + this);
			return null;
		}
	}

	/**
	 * Asynchronous JSON loader.
	 */
	private class AsyncJsonLoader extends AsyncTask<Void, Void, ArrayList<ScheduleItem>> {

		@Override
		protected ArrayList<ScheduleItem> doInBackground(Void... v) {
			try {
				Log.d(Configuration.LogTags.TICK_SERVICE_TAG, "Downloading schedules");

				// Download schedule.
				String json = Http.downloadText(Configuration.DataSources.JSON_URL + "schedule");

				// Parse JSON.
				Gson gson = new GsonBuilder().registerTypeAdapter(ScheduleItem.class, new ScheduleDeserializer()).create();
				ScheduleResponse response = gson.fromJson(json, ScheduleResponse.class);

				return response.getItems();

			} catch (Exception e) {
				Log.e(Configuration.LogTags.TICK_SERVICE_TAG, "error loading JSON: schedule");
				Http.logError(e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<ScheduleItem> result) {
			updateDb(result);
		}
	}

	/**
	 * Nacitani z textaku
	 * 
	 * @author kovi
	 * 
	 */
	private class AsyncTextLoader extends AsyncTask<Void, Void, ArrayList<ScheduleItem>> {

		private final Pattern linesPattern = Pattern.compile("\n");
		private final Pattern itemsPattern = Pattern.compile("\\s");

		@Override
		protected ArrayList<ScheduleItem> doInBackground(Void... params) {
			try {
				Log.d(Configuration.LogTags.TICK_SERVICE_TAG, "Downloading schedules");

				// Download schedule.
				String file = Http.downloadText(Configuration.DataSources.TXT_URL);

				ArrayList<ScheduleItem> schedules = new ArrayList<ScheduleItem>();
				String[] items = linesPattern.split(file);
				for (String line : items) {
					String[] item = itemsPattern.split(line);
					if (item.length == 3) {
						schedules.add(new ScheduleItem(item[0], item[1], item[2]));
					}
				}

				return schedules;

			} catch (Exception e) {
				Log.e(Configuration.LogTags.TICK_SERVICE_TAG, "error parsing file");
				Http.logError(e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<ScheduleItem> result) {
			updateDb(result);
		}
	}
}
