package com.pc.dailymile.domain.converters;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateConverter implements JsonDeserializer<Date>, JsonSerializer<Date> {

	//2010-12-25 12:15:00
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public Date deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(json.getAsJsonPrimitive().getAsString());
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse date", e);
		}
	}

	public JsonElement serialize(Date date, Type typeOfT,
			JsonSerializationContext context) {
		return new JsonPrimitive(new SimpleDateFormat(DATE_FORMAT).format(date));
	}
}
