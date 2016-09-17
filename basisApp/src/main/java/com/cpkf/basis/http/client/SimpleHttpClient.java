package com.cpkf.basis.http.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleHttpClient {
    private static Log logger = LogFactory.getLog("ROLLING_FILE");
    public HttpClient httpClient;
    
    public SimpleHttpClient (String serviceHost, int servicePort) {
        logger.info("Http client start: " + serviceHost + ":" + servicePort);
        httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(serviceHost, servicePort);
    }

    /**
     * get the post method by uri and parameters
     * @param uri
     * @param nameValues
     * @return
     */
    public HttpMethod getPostMethod (String uri, Map<String, String> nameValues) {
        HttpMethod postMethod = new PostMethod(uri);
        
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Set<String> names = nameValues.keySet();
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String name = it.next();
            String value = nameValues.get(name);
            NameValuePair nameValuePair = new NameValuePair(name, value);
            pairs.add(nameValuePair);
        }
        NameValuePair[] nameValuePairs = (NameValuePair[])pairs.toArray(new NameValuePair[pairs.size()]);
        
        postMethod.setQueryString(nameValuePairs);
        return postMethod;
    }
    
    /**
     * get getMoethod by uri
     * @param uri
     * @return
     */
    public HttpMethod getGetMethod (String uri) {
        return new GetMethod(uri);
    }
    
    public String executeRequest (String uri, Map<String, String> nameValues) {
        String response = "";
        HttpMethod method = null;
        try {
            method = getPostMethod(uri, nameValues);
            httpClient.executeMethod(method);
            response = method.getResponseBodyAsString();
            byte[] bytes = method.getResponseBody();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return response;
    }
    
    public static void main(String[] args) {
		SimpleHttpClient client = new SimpleHttpClient("172.17.20.112", 9999);
		String result = client.executeRequest("http://172.17.20.112:9999/file", new HashMap<String, String>());
		System.out.println(result);
	}
}
