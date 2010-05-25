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

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;

public class RequestToken {

    private OAuthProvider provider;
    private OAuthConsumer consumer;
    private String authorizeUrl;

    public RequestToken(OAuthProvider provider, OAuthConsumer consumer, String authorizeUrl) {
        this.provider = provider;
        this.consumer = consumer;
        this.authorizeUrl = authorizeUrl;
    }

    public String getAuthorizeUrl() {
        return this.authorizeUrl;
    }

    public OAuthConsumer getConsumer() {
        return this.consumer;
    }

    public OAuthProvider getProvider() {
        return this.provider;
    }
}
