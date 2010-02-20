package com.pc.dailymile.utils;

import java.text.MessageFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DailyMileUtil {
	public static final String REQUEST_TOKEN_ENDPOINT_URL = "http://api.dailymile.com/oauth/request_token";
	public static final String ACCESS_TOKEN_ENDPOINT_URL = "http://api.dailymile.com/oauth/access_token";
	public static final String AUTHORIZE_WEBSITE_URL = "http://api.dailymile.com/oauth/authorize";
	
	public static final String ENTRIES_URL = "http://api.dailymile.com/entries.json";
	private static final String COMMENT_URL = "http://api.dailymile.com/entries/{0}/comments.json";
	
	public static Gson getGson() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}
	
	public static String buildUserStreamUrl(String username) {
		return "http://api.dailymile.com/people/" + username
			+ "/entries.json";
	}
	
	public static String buildEntryUrl(Long id) {
		return "http://api.dailymile.com/entries/" + id
			+ ".json";
	}
	
	public static String buildCommentUrl(Long id){
		return MessageFormat.format(COMMENT_URL, id.toString());
	}
	
	
}
