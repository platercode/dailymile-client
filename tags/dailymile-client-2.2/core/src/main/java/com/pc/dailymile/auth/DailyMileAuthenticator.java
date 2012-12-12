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

import java.text.MessageFormat;


public class DailyMileAuthenticator {

    private static final String AUTH_URL =
        "https://api.dailymile.com/oauth/authorize?" +
        "response_type=token&client_id={0}&redirect_uri={1}";
    
    /**
     * Build out the authorize url
     * 
     * @param clientId
     *            your applications client id
     * @param callback
     *            the callback url registered for your application
     * @return the url where you should direct users to
     * 
     * @throws Exception
     *             thrown if the token can't be fetched
     */
    public static String buildAuthorizeUrl(String clientId,
            String callback) throws Exception {
        
        return MessageFormat.format(AUTH_URL, clientId, callback);
    }

}
