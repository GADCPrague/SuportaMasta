package cz.adevcamp.lsd.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Datova reprezentacec jedneho supportu
 * 
 * @author kovi
 * 
 */
public class ScheduleItem {

	// jmeno supportujiciho
	private String name;
	// datum supportu ve formatu YYYY-MM-DD
	private Date date;
	// rano nebo vecer supportu
	private ScheduleInterval interval;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

	public ScheduleItem() {
	}

	private Date stringToDate(String date) {
		try {
			return sdf.parse(date);

		} catch (ParseException e) {
			return null;
		}
	}

	public ScheduleItem(String date, Integer interval, String name) {
		this.date = stringToDate(date);
		this.interval = ScheduleInterval.getFromInt(interval);
		this.name = name;
	}

	public ScheduleItem(String date, String interval, String name) {
		this.date = stringToDate(date);
		this.interval = ScheduleInterval.getFromString(interval);
		this.name = name;
	}

	public ScheduleItem(Date date, ScheduleInterval interval, String name) {
		this.date = date;
		this.interval = interval;
		this.name = name;
	}

	public ScheduleItem(long date, int interval, String name) {
		this.date = new Date(date);
		this.interval = ScheduleInterval.getFromInt(interval);
		this.name = name;
	}

	public String getName() {

		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public void setDate(String date) {
		this.date = stringToDate(date);
	}

	public ScheduleInterval getInterval() {
		return this.interval;
	}

	public void setInterval(ScheduleInterval interval) {
		this.interval = interval;
	}
	public void setInterval(Integer interval) {
		this.interval = ScheduleInterval.getFromInt(interval);
	}

}
