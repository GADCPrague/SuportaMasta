package cz.adevcamp.lsd;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class ConfigurationActivity extends PreferenceActivity {

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.configuration);
		
		Preference pref;
		
		pref = findPreference("autoActualization");
		pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				
				// Disablujeme nastavenie obnovenia intervalu.
				Preference prefInterval = findPreference("actualizationInterval");
				prefInterval.setEnabled(newValue.equals(true));
				
				// TODO: spustit/zastavit service.
				
				return true;
			}
		});
		
		pref = findPreference("name");
		pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {

				// TODO: pri zmene mena na to nejak zareagovat.
				
				return true;
			}
		});
		
		/* 
		Ukazka ziskania hodnot uozenych v shared preferencies:
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		sharedPrefs.getBoolean("autoActualization", true);
		*/
	}
}
