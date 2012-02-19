package cz.adevcamp.lsd.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import cz.adevcamp.lsd.bo.ScheduleItem;

public class ScheduleDeserializer implements JsonDeserializer<ScheduleItem>{

	public ScheduleItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jo = (JsonObject)json;
	    String name = jo.get("name").getAsString();
	    String date = jo.get("date").getAsString();
	    Integer interval = jo.get("interval").getAsInt();

	//Should be an appropriate constructor
	    return new ScheduleItem(date, interval, name);
	}

}
