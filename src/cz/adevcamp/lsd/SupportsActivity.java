package cz.adevcamp.lsd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.adevcamp.lsd.bo.ScheduleItem;
import cz.adevcamp.lsd.scheduler.TickService;
import cz.adevcamp.lsd.tools.ScheduleModel;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Zobrazeni polozek v seznam supportu
 * 
 * @author kovi
 * 
 */
public class SupportsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.supports_list);
	}

	private SupportArrayAdapter adapter;

	private void bindData() {
		ListView listView = (ListView) findViewById(R.id.lvActualizacion);
		ArrayList<ScheduleItem> values = getItemsFromDb();

		if (adapter == null) {
			adapter = new SupportArrayAdapter(this, values);
			listView.setAdapter(adapter);
		} else {
			// TODO: udelat diff objektu a rozmyslet optimalizaci
			adapter.clear();
			for (ScheduleItem item : values) {
				adapter.add(item);
			}
		}
	}

	private ArrayList<ScheduleItem> getItemsFromDb() {

		ArrayList<ScheduleItem> toReturn;
		ScheduleModel model = null;
		try {
			model = new ScheduleModel(getApplicationContext());
			model.openDatabase();
			toReturn = model.getScheduleFromToday();
			model.closeDatabase();
		} catch (Exception e) {
			if (model != null) {
				model.closeDatabase();
			}

			toReturn = new ArrayList<ScheduleItem>();

			Log.e(MainActivity.LOG_TAG, "error loading data from DB");
		}

		return toReturn;
	}

	@Override
	protected void onResume() {
		super.onResume();

		startListeningToBroadcasts();
		bindData();
	}

	@Override
	protected void onPause() {
		super.onPause();

		stopListeningToBroadcasts();
	}

	private MainActivityTickBroadcastServiceReceiver broadcastReceiver;
	private static final IntentFilter notifyFilter = new IntentFilter(TickService.NOTIFICATION_INTENT_STRING);

	private void startListeningToBroadcasts() {
		if (broadcastReceiver == null) {
			broadcastReceiver = new MainActivityTickBroadcastServiceReceiver();
		}
		registerReceiver(broadcastReceiver, notifyFilter);
	}

	private void stopListeningToBroadcasts() {
		if (broadcastReceiver != null) {
			unregisterReceiver(broadcastReceiver);
		}
	}

	/**
	 * Reakce na zmenu dat
	 * 
	 * @author kovi
	 * 
	 */
	private class MainActivityTickBroadcastServiceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.d(MainActivity.LOG_TAG, "Dostal jsem notifikaci od servisy");

			bindData();
		}
	}

	/**
	 * Zobrazeni radku v list view
	 * 
	 * @author kovi
	 * 
	 */
	public class SupportArrayAdapter extends ArrayAdapter<ScheduleItem> {

		private final Context context;
		private final List<ScheduleItem> schedules;

		private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.US); // tohle fakt blbost, ale nelze to udelat static

		/**
		 * Podrzi si obsah view a neprepina ho porad
		 * @author kovi
		 *
		 */
		class SupportViewHolder {
			public ImageView image;
			public TextView dateView;
			public TextView intervalView;
			public TextView nameView;
		}

		public SupportArrayAdapter(Context context, ArrayList<ScheduleItem> schedules) {
			super(context, R.layout.supports_list_item_detail, schedules);
			this.context = context;
			this.schedules = schedules;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.supports_list_item_detail, parent, false);

				SupportViewHolder viewHolder = new SupportViewHolder();
				viewHolder.image = (ImageView) rowView.findViewById(R.id.support_item_icon);
				viewHolder.dateView = (TextView) rowView.findViewById(R.id.lDate);
				viewHolder.intervalView = (TextView) rowView.findViewById(R.id.lInterval);
				viewHolder.nameView = (TextView) rowView.findViewById(R.id.lName);
				rowView.setTag(viewHolder);
			}

			SupportViewHolder holder = (SupportViewHolder) rowView.getTag();
			holder.image.setImageResource(R.drawable.ic_launcher);// TODO: ikonku podle jmena
			holder.dateView.setText(this.sdf.format(schedules.get(position).getDate()));
			holder.intervalView.setText(schedules.get(position).getInterval().toText(getResources()));

			holder.nameView.setText(schedules.get(position).getName().toString());
			return rowView;
		}
	}
}
