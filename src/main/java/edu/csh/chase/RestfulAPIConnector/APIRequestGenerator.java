package edu.csh.chase.RestfulAPIConnector;

import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chase on 9/27/14.
 */
public class APIRequestGenerator {

    private static final String URLKEY = "url";
    private static final String HEADERKEY = "headers";
    private static final String JSONKEY = "rawJson";
    private static final String FORMKEY = "formData";


    /**
     * Generates an HttpRequest from the given parameters.
     *
     * @param method An int method type defined in RestAPIRunnable
     * @param url The url to connect to
     * @param parameters A varArg of Parameters to add to this request. Only applicable parameters will be applied
     *                   to the request. IE POST Form data will only be added to a POST request. If given
     *                   Form and JSONBody, a post request will take only the Form data.
     * @return A HttpRequest if everything was valid, or null if a problem occured
     */
    public static HttpRequestBase generateRequest(final int method, String url, Parameter... parameters){
        HashMap<String, Object> paramMap = parseParameters(url, parameters);
        HttpRequestBase httpRequest = null;
        url = (String) paramMap.get(URLKEY);
        switch (method) {
            case RestAPIRunnable.GET:
                httpRequest = getGet(url);
                break;
            case RestAPIRunnable.POST:
                httpRequest = getPost(paramMap);
                break;
            case RestAPIRunnable.PUT:
                httpRequest = getPut(paramMap);
                break;
            case RestAPIRunnable.DELETE:
                httpRequest = getDelete(url);
                break;
            case RestAPIRunnable.HEAD:
                httpRequest = getHead(url);
                break;
            case RestAPIRunnable.OPTIONS:
                httpRequest = getOptions(url);
                break;
        }
        if(httpRequest == null){
            return null;
        }
        try {
            addHeadersToRequest((ArrayList<Header>) paramMap.get(HEADERKEY), httpRequest);
        }catch(ClassCastException e){
            //log couldn't add headers
        }
        return httpRequest;
    }

    private static void addHeadersToRequest(ArrayList<Header> headers, HttpRequestBase request){
        if (headers != null) {
            for (Header header : headers) {
                request.addHeader(header);
            }
        }
    }

    private static HashMap<String, Object> parseParameters(String baseUrl, Parameter[] parameters) {
        baseUrl += "?";
        ArrayList<BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();
        ArrayList<Header> headers = new ArrayList<Header>();
        JSONObject rawJson = new JSONObject();
        for (Parameter param : parameters) {
            switch (param.getMethodType()) {
                case Parameter.URLPARAMETER:
                    baseUrl += param.getKey() + "=" + param.getValue() + "&";
                    break;
                case Parameter.FORMDATA:
                    values.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                    break;
                case Parameter.HEADER:
                    headers.add(new BasicHeader(param.getKey(), param.getValue()));
                    break;
                case Parameter.JSONBODY:
                    Object value = param.getValue();
                    try {
                        JSONParameter parameter = (JSONParameter) param;
                        value = parameter.getObjectValue();
                    } catch (ClassCastException e) {
                    }
                    try {
                        rawJson.put(param.getKey(), value);
                    } catch (JSONException e) {

                    }
                    break;

            }
        }
        if (values.isEmpty()) {
            values = null;
        }
        if (headers.isEmpty()) {
            headers = null;
        }
        if (rawJson.length() == 0) {
            rawJson = null;
        }
        HashMap<String, Object> paramMap = new HashMap<String, Object>(4);
        paramMap.put(URLKEY, baseUrl);
        paramMap.put(FORMKEY, values);
        paramMap.put(HEADERKEY, headers);
        paramMap.put(JSONKEY, rawJson);
        return paramMap;
    }

    private static HttpPost getPost(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get(URLKEY);
        HttpPost post = new HttpPost(url);
        ArrayList<BasicNameValuePair> formParams = (ArrayList<BasicNameValuePair>) paramMap.get("formData");
        if (formParams != null) {
            try {
                post.setEntity(new UrlEncodedFormEntity(formParams));
                return post;
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        JSONObject rawData = (JSONObject) paramMap.get("rawJson");
        if (rawData != null) {
            try {
                post.setEntity(new StringEntity(rawData.toString()));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return post;
    }

    private static HttpGet getGet(String url) {
        return new HttpGet(url);
    }

    private static HttpPut getPut(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get("url");
        HttpPut put = new HttpPut(url);
        JSONObject rawJson = (JSONObject) paramMap.get("rawJson");
        if (rawJson != null) {
            try {
                put.setEntity(new StringEntity(rawJson.toString()));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return put;
    }

    private static HttpDelete getDelete(String url) {
        return new HttpDelete(url);
    }

    private static HttpHead getHead(String url) {
        return new HttpHead(url);
    }

    private static HttpOptions getOptions(String url) {
        return new HttpOptions(url);
    }

}
