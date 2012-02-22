package cz.adevcamp.lsd;

/**
 * Konfiguracni konstanty
 * 
 * @author kovi
 * 
 */
public final class Configuration {

	public final static boolean IS_TESTING = true;
	/**
	 * Logovaci tagy
	 * 
	 * @author kovi
	 *
	 */
	public final static class LogTags {
		public static final String MAIN_TAG = "SM-Main";
		public static final String SCHEDULE_ITEM_TAG = "SM-Item";
		public static final String TICK_SERVICE_TAG = "SM-TickService";
		public static final String HTTP_TAG = "SM-Http";
	}

	/**
	 * Konfigurace datovych zdroju
	 * 
	 * @author kovi
	 * 
	 */
	public final static class DataSources {
		/**
		 * Pripojeni na Json resource
		 */
		public static final String JSON_URL = "http://adevcamp.pematon.com/SaportaMasta/schedule";
		/**
		 * Pripojeni na txt soubor
		 */
		public static final String TXT_URL = "http://dl.dropbox.com/u/1474328/Android/supporty.txt";
		/**
		 * Intranetovy soubor
		 */
		public static final String TXT_INTRANET = "file:\\\\192.168.123.100\\Users\\kovi\\Develop\\supporty.txt";
	}

	public final static class Intents {
		public static final String TICK_RECEIVER_NOTIFY_CHANGE = "cz.adevcamp.lsd.scheduler.TickReceiver.NotifyChange";
	}
}
