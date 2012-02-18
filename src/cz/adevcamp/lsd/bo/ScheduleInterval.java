package cz.adevcamp.lsd.bo;

import java.util.Date;
/**
 * Informace, jestli se jedna o ranni, nebo vecerni support.
 * 
 * @author kovi
 */
public enum ScheduleInterval {
	Morning(createDate(22)),
	Evening(createDate(10));
	
	private static Date createDate(int hour){
		Date d = new Date();
		d.setHours(hour);
		d.setMinutes(0);
		d.setSeconds(0);
		
		return d;
	}
	
	public static ScheduleInterval getFromString(String intervalName){
		
		if (intervalName.toLowerCase().compareTo("morning") == 0){
			return Morning;
		}
		
		//TODO: vyhazovat vyjimku - kdyz tak osetrit
		
		return Evening;
	}
	
	/**
	 * Cas, od ktereho je dany interval platny pro zobrazeni jako prvni v poradi
	 */
	private Date dateFrom;
	ScheduleInterval (Date from){
		setDateFrom(from);
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	private void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
