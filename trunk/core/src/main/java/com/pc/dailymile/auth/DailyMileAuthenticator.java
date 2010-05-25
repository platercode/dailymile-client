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
package com.pc.dailymile.auth;

import com.pc.dailymile.utils.DailyMileUtil;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

public class DailyMileAuthenticator {

    /**
     * Retrieve the request token
     * 
     * @param consumerKey
     *            your applications consumer key
     * @param consumerSecret
     *            your applications consumer secret
     * @param callback
     *            the callback url registered for your application
     * @return the request token returned from dailymile
     * 
     * @throws Exception
     *             thrown if the token can't be fetched
     */
    public static RequestToken obtainRequestToken(String consumerKey, String consumerSecret,
            String callback) throws Exception {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

        OAuthProvider provider =
            new CommonsHttpOAuthProvider(DailyMileUtil.REQUEST_TOKEN_ENDPOINT_URL,
                    DailyMileUtil.ACCESS_TOKEN_ENDPOINT_URL, DailyMileUtil.AUTHORIZE_WEBSITE_URL);

        String url = provider.retrieveRequestToken(consumer, callback);
        return new RequestToken(provider, consumer, url);
    }

}
