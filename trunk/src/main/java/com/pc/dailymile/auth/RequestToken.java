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
	
	public String getAuthorizeUrl(){
		return this.authorizeUrl;
	}
	
	public OAuthConsumer getConsumer(){
		return this.consumer;
	}
	
	public OAuthProvider getProvider(){
		return this.provider;
	}
}
