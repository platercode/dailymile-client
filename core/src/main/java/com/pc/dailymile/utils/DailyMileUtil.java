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
package com.pc.dailymile.utils;

import java.text.MessageFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pc.dailymile.domain.converters.DateConverter;
import com.pc.dailymile.domain.converters.TypeConverter;

public class DailyMileUtil {
    public static final String ENTRIES_URL = "https://api.dailymile.com/entries.json";

    public static final String USER_AND_FRIENDS_STREAM_URL =
        "https://api.dailymile.com/entries/friends.json";
    
    public static final String USER_AND_FRIENDS_STREAM_PAGED_URL =
            "https://api.dailymile.com/entries/friends.json?page={0}";
    
    public static final String USER_URL =
            "https://api.dailymile.com/people/me.json";

    private static final String COMMENT_URL = "https://api.dailymile.com/entries/{0}/comments.json";
    private static final String USER_STREAM_URL =
        "https://api.dailymile.com/people/{0}/entries.json";
    private static final String ENTRY_URL = "https://api.dailymile.com/entries/{0}.json";
    
    private static final String USER_STREAM_PAGED_URL =
        "https://api.dailymile.com/people/{0}/entries.json?page={1}";

    // date format: 2010-03-29T21:25:09-04:00
    private static final GsonBuilder GSON_BUILDER =
        new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.Z").registerTypeAdapter(Type.class,
                new TypeConverter()).registerTypeAdapter(Date.class, new DateConverter());

    public static Gson getGson() {
        return GSON_BUILDER.create();
    }

    public static String buildUserStreamUrl(String username) {
        return MessageFormat.format(USER_STREAM_URL, username);
    }
    
    public static String buildUserStreamUrl(String username, int pageNumber, EntryCriteria query) {
        StringBuilder rtn = new StringBuilder();
        rtn.append(MessageFormat.format(USER_STREAM_PAGED_URL, username, pageNumber));
        if (query != null) {
            String queryString = query.buildQueryString();
            if (queryString != null && queryString.length() > 0) {
                rtn.append("&").append(queryString);
            }
        }
        
        return rtn.toString();
    }
    
    public static String buildUserAndFriendsStreamUrl(int pageNumber, EntryCriteria query) {
        StringBuilder rtn = new StringBuilder();
        rtn.append(MessageFormat.format(USER_AND_FRIENDS_STREAM_PAGED_URL, pageNumber));
        if (query != null) {
            String queryString = query.buildQueryString();
            if (queryString != null && queryString.length() > 0) {
                rtn.append("&").append(queryString);
            }
        }
        
        return rtn.toString();
    }

    public static String buildEntryUrl(Long id) {
        return MessageFormat.format(ENTRY_URL, id.toString());
    }

    public static String buildCommentUrl(Long id) {
        return MessageFormat.format(COMMENT_URL, id.toString());
    }
}
