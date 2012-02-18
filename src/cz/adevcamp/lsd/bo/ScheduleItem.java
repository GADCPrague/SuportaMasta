package cz.adevcamp.lsd.bo;

import java.util.Date;

/**
 * Datova reprezentacec jedneho supportu
 * 
 * @author kovi
 *
 */
public class ScheduleItem {

	// jmeno supportujiciho
	private String name;
	// datum supportu
	private Date date;
	// rano nebo vecer supportu
	private ScheduleInterval interval;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ScheduleInterval getInterval() {
		return interval;
	}

	public void setInterval(ScheduleInterval interval) {
		this.interval = interval;
	}
	
}
