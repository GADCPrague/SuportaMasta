package cz.adevcamp.lsd;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import cz.adevcamp.lsd.scheduler.TickService;

public class MainActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startTickService();
		setContentView(R.layout.main);

		setTabContext();
	}

	private void setTabContext() {
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		intent = new Intent().setClass(this, SupportsActivity.class);
		spec = tabHost.newTabSpec("configs").setIndicator(res.getString(R.string.tab_support_header), res.getDrawable(R.drawable.main_tab_supports))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, LogsActivity.class);
		spec = tabHost.newTabSpec("logs").setIndicator(res.getString(R.string.tab_logs_header), res.getDrawable(R.drawable.main_tab_logs))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ConfigurationActivity.class);
		spec = tabHost.newTabSpec("configs").setIndicator(res.getString(R.string.tab_config_header), res.getDrawable(R.drawable.main_tab_config))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// NOTE: nated disablovana zalozka s logy
		tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
	}

	/**
	 * Po instalaci je treba jeste intentnout servicu, aby se spustila. Se spustenim zarizeni se spusti sama.
	 */
	private void startTickService() {
		Intent bootTicking = new Intent(this, TickService.class);
		startService(bootTicking);
	}

}