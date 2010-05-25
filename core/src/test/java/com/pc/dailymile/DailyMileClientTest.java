package com.pc.dailymile;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import com.pc.dailymile.domain.UserStream;

public class DailyMileClientTest {

    private static final String USERNAME = "travistruman";
    private OAuthConsumer consumer = new CommonsHttpOAuthConsumer("foo", "bar");

    @Test
    public void testGetUserStreamWithAcceptGzipEncoding() {
        
        UserStream stream = new DailyMileClient(
                consumer, buildHttpClient(false)).getUserStream(USERNAME);
        
        UserStream gzipStream = new DailyMileClient(
                consumer, buildHttpClient(true)).getUserStream(USERNAME);
        
        assertEquals(stream.getEntries(), gzipStream.getEntries());
    }

    private DefaultHttpClient buildHttpClient(boolean acceptGzip) {
        HttpParams parameters = new BasicHttpParams();
        HttpProtocolParams.setVersion(parameters, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(parameters, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUserAgent(parameters,
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        HttpProtocolParams.setUseExpectContinue(parameters, false);
        HttpConnectionParams.setTcpNoDelay(parameters, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        ClientConnectionManager tsccm = new ThreadSafeClientConnManager(parameters, schReg);
        DefaultHttpClient httpClient = new DefaultHttpClient(tsccm, parameters);
        if (acceptGzip) {
            httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
                public void process(final HttpRequest request, final HttpContext context)
                        throws HttpException, IOException {
                    if (!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept-Encoding", "gzip");
                    }
                }
            });

            httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
                public void process(final HttpResponse response, final HttpContext context)
                        throws HttpException, IOException {
                    HttpEntity entity = response.getEntity();
                    Header ceheader = entity.getContentEncoding();
                    if (ceheader != null) {
                        HeaderElement[] codecs = ceheader.getElements();
                        for (int i = 0; i < codecs.length; i++) {
                            if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                                response.setEntity(
                                    new DailyMileClient.GzipDecompressingEntity(response.getEntity()));
                                return;
                            }
                        }
                    }
                }
            });
        }
        return httpClient;
    }
}
