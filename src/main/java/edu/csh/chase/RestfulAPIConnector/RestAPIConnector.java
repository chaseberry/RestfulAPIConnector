package edu.csh.chase.RestfulAPIConnector;

import android.os.AsyncTask;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ClassCastException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONObjectWrapper;
import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONWrapper;


public class RestAPIConnector extends AsyncTask<String, Void, JSONWrapper> {

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;
    public static final int HEAD = 4;
    public static final int OPTIONS = 5;

    private RestAPIListener runner;
    private HttpRequestBase httpRequest;

    /**
     * Creates a RestAPIConnector to make the connection to the server aysnc.
     *
     * @param runner The RestAPIListener to run once the request has been fulfilled
     * @param url The URL to make the request
     * @param method The method type
     * @param params Vararg of parameters
     * @throws InvalidMethodTypeException if the method type is invalid
     */
    public RestAPIConnector(RestAPIListener runner, String url, int method, Parameter... params) throws InvalidMethodTypeException {
        if (method < 0 || method > 5) {
            throw new InvalidMethodTypeException(method + " is not a valid method. Use RestAPIConnector to get " +
                    "valid methods");
        }
        this.runner = runner;
        HashMap<String, Object> paramMap = parseParameters(url, params);
        switch (method) {
            case GET:
                httpRequest = getGet(paramMap);
                break;
            case POST:
                httpRequest = getPost(paramMap);
                break;
            case PUT:
                httpRequest = getPut(paramMap);
                break;
            case DELETE:
                httpRequest = getDelete(paramMap);
                break;
            case HEAD:
                httpRequest = getHead(paramMap);
                break;
            case OPTIONS:
                httpRequest = getOptions(paramMap);
                break;
        }
    }

    private HashMap<String, Object> parseParameters(String baseUrl, Parameter[] parameters) {
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
                       JSONParamter jsonParameter = (JSONParameter) param;
                        switch(jsonParameter.getJsonParameterType){
                            case JSONParameter.JSONOBJECTPARAMETER:
                                value = jsonParameter.getJsonObjectValue;
                                break;
                            case JSONParameter.JSONARRAYPARAMETER:
                                value = jsonParameter.getJsonArrayValue;
                                break;
                        }
                    }
                    catch(ClassCastException e){

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
        paramMap.put("url", baseUrl);
        paramMap.put("formData", values);
        paramMap.put("headers", headers);
        paramMap.put("rawJson", rawJson);
        return paramMap;
    }

    private HttpPost getPost(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get("url");
        ArrayList<Header> headers = (ArrayList<Header>) paramMap.get("headers");
        HttpPost post = new HttpPost(url);
        if(headers != null) {
            for (Header header : headers) {
                post.addHeader(header);
            }
        }
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

    private HttpGet getGet(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get("url");
        ArrayList<Header> headers = (ArrayList<Header>) paramMap.get("headers");
        HttpGet get = new HttpGet(url);
        if(headers != null) {
            for (Header header : headers) {
                get.addHeader(header);
            }
        }
        return get;
    }

    private HttpPut getPut(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get("url");
        ArrayList<Header> headers = (ArrayList<Header>) paramMap.get("headers");
        HttpPut put = new HttpPut(url);
        if(headers != null) {
            for (Header header : headers) {
                put.addHeader(header);
            }
        }
        JSONObject rawJson = (JSONObject)paramMap.get("rawJson");
        if(rawJson != null){
            try {
                put.setEntity(new StringEntity(rawJson.toString()));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return put;
    }

    private HttpDelete getDelete(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get("url");
        ArrayList<Header> headers = (ArrayList<Header>) paramMap.get("headers");
        HttpDelete delete = new HttpDelete(url);
        if(headers != null) {
            for (Header header : headers) {
                delete.addHeader(header);
            }
        }
        return delete;
    }

    private HttpHead getHead(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get("url");
        ArrayList<Header> headers = (ArrayList<Header>) paramMap.get("headers");
        HttpHead head = new HttpHead(url);
        if(headers != null) {
            for (Header header : headers) {
                head.addHeader(header);
            }
        }
        return head;
    }

    private HttpOptions getOptions(HashMap<String, Object> paramMap) {
        String url = (String) paramMap.get("url");
        ArrayList<Header> headers = (ArrayList<Header>) paramMap.get("headers");
        HttpOptions options = new HttpOptions(url);
        if(headers != null) {
            for (Header header : headers) {
                options.addHeader(header);
            }
        }
        return options;
    }

    @Override
    protected JSONWrapper doInBackground(String... strings) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httpRequest);
            StatusLine status = response.getStatusLine();

            if (runner != null) {
                runner.setStatusCode(status.getStatusCode());
            }
            if(response.getEntity() == null){
                return null;
            }
            String res = EntityUtils.toString(response.getEntity());
            try {
                return new JSONWrapper(res);
            } catch (JSONException ex) {
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONWrapper jsonObject) {
        if (runner != null) {//We should NEVER get a code > 300 except for permissions errors
            runner.setData(jsonObject);
            runner.start();
        }
    }
}
