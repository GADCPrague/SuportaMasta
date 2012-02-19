package cz.adevcamp.lsd.tools;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import cz.adevcamp.lsd.bo.ScheduleItem;
import cz.adevcamp.lsd.bo.ScheduleInterval;

/**
 * Schedule model.
 *
 * @author peter
 */
public class ScheduleModel {
	public static final String LOG_TAG = "SM-Model";

	private static final String DATABASE_NAME = "SaportaMasta.db";
	private static final int DATABASE_VERSION = 4;

	public static final String SCHEDULE_TABLE_NAME = "schedule";
	private static final String SCHEDULE_TABLE_CREATE = "CREATE TABLE " + SCHEDULE_TABLE_NAME + " (" +
				"date DATE NOL NULL, " +
				"interval INTEGER NOT NULL, " + // 0 - morning, 1 - evening
				"name TEXT NOT NULL," +
				"PRIMARY KEY (date, interval)" +
				");";

	private Context context;
	public SQLiteDatabase db;

	public ScheduleModel(Context context) {
		this.context = context;

		Log.d(LOG_TAG, "Opening db helper");

		OpenHelper openHelper = new OpenHelper(this.context);
		db = openHelper.getWritableDatabase();
		db.setLocale(new Locale("cs", "CZ"));
	}

	// Insert whole schedule.
	public void insertSchedule(ArrayList<ScheduleItem> items) {
		Log.d(LOG_TAG, "Inserting schedule");

		for (ScheduleItem item: items) {
			db.execSQL("INSERT OR REPLACE INTO " + SCHEDULE_TABLE_NAME + " VALUES ('" +
					item.getDate() + "', '" +
					item.getInterval().toInt() + "', '" +
					item.getName() + "')");
		}
	}

	// Get schedule.
	public ArrayList<ScheduleItem> getSchedule() {
		Log.d(LOG_TAG, "Getting schedule");

		Cursor cur = db.rawQuery("SELECT date, interval, name" +
				"FROM " + SCHEDULE_TABLE_NAME +
				"ORDER BY date, interval", null);
		ArrayList<ScheduleItem> items = new ArrayList<ScheduleItem>();

		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			items.add(new ScheduleItem(new Date(cur.getString(0)), ScheduleInterval.getFromInt(cur.getInt(1)), cur.getString(2)));
			cur.moveToNext();
		}
		cur.close();

		return items;
	}

	/**
	 * Helper class for managing tables creation.
	 */
	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		// What to do on first run.
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(LOG_TAG, "Creating tables");
			db.execSQL(SCHEDULE_TABLE_CREATE);
		}

		// What to do when DATABASE_VERSION changes.
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(LOG_TAG, "Dropping tables");

			db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
			this.onCreate(db);
		}
	}
}
