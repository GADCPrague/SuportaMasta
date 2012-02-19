package cz.adevcamp.lsd.tools;

import android.util.Log;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author peter
 */
public class Http {
	public static final String LOG_TAG = "SM-Http";

	public static String downloadText(String url) {
		int BUFFER_SIZE = 2000;

		InputStream in = null;
		try {
			in = Http.openHttpConnection(url);

		} catch (Exception e) {
			Http.logError(e);
			return "";
		}

		if (in == null)
			return "";

		Log.d(LOG_TAG, "Reading stream");
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(in, "utf-8");

		} catch (UnsupportedEncodingException e) {
			Http.logError(e);
			return "";
		}

		int charRead;
		String str = "";
		char[] inputBuffer = new char[BUFFER_SIZE];

		try {
			while ((charRead = isr.read(inputBuffer)) > 0) {
				String readString = String.copyValueOf(inputBuffer, 0, charRead);
				str += readString;
				inputBuffer = new char[BUFFER_SIZE];
			}
			in.close();

		} catch (Exception e) {
			Http.logError(e);
			return "";
		}

		return str;
	}

	private static InputStream openHttpConnection(String urlString) throws Exception {
		InputStream in = null;

		try {
			Log.d(LOG_TAG, "Opening connection");

			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();

			if (!(conn instanceof HttpURLConnection)) {
				throw new Exception("connection_error");
			}

			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestProperty("Accept-Charset", "utf-8");
			httpConn.setRequestProperty("Content-Type", "text/html; charset=utf-8");
			httpConn.setRequestMethod("GET");

			Log.d(LOG_TAG, "Connecting");
			httpConn.connect();

			int response = httpConn.getResponseCode();
			Log.d(LOG_TAG, "Response code: " + response);

			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}

		} catch (Exception e) {
			Http.logError(e);
			return null;
		}

		return in;
	}

	public static void logError(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String retValue = sw.toString();

		Log.d(LOG_TAG, retValue);
	};
}
