package cz.adevcamp.lsd;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import cz.adevcamp.lsd.scheduler.TickService;

public class MainActivity extends TabActivity {
	public static final String LOG_TAG = "SM-MainActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTickService();
        startListeningToBroadcasts();
        
        setContentView(R.layout.main);
        
        setTabContext();
    }
    
    private void setTabContext() {
    	Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, SupportsActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("supports").setIndicator(res.getString(R.string.tab_support_header),
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, LogsActivity.class);
        spec = tabHost.newTabSpec("logs").setIndicator(res.getString(R.string.tab_logs_header),
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, SupportsActivity.class);
        spec = tabHost.newTabSpec("configs").setIndicator(res.getString(R.string.tab_config_header),
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
	}

	private static final IntentFilter notifyFilter = new IntentFilter(TickService.NOTIFICATION_INTENT_STRING);
    private void startListeningToBroadcasts() {
    	MainActivityTickBroadcastServiceReceiver broadcastReceiver = new MainActivityTickBroadcastServiceReceiver(); 
    	registerReceiver(broadcastReceiver, notifyFilter);
	}

	/**
     * Po instalaci je treba jeste intentnout servicu, aby se spustila. Se spustenim zarizeni se spusti sama.
     */
    private void startTickService(){
        Intent bootTicking = new Intent(this, TickService.class);
        startService(bootTicking);    	
    }

    private class MainActivityTickBroadcastServiceReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.d(LOG_TAG, "Dostal jsem notifikaci od servisy");
			
			// TODO: prisla zmena, neco musim udelat.
		}
    }
}