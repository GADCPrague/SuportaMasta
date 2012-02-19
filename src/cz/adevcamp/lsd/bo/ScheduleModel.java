package cz.adevcamp.lsd.bo;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

import cz.adevcamp.lsd.Configuration;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Schedule model.
 * 
 * @author peter
 */
public class ScheduleModel {

	private static final String DATABASE_NAME = "SaportaMasta.db";
	private static final int DATABASE_VERSION = 5;

	public static final String SCHEDULE_TABLE_NAME = "schedule";
	public static final String DATE_COLUMN_SCHEDULE_TABLE_NAME = "date";
	public static final String INTERVAL_COLUMN_SCHEDULE_TABLE_NAME = "interval";
	public static final String NAME_COLUMN_SCHEDULE_TABLE_NAME = "name";

	private Context context;
	public SQLiteDatabase db;

	public ScheduleModel(Context context) {
		this.context = context;
	}

	public void openDatabase() {
		Log.d(Configuration.LogTags.SCHEDULE_ITEM_TAG, "Opening db helper");

		OpenHelper openHelper = new OpenHelper(this.context);
		db = openHelper.getWritableDatabase();
		db.setLocale(new Locale("cs", "CZ"));
	}

	public void closeDatabase() {
		db.close();
	}

	// Insert whole schedule.
	public void insertSchedule(ArrayList<ScheduleItem> items) {
		Log.d(Configuration.LogTags.SCHEDULE_ITEM_TAG, "Inserting schedule");

		if (items != null) {
			for (ScheduleItem item : items) {
				db.execSQL("INSERT OR REPLACE INTO " + SCHEDULE_TABLE_NAME + " VALUES (" + item.getDate().getTime() + ", '"
						+ item.getInterval().toInt() + "', '" + item.getName() + "')");
			}
		}
	}

	// Get schedule.
	public ArrayList<ScheduleItem> getSchedule() {
		Log.d(Configuration.LogTags.SCHEDULE_ITEM_TAG, "Getting schedule");

		Cursor cur = db.rawQuery("SELECT date, interval, name" + " FROM " + SCHEDULE_TABLE_NAME + " ORDER BY date, interval", null);
		ArrayList<ScheduleItem> items = new ArrayList<ScheduleItem>();

		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			items.add(new ScheduleItem(cur.getLong(0), cur.getInt(1), cur.getString(2)));
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
			Log.d(Configuration.LogTags.SCHEDULE_ITEM_TAG, "Creating tables");
			db.execSQL("CREATE TABLE " + SCHEDULE_TABLE_NAME + " (" + "date LONG NOL NULL, " + // date as long
					"interval INTEGER NOT NULL, " + // 0 - morning, 1 - evening
					"name TEXT NOT NULL," + "PRIMARY KEY (date, interval)" + ");");
		}

		// What to do when DATABASE_VERSION changes.
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(Configuration.LogTags.SCHEDULE_ITEM_TAG, "Dropping tables");

			db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
			this.onCreate(db);
		}
	}

	public ArrayList<ScheduleItem> getScheduleFromToday() {
		Log.d(Configuration.LogTags.SCHEDULE_ITEM_TAG, "Getting schedule from");

		Date fromToday = new Date();
		fromToday.setHours(0);
		fromToday.setMinutes(0);
		fromToday.setSeconds(0);

		Cursor cur = db.rawQuery("SELECT date, interval, name" + " FROM " + SCHEDULE_TABLE_NAME + " WHERE date > " + fromToday.getTime()
				+ " ORDER BY date, interval", null);
		ArrayList<ScheduleItem> items = new ArrayList<ScheduleItem>();

		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			items.add(new ScheduleItem(cur.getLong(0), cur.getInt(1), cur.getString(2)));
			cur.moveToNext();
		}
		cur.close();

		return items;
	}
}
