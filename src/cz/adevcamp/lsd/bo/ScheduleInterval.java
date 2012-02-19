package cz.adevcamp.lsd.bo;

import java.util.Date;

import cz.adevcamp.lsd.Configuration;

import android.content.res.Resources;
import android.util.Log;

/**
 * Informace, jestli se jedna o ranni, nebo vecerni support.
 * 
 * @author kovi
 */
public enum ScheduleInterval {
	Morning(createDate(22)), Evening(createDate(10));

	private static Date createDate(int hour) {
		Date d = new Date();
		d.setHours(hour);
		d.setMinutes(0);
		d.setSeconds(0);

		return d;
	}

	public static ScheduleInterval getFromInt(Integer interval) {

		if (interval == 0) {
			return Morning;
		}

		// TODO: vyhazovat vyjimku - kdyz tak osetrit

		return Evening;
	}

	public static ScheduleInterval getFromString(String interval) {
		if ("morning".compareToIgnoreCase(interval) == 0) {
			return Morning;
		}

		// TODO: vyhazovat vyjimku - kdyz tak osetrit

		return Evening;
	}

	public Integer toInt() {
		switch (this) {
		case Morning:
			return 0;
		case Evening:
			return 1;
		}

		Log.e(Configuration.LogTags.SCHEDULE_ITEM_TAG, "toInt(); Missing ScheduleInterval: " + this);
		return null;
	}

	/**
	 * Cas, od ktereho je dany interval platny pro zobrazeni jako prvni v poradi
	 */
	private Date dateFrom;

	ScheduleInterval(Date from) {
		setDateFrom(from);
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	private void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String toText(Resources res) {
		switch (this) {
		case Morning:
			return res.getString(cz.adevcamp.lsd.R.string.interval_morning);
		case Evening:
			return res.getString(cz.adevcamp.lsd.R.string.interval_evening);
		}

		Log.e(Configuration.LogTags.SCHEDULE_ITEM_TAG, "toText(); Missing ScheduleInterval: " + this);
		return null;
	}
}
