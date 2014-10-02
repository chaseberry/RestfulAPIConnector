package edu.csh.chase.RestfulAPIConnector;


import android.content.Context;

import org.apache.http.client.methods.HttpRequestBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIConnectionManager {

    private Context parent;
    private String url;
    private static ExecutorService apiExecutorService;

    /**
     * Creates an APIConnectionManager to interface with the RestAPIConnector
     *
     * @param context The context of the parent
     * @param url The base URL for the connection
     */
    public APIConnectionManager(Context context, String url) {
        parent = context;
        this.url = url;
        if(apiExecutorService == null){
            apiExecutorService = Executors.newCachedThreadPool();
        }
    }

    /**
     * Creates an APIConnectionManager to interface with the RestAPIConnector
     *
     * @param context The context of the parent
     */
    public APIConnectionManager(Context context) {
        this(context, "");
    }

    /**
     * Sets the default URL for requests
     * @param url The base URL for the connection
     */
    public void setDefaultUrl(String url) {
        this.url = url;
    }

    /**
     * Executes a request to the requestURL
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner A RestAPIListener to process the returned data when the request finishes
     * @param url The url for the request
     * @param method The request method to make the request
     * @param extra A string extra that can be accessed from the RestAPIListener when the request is finished
     * @param parameters A vararg of Parameters to send with the request
     * @throws edu.csh.chase.RestfulAPIConnector.InvalidMethodTypeException if the method is not a valid method type defined in RestAPIConnector

     */
    public void execute(RestAPIListener runner, String url, int method, String extra, Parameter... parameters)
        throws InvalidMethodTypeException {

        if (method < 0 || method > 5) {
            throw new InvalidMethodTypeException(method + " is not a valid method. Use RestAPIConnector to get " +
                    "valid methods");
        }
        if (extra != null) {
            runner.setExtra(extra);
        }

        HttpRequestBase request = APIRequestGenerator.generateRequest(method, url, parameters);
        if(request == null){
            return;
        }
        apiExecutorService.execute(new RestAPIRunnable(runner, request));

    }

    /**
     * Executes a request to the baseURL
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner A RestAPIListener to process the returned data when the request finishes
     * @param method The request method to make the request
     * @param parameters A vararg of Parameters to send with the request
     * @throws edu.csh.chase.RestfulAPIConnector.InvalidMethodTypeException if the method is not a valid method type defined in RestAPIConnector
     */
    public void execute(RestAPIListener runner, int method, Parameter... parameters)
        throws InvalidMethodTypeException {
        if (url == null) {
            return; //invalid URL
        }
        execute(runner, this.url, method, parameters);
    }

    /**
     * Executes a request to the requestURL
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner A RestAPIListener to process the returned data when the request finishes
     * @param url The url for the request
     * @param method The request method to make the request
     * @param parameters A vararg of Parameters to send with the request
     * @throws edu.csh.chase.RestfulAPIConnector.InvalidMethodTypeException if the method is not a valid method type defined in RestAPIConnector

     */
    public void execute(RestAPIListener runner, String url, int method, Parameter... parameters)
        throws InvalidMethodTypeException {
        execute(runner, url, method, null, parameters);
    }

    /**
     * Executes a request to the baseURL with the defined endpoint
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner A RestAPIListener to process the returned data when the request finishes
     * @param endPoint The URL Endpoint to make the request against.
     *                 With a base url of http://example.com/api(/)
     *                 and endpoint of "(/)messages/15(/)"
     *                 will become "http://example.com/api/messages/15"
     * @param method The request method to make the request
     * @param parameters A vararg of Parameters to send with the request
     * @throws edu.csh.chase.RestfulAPIConnector.InvalidMethodTypeException if the method is not a valid method type defined in RestAPIConnector

     */
    public void executeWithEndpoint(RestAPIListener runner, String endPoint, int method, Parameter... parameters)
        throws InvalidMethodTypeException {
        executeWithEndpoint(runner, endPoint, method, null, parameters);
    }

    /**
     * Executes a request to the baseURL with the defined endpoint
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner A RestAPIListener to process the returned data when the request finishes
     * @param endPoint The URL Endpoint to make the request against.
     *                 With a base url of http://example.com/api(/)
     *                 and endpoint of "(/)messages/15(/)"
     *                 will become "http://example.com/api/messages/15"
     * @param method The request method to make the request
     * @param extra A string extra that can be accessed from the RestAPIListener when the request is finished
     * @param parameters A vararg of Parameters to send with the request
     * @throws edu.csh.chase.RestfulAPIConnector.InvalidMethodTypeException if the method is not a valid method type defined in RestAPIConnector

     */
    public void executeWithEndpoint(RestAPIListener runner, String endPoint, int method, String extra, Parameter... parameters)
        throws InvalidMethodTypeException{
        if (this.url == null) {
            return;//throw error?
        }
        if (endPoint.charAt(0) == '/') {
            endPoint = endPoint.replaceFirst("/", "");
        }
        if (url.charAt(url.length() - 1) != '/') {
            url += "/";
        }
        execute(runner, url + endPoint, method, extra, parameters);
    }

}
