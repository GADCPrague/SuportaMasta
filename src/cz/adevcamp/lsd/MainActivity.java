package cz.adevcamp.lsd;

import cz.adevcamp.lsd.scheduler.SetupReceiver;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Po instalaci je treba jeste intentnout servicu, aby se spustila. Se spustenim
        // zarizeni se spusti sama.
        Intent bootTicking = new Intent(getBaseContext(), SetupReceiver.class);
        startService(bootTicking);
        
    }
}