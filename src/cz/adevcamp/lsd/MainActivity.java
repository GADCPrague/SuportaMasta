package cz.adevcamp.lsd;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import cz.adevcamp.lsd.scheduler.SetupReceiver;
import cz.adevcamp.lsd.scheduler.TickService;

public class MainActivity extends Activity {
	public static final String LOG_TAG = "SM-MainActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTickService();
        startListeningToBroadcasts();
        setContentView(R.layout.main);      
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
        Intent bootTicking = new Intent(this, SetupReceiver.class);
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