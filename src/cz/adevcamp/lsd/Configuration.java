package cz.adevcamp.lsd;

/**
 * Konfiguracni konstanty
 * 
 * @author kovi
 * 
 */
public final class Configuration {

	/**
	 * Logovaci tagy
	 * 
	 * @author kovi
	 *
	 */
	public final class LogTags {
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
	public final class DataSources {
		/**
		 * Pripojeni na Json resource
		 */
		public static final String JSON_URL = "http://adevcamp.pematon.com/SaportaMasta/";
		/**
		 * Pripojeni na txt soubor
		 */
		public static final String TXT_URL = "http://dl.dropbox.com/u/1474328/Android/supporty.txt";
	}

}
