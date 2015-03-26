package edu.csh.chase.RestfulAPIConnector;


import org.apache.http.client.methods.HttpRequestBase;

import java.util.ArrayList;

public class Request extends ArrayList<Parameter> {

    private final String url;

    private final RequestType requestType;

    private final RestAPIListener responseListener;

    public Request(String url, RequestType type, RestAPIListener listener, String extraData) {
        super();
        this.url = url;
        this.requestType = type;
        this.responseListener = listener;
        if (extraData != null && responseListener != null) {
            responseListener.setExtra(extraData);
        }
    }

    public Request(String url, RequestType type, RestAPIListener listener) {
        this(url, type, listener, null);
    }

    public String getUrl() {
        return url;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public RestAPIListener getResponseListener() {
        return responseListener;
    }

    public HttpRequestBase build() {
        return APIRequestGenerator.generateRequest(requestType, url, arrayOfParameters());
    }

    private Parameter[] arrayOfParameters() {
        Parameter[] arr = new Parameter[this.size()];
        for (int z = 0; z < this.size(); z++) {
            arr[z] = this.get(z);
        }
        return arr;
    }
}
