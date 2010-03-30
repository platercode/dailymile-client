package com.pc.dailymile.auth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import com.pc.dailymile.utils.DailyMileUtil;

public class DailyMileAuthenticator {

	/**
	 * Retrieve the request token
	 * 
	 * @param consumerKey your applications consumer key
	 * @param consumerSecret your applications consumer secret
	 * @param callback the callback url registered for your application
	 * @return the request token returned from dailymile
	 * 
	 * @throws Exception thrown if the token can't be fetched
	 */
	public static RequestToken obtainRequestToken(String consumerKey,
			String consumerSecret, String callback) throws Exception {
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey,
				consumerSecret);

		OAuthProvider provider = new CommonsHttpOAuthProvider(
				DailyMileUtil.REQUEST_TOKEN_ENDPOINT_URL, 
				DailyMileUtil.ACCESS_TOKEN_ENDPOINT_URL,
				DailyMileUtil.AUTHORIZE_WEBSITE_URL);

		String url = provider.retrieveRequestToken(consumer, callback);
		return new RequestToken(provider, consumer, url);
	}
	
}
