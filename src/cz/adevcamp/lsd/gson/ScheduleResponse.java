package cz.adevcamp.lsd.gson;

import com.google.gson.annotations.SerializedName;
import cz.adevcamp.lsd.bo.ScheduleItem;
import java.util.ArrayList;

/**
 * Response with all scheduled items.
 *
 * @author peter
 */
public class ScheduleResponse {
	@SerializedName ("item")
	private ArrayList<ScheduleItem> items;

	public ScheduleResponse() {
		super();
	}

	public ScheduleResponse(ArrayList<ScheduleItem> items) {
		super();
		this.items = items;
	}

	public ArrayList<ScheduleItem> getItems() {
		return this.items;
	}

	public void setItems(ArrayList<ScheduleItem> items) {
		this.items = items;
	}
}
