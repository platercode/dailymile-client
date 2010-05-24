package com.pc.dailymile.utils;

import java.text.MessageFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pc.dailymile.domain.converters.TypeConverter;

public class DailyMileUtil {
	public static final String REQUEST_TOKEN_ENDPOINT_URL = "http://api.dailymile.com/oauth/request_token";
	public static final String ACCESS_TOKEN_ENDPOINT_URL = "http://api.dailymile.com/oauth/access_token";
	public static final String AUTHORIZE_WEBSITE_URL = "http://api.dailymile.com/oauth/authorize";
	
	public static final String ENTRIES_URL = "http://api.dailymile.com/entries.json";
	
	public static final String USER_AND_FRIENDS_STREAM_URL = "http://api.dailymile.com/entries/friends.json";

	private static final String COMMENT_URL = "http://api.dailymile.com/entries/{0}/comments.json";
	private static final String USER_STREAM_URL = "http://api.dailymile.com/people/{0}/entries.json";
	private static final String ENTRY_URL = "http://api.dailymile.com/entries/{0}.json";
		
	//date format: 2010-03-29T21:25:09-04:00
	private static final GsonBuilder gsonBuilder = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.Z")
			.registerTypeAdapter(Type.class, new TypeConverter());
	
	public static Gson getGson() {
		return gsonBuilder.create();
	}
	
	public static String buildUserStreamUrl(String username) {
		return MessageFormat.format(USER_STREAM_URL, username);
	}
	
	public static String buildEntryUrl(Long id) {
		return MessageFormat.format(ENTRY_URL, id.toString());
	}
	
	public static String buildCommentUrl(Long id) {
		return MessageFormat.format(COMMENT_URL, id.toString());
	}
}
