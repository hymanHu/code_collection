package com.cpkf.util.iTextPDF;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class HttpClientUtil {
	
	@SuppressWarnings("static-access")
	public InputStream doURLPost(String requestUrl) throws IOException {
		InputStream is = null;
		
		String userAgent = "User-Agent";
		String userAgentValue = "Mozilla/12.0 (compatible; MSIE 8.0; Windows NT)";
		String contentType = "Content-Type";
		String contentTypeValue = "application/x-www-form-urlencoded";

		URL url = new URL(requestUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");
		httpConn.setRequestProperty(userAgent, userAgentValue);
		httpConn.setRequestProperty(contentType, contentTypeValue);
		httpConn.setFollowRedirects(false);
		httpConn.setInstanceFollowRedirects(false);
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.connect();
		
		String cookies = getCookies(httpConn);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestProperty(userAgent, userAgentValue);
		httpConn.setRequestProperty(contentType, contentTypeValue);
		httpConn.setRequestProperty("Cookie", cookies);
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		is = httpConn.getInputStream();
		
		return is;
	}

	public static String getCookies(HttpURLConnection conn) {
		StringBuffer cookies = new StringBuffer();
		String semicolon = "; ";
		String headName;
		int i = 0;
		while (conn.getHeaderField(i) != null) {
			headName = conn.getHeaderField(i);
			StringTokenizer st = new StringTokenizer(headName, semicolon);
			while (st.hasMoreTokens()) {
				cookies.append(st.nextToken() + semicolon);
			}
			i++;
		}
		return cookies.toString();
	}
}
