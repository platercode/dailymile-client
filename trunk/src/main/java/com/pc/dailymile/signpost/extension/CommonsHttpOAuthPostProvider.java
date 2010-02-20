package com.pc.dailymile.signpost.extension;

import java.io.IOException;
import java.util.Map;

import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.HttpRequestAdapter;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * An implementation of OAuthProvider that retrieves the token using a
 * HTTP POST (CommonsHttpOAuthProvider uses a GET).
 * 
 * Note: If signpost adds support for this, this class should be removed.
 * 
 * @author jplater
 *
 */
public class CommonsHttpOAuthPostProvider extends AbstractOAuthProvider {
	private static final long serialVersionUID = 1L;
	private HttpClient httpClient;

	public CommonsHttpOAuthPostProvider(String requestTokenEndpointUrl,
			String accessTokenEndpointUrl, String authorizationWebsiteUrl) {
		super(requestTokenEndpointUrl, accessTokenEndpointUrl,
				authorizationWebsiteUrl);
		httpClient = new DefaultHttpClient();
	}

	protected void retrieveToken(OAuthConsumer consumer, String endpointUrl)
			throws OAuthMessageSignerException, OAuthCommunicationException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException {
		Map<String, String> defaultHeaders = getRequestHeaders();
		if (consumer.getConsumerKey() == null
				|| consumer.getConsumerSecret() == null) {
			throw new OAuthExpectationFailedException(
					"Consumer key or secret not set");
		}
		HttpPost request = new HttpPost(endpointUrl);
		
		for (String header : defaultHeaders.keySet()) {
			request.setHeader(header, defaultHeaders.get(header));
		}
		HttpResponse response = null;
		try {
			consumer.sign(new HttpRequestAdapter(request));
			response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 401) {
				throw new OAuthNotAuthorizedException();
			}
			Map<String, String> responseParams = OAuth.decodeForm(response
					.getEntity().getContent());
			String token = responseParams.get(OAuth.OAUTH_TOKEN);
			String secret = responseParams.get(OAuth.OAUTH_TOKEN_SECRET);
			responseParams.remove(OAuth.OAUTH_TOKEN);
			responseParams.remove(OAuth.OAUTH_TOKEN_SECRET);
			setResponseParameters(responseParams);
			if (token == null || secret == null) {
				throw new OAuthExpectationFailedException(
						"Request token or token secret not set in server reply. "
								+ "The service provider you use is probably buggy.");
			}
			consumer.setTokenWithSecret(token, secret);
		} catch (OAuthNotAuthorizedException e) {
			throw e;
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

	void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
}
