package cz.adevcamp.lsd.bo;

import java.util.Date;

import android.content.res.Resources;
/**
 * Informace, jestli se jedna o ranni, nebo vecerni support.
 *
 * @author kovi
 */
public enum ScheduleInterval {
	Morning(createDate(22), 0),
	Evening(createDate(10), 1);

	private static Date createDate(int hour){
		Date d = new Date();
		d.setHours(hour);
		d.setMinutes(0);
		d.setSeconds(0);

		return d;
	}

	public static ScheduleInterval getFromInt(Integer interval){

		if (interval == 0){
			return Morning;
		}

		//TODO: vyhazovat vyjimku - kdyz tak osetrit

		return Evening;
	}
	
	public Integer toInt() {
		return this.index;
	}

	/**
	 * Cas, od ktereho je dany interval platny pro zobrazeni jako prvni v poradi
	 */
	private Date dateFrom;
	private Integer index;

	ScheduleInterval (Date from, Integer index){
		setDateFrom(from);
		this.index = index;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	private void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	public String toText(Resources res){
		if (index == 0){
			return res.getString(cz.adevcamp.lsd.R.string.interval_morning);
		}

		//TODO: vyhazovat vyjimku - kdyz tak osetrit
		
		return res.getString(cz.adevcamp.lsd.R.string.interval_evening);
	}
}
