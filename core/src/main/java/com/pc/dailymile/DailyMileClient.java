/*
   Copyright 2010 platers.code

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package com.pc.dailymile;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.pc.dailymile.domain.Entry;
import com.pc.dailymile.domain.UserStream;
import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.DailyMileUtil;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.HttpRequestAdapter;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;

public class DailyMileClient {

    private OAuthConsumer oauthConsumer;
    private HttpClient httpClient;

    /**
     * Create a new DailyMileClient object that will use the provided
     * OAuthConsumer to sign requests
     * 
     * @param oauthConsumer
     *            a valid OAuthConsumer that can be used to sign requests
     */
    public DailyMileClient(OAuthConsumer oauthConsumer) {
        this.oauthConsumer = oauthConsumer;
        initHttpClient();
    }

    /**
     * Create a new DailyMileClient object that will use the provided
     * OAuthConsumer to sign requests and the provided HttpClient.
     * 
     * Note: Daily Mile might not work with all useragents
     * 
     * @param oauthConsumer
     *            a valid OAuthConsumer that can be used to sign requests
     * @param httpClient
     *            the httpClient to use when making requests
     */
    public DailyMileClient(OAuthConsumer oauthConsumer, HttpClient httpClient) {
        this.oauthConsumer = oauthConsumer;
        this.httpClient = httpClient;
    }

    /**
     * Retrieve a users stream. A users stream contains the 20 most recent
     * entries
     * 
     * @param username
     *            the user who's stream you want to pull down
     * 
     * @return the user's stream (20 most recent entries)
     */
    public UserStream getUserStream(String username) {
        return DailyMileUtil.getGson().fromJson(
                getResource(DailyMileUtil.buildUserStreamUrl(username)), UserStream.class);
    }

    /**
     * Retrieve a stream belonging to the user and the user's friends.
     */
    public UserStream getUserAndFriendsStream() {
        try {
            return DailyMileUtil.getGson()
                    .fromJson(getSecuredResource(DailyMileUtil.USER_AND_FRIENDS_STREAM_URL),
                            UserStream.class);
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch user and friends stream", e);
        }
    }

    /**
     * Add a workout
     * 
     * @param consumer
     *            an authenticated consumer
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
            throw new RuntimeException("Unable to add workout", e);
        }
    }

    /**
     * Delete the entry with the provided id. You must have permissions to
     * delete the entry.
     * 
     * @param id
     *            id of the entry to delete
     */
    public void deleteEntry(Long id) {
        try {
            doAuthenticatedDelete(DailyMileUtil.buildEntryUrl(id));
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete entry", e);
        }
    }

    /**
     * Get a workout by id
     * 
     * @param id
     *            id of the workout you want
     * @return the workout
     */
    public Workout getWorkout(Long id) {
        return getEntry(id).getWorkout();
    }

    /**
     * Adds to provided comment to the entry with the provided id
     * 
     * @param comment
     *            comment to add
     * @param id
     *            id of the entry to add the comment to
     */
    public void addComment(String comment, Long id) {
        String body = "{\"body\":\"" + comment + "\"}";
        try {
            doAuthenticatedPost(DailyMileUtil.buildCommentUrl(id), body);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add comment", e);
        }
    }

    private void initHttpClient() {
        HttpParams parameters = new BasicHttpParams();
        HttpProtocolParams.setVersion(parameters, HttpVersion.HTTP_1_1);
        // set the User-Agent to a common User-Agent because currently
        // the default httpclient User-Agent doesn't work with dailymile
        HttpProtocolParams.setContentCharset(parameters, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUserAgent(parameters,
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        HttpProtocolParams.setUseExpectContinue(parameters, false);
        HttpConnectionParams.setTcpNoDelay(parameters, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        ClientConnectionManager tsccm = new ThreadSafeClientConnManager(parameters, schReg);
        httpClient = new DefaultHttpClient(tsccm, parameters);
    }

    private Entry getEntry(Long id) {
        return DailyMileUtil.getGson().fromJson(getResource(DailyMileUtil.buildEntryUrl(id)),
                Entry.class);
    }

    /**
     * Generic method for adding a dailymile "entry". Currently an entry can be
     * a note or a workout
     * 
     * @param entry
     *            the entry to add
     * 
     * @throws Exception
     *             thrown if the add fails
     */
    private void addEntry(Entry entry) throws Exception {
        String json = DailyMileUtil.getGson().toJson(entry);
        doAuthenticatedPost(DailyMileUtil.ENTRIES_URL, json);
    }

    private void doAuthenticatedPost(String url, String body) throws Exception {

        if (oauthConsumer.getConsumerKey() == null || oauthConsumer.getConsumerSecret() == null) {
            throw new OAuthExpectationFailedException("Consumer key or secret not set");
        }
        HttpPost request = new HttpPost(url);
        HttpResponse response = null;
        try {
            HttpEntity entity = new StringEntity(body);
            // set the content type to json
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            request.setEntity(entity);
            // sign the request
            oauthConsumer.sign(new HttpRequestAdapter(request));
            // send the request
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 401) {
                throw new RuntimeException("unable to execute POST - url: "
                    + url
                    + " body: "
                    + body);
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

    private void doAuthenticatedDelete(String url) throws Exception {

        if (oauthConsumer.getConsumerKey() == null || oauthConsumer.getConsumerSecret() == null) {
            throw new OAuthExpectationFailedException("Consumer key or secret not set");
        }
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = null;
        try {
            // set the content type to json
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            // sign the request
            oauthConsumer.sign(new HttpRequestAdapter(request));
            // send the request
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 401) {
                throw new RuntimeException("unable to execute DELETE - url: " + url);
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
        HttpResponse response = null;
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
            throw new RuntimeException("Unable to get resource", e);
        }
    }

    // handles the few API resources that require oauth
    private String getSecuredResource(String url) throws OAuthExpectationFailedException {

        if (oauthConsumer.getConsumerKey() == null || oauthConsumer.getConsumerSecret() == null) {
            throw new OAuthExpectationFailedException("Consumer key or secret not set");
        }
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            oauthConsumer.sign(new HttpRequestAdapter(request));
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Unable to get resource: " + url);
            }
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get resource", e);
        }
    }

}
