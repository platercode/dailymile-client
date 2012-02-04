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
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.pc.dailymile.domain.Entry;
import com.pc.dailymile.domain.User;
import com.pc.dailymile.domain.UserAndFriendsStreamIterator;
import com.pc.dailymile.domain.UserStream;
import com.pc.dailymile.domain.UserStreamIterator;
import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.DailyMileUtil;
import com.pc.dailymile.utils.EntryCriteria;


public class DailyMileClient {

    private String oauthToken;
    private HttpClient httpClient;

    /**
     * Create a new DailyMileClient object that will use the provided
     * OAuthToken to authorize requests
     * 
     * @param oauthToken
     *            a valid OAuthToken that can be passed along with the request
     */
    public DailyMileClient(String oauthToken) {
        this.oauthToken = oauthToken;
        initHttpClient();
    }

    /**
     * Create a new DailyMileClient object that will use the provided
     * OAuthToken to authorize requests and the provided HttpClient.
     * 
     * Note: Daily Mile might not work with all useragents
     * 
     * @param oauthToken
     *            a valid OAuthToken that can be passed along with the request
     * @param httpClient
     *            the httpClient to use when making requests
     */
    public DailyMileClient(String oauthToken, HttpClient httpClient) {
        this.oauthToken = oauthToken;
        this.httpClient = httpClient;
    }

    /**
     * Retrieve the current user's information
     * 
     * @return information on the logged in user
     */
    public User getUser() {
        try {
            return DailyMileUtil.getGson().fromJson(
                    getSecuredResource(DailyMileUtil.USER_URL), User.class);
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch user", e);
        }
    }
    
    /**
     * Retrieve a user's stream. A users stream contains the 20 most recent
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
     * Retrieve a specific page of a user's stream 
     * 
     * @param username
     *             the user who's stream you want to pull down
     * @param page
     *             
     * @param criteria
     * @return
     */
    public UserStream getUserStream(String username, int page, EntryCriteria criteria) {
        return DailyMileUtil.getGson().fromJson(
                getResource(DailyMileUtil.buildUserStreamUrl(username, page, criteria)),
                UserStream.class);
    }
    
    /**
     * Retrieve all user entries that match the provided criteria.  The entries
     * are returned in an Iterator which is lazily loaded.
     * 
     * @param username 
     *              the user who's entries you want to pull down
     * @param criteria
     *              any filtering you want to do, pass null to get all entries
     * @return
     *              an Iterator of entries which is lazily loaded
     */
    public Iterator<Entry> getUserEntries(String username, EntryCriteria criteria) {
        return new UserStreamIterator(this, username, criteria);
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
    
    public UserStream getUserAndFriendsStream(int page, EntryCriteria criteria) {
        return DailyMileUtil.getGson().fromJson(
                getSecuredResource(DailyMileUtil.buildUserAndFriendsStreamUrl(page, criteria)),
                UserStream.class);
    }
    
    public Iterator<Entry> getUserAndFriendsEntries(EntryCriteria criteria) {
        return new UserAndFriendsStreamIterator(this, criteria);
    }

    /**
     * Add a workout
     * 
     * @param consumer
     *            an authenticated consumer
     * @param workout
     * @param message
     * @return the id of the workout that was created
     */
    public Long addWorkout(Workout workout, String message) {
        Entry entry = new Entry();
        entry.setMessage(StringUtils.stripToEmpty(message));
        entry.setWorkout(workout);
        try {
            return addEntry(entry);
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
     * Adds to provided comment to the entry with the provided id
     * 
     * @param comment
     *            comment to add
     * @param id
     *            id of the entry to add the comment to
     * @return the id of the comment that was added
     */
    public Long addComment(String comment, Long id) {
        String body = "{\"body\":\"" + comment + "\"}";
        try {
            return doAuthenticatedPost(DailyMileUtil.buildCommentUrl(id), body);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add comment", e);
        }
    }

    /**
     * Gets an entry by id
     * 
     * @param id
     *            id of the entry you want
     * @return the entry
     */
    public Entry getEntry(Long id) {
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
     * @return the id of the entry that was created
     */
    private Long addEntry(Entry entry) throws Exception {
        String json = DailyMileUtil.getGson().toJson(entry);
        return doAuthenticatedPost(DailyMileUtil.ENTRIES_URL, json);
    }

    
    private Long doAuthenticatedPost(String url, String body) throws Exception {
        HttpPost request = new HttpPost(url + "?oauth_token=" + oauthToken);
        HttpResponse response = null;
        try {
            HttpEntity entity = new StringEntity(body);
            // set the content type to json
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            request.setEntity(entity);
            // sign the request
            // send the request
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 401) {
                throw new RuntimeException("unable to execute POST - url: "
                    + url
                    + " body: "
                    + body);
            }
            
            if (statusCode == 201) {
                Header loc = response.getFirstHeader("Location");
                if (loc != null) {
                    String locS = loc.getValue();
                    if (!StringUtils.isBlank(locS) && locS.matches(".*/[0-9]+$")) {
                        try {
                            return NumberUtils.createLong(
                                    locS.substring(locS.lastIndexOf("/") + 1));
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                }
            }
            
            return null;
        } finally {
            try {
                if (response != null) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                }
                //EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                // ignore
            }
        }
    }

    private void doAuthenticatedDelete(String url) throws Exception {
        HttpDelete request = new HttpDelete(url + "?oauth_token=" + oauthToken);
        HttpResponse response = null;
        try {
            // set the content type to json
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            // send the request
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 401) {
                throw new RuntimeException("unable to execute DELETE - url: " + url);
            }
        } finally {
            try {
                if (response != null) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                }
                //EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                // ignore
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
    private String getSecuredResource(String url) {
        HttpGet request =
            new HttpGet(url.contains("?") ? (url + "&oauth_token=" + oauthToken) : (url
                + "?oauth_token=" + oauthToken));
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
    
    private void initHttpClient() {
        HttpParams parameters = new BasicHttpParams();
        HttpProtocolParams.setVersion(parameters, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(parameters, HTTP.DEFAULT_CONTENT_CHARSET);
        // set the User-Agent to a common User-Agent because currently
        // the default httpclient User-Agent doesn't work with dailymile
        HttpProtocolParams.setUserAgent(parameters,
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        HttpProtocolParams.setUseExpectContinue(parameters, false);
        HttpConnectionParams.setTcpNoDelay(parameters, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager tsccm = new ThreadSafeClientConnManager(parameters, schReg);
        
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(tsccm, parameters);
        defaultHttpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(final HttpRequest request, final HttpContext context)
                throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            }
        });

        defaultHttpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(final HttpResponse response, final HttpContext context)
                throws HttpException, IOException {
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(
                                new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
            }
        });
        httpClient = defaultHttpClient;
    }
    
    static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent() throws IOException, IllegalStateException {
            InputStream wrappedin = wrappedEntity.getContent();
            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }
    }

}
