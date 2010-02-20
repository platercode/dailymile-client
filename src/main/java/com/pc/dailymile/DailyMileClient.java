package com.pc.dailymile;

import java.io.IOException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.HttpRequestAdapter;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.pc.dailymile.domain.Entry;
import com.pc.dailymile.domain.UserStream;
import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.DailyMileUtil;

public class DailyMileClient {
	
	private OAuthConsumer oauthConsumer;
	
	/**
	 * Create a new DailyMileClient object that will use the provided
	 * OAuthConsumer to sign requests
	 * 
	 * @param oauthConsumer a valid OAuthConsumer that can be used to sign requests
	 */
	public DailyMileClient(OAuthConsumer oauthConsumer){
		this.oauthConsumer = oauthConsumer;
	}

	/**
	 * Retrieve a users stream.  A users stream contains
	 * the 20 most recent entries
	 * 
	 * @param username the user who's stream you want to pull down
	 * 
	 * @return the user's stream (20 most recent entries)
	 */
	public UserStream getUserStream(String username) {
		return DailyMileUtil.getGson().fromJson(getResource(DailyMileUtil
				.buildUserStreamUrl(username)), UserStream.class);
	}

	/**
	 * Add a workout
	 * 
	 * @param consumer an authenticated consumer
	 * @param workout
	 * @param message
	 */
	public void addWorkout(Workout workout, String message) {
		Entry entry = new Entry();
		entry.setMessage(StringUtils.stripToEmpty(message));
		entry.setWorkout(workout);
		try {
			addEntry(entry);
		} catch (Exception e) {
			throw new RuntimeException("Unable to add workout",e);
		}
	}
	
	/**
	 * Delete the entry with the provided id.  You must
	 * have permissions to delete the entry.
	 * 
	 * @param id id of the entry to delete
	 */
	public void deleteEntry(Long id) {
		throw new UnsupportedOperationException("delete isn't supported yet");
	}

	/**
	 * Get a workout by id
	 * 
	 * @param id id of the workout you want
	 * @return the workout
	 */
	public Workout getWorkout(Long id) {
		return getEntry(id).getWorkout();
	}
	
	private Entry getEntry(Long id) {
		return DailyMileUtil.getGson().fromJson(getResource(DailyMileUtil
				.buildEntryUrl(id)), Entry.class);
	}
	
	/**
	 * Generic method for adding a dailymile "entry".  Currently an 
	 * entry can be a note or a workout
	 * 
	 * @param entry the entry to add
	 * 
	 * @throws Exception thrown if the add fails
	 */
	private void addEntry(Entry entry) throws Exception {
		
		if (oauthConsumer.getConsumerKey() == null
				|| oauthConsumer.getConsumerSecret() == null) {
			throw new OAuthExpectationFailedException(
					"Consumer key or secret not set");
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost(DailyMileUtil.ENTRIES_URL);
		HttpResponse response = null;
		try {
			String json = DailyMileUtil.getGson().toJson(entry);
			HttpEntity entity = new StringEntity(json);
			//set the content type to json
			request.setHeader("Content-Type", "application/json; charset=utf-8");
			request.setEntity(entity);
			//sign the request
			oauthConsumer.sign(new HttpRequestAdapter(request));
			//send the request
			response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 401) {
				throw new RuntimeException("unable to add entry - " + json);
			}
		} catch (OAuthExpectationFailedException e) {
			throw e;
		} catch (Exception e) {
			throw new OAuthCommunicationException(e);
		} finally {
			if (response != null) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					try {
						entity.consumeContent();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// no authentication
	private String getResource(String url) {
		HttpGet request = new HttpGet(url);
		//set the User-Agent to a common User-Agent because currently
		//the default httpclient User-Agent doesn't work with dailymile
		request.setHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		
		HttpResponse response = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Unable to get resource: " + url);
			}

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);

		} catch (Exception e) {
			throw new RuntimeException("Unable to get resouce", e);
		}
	}

}
