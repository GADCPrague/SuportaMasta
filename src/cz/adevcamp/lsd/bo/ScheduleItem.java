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
	private String date;	
	// rano nebo vecer supportu
	private Integer interval;
	
	private SimpleDateFormat sdf;
	
	public ScheduleItem() {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	}
	
	public ScheduleItem(String date, Integer interval, String name) {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		
		this.date = date;
		this.interval = interval;
		this.name = name;
	}
	
	public ScheduleItem(Date date, ScheduleInterval interval, String name) {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		
		this.date = this.sdf.format(date);
		this.interval = interval.toInt();
		this.name = name;
	}
	
	public String getName() {
		
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		try {
			return this.sdf.parse(this.date);
			
		} catch (ParseException e) {
			return null;
		}
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public ScheduleInterval getInterval() {
		return ScheduleInterval.getFromInt(this.interval);
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	
}
