package edu.csh.chase.RestfulAPIConnector;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class APIConnectionManager {

    private String url;
    private static ExecutorService apiExecutorService;

    /**
     * Creates an APIConnectionManager to interface with the RestAPIConnector
     *
     * @param url The base URL for the connection
     */
    public APIConnectionManager(String url, final Thread.UncaughtExceptionHandler threadExceptionHandler) {
        this.url = url;
        if (apiExecutorService == null) {
            apiExecutorService = Executors.newCachedThreadPool(new ThreadFactory() {

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setUncaughtExceptionHandler(threadExceptionHandler);
                    return thread;
                }
            });
        }
    }

    /**
     * Creates an APIConnectionManager to interface with the RestAPIConnector
     */
    public APIConnectionManager(Thread.UncaughtExceptionHandler exceptionHandler) {
        this("", exceptionHandler);
    }

    /**
     * Sets the default URL for requests
     *
     * @param url The base URL for the connection
     */
    public void setDefaultUrl(String url) {
        this.url = url;
    }

    /**
     * Executes a request to the requestURL
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner     A RestAPIListener to process the returned data when the request finishes
     * @param url        The url for the request
     * @param method     The request method to make the request
     * @param extra      A string extra that can be accessed from the RestAPIListener when the request is finished
     * @param parameters A vararg of Parameters to send with the request
     */
    public void execute(RestAPIListener runner, String url, RequestType method, String extra, Parameter... parameters) {
        Request r = new Request(url, method, runner, extra);
        r.addAll(new ArrayList<Parameter>(Arrays.asList(parameters)));
        execute(r);
    }

    /**
     * Executes a request to the requestURL
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param request A request object containing all required information to make the request
     */
    public void execute(Request request) {
        apiExecutorService.execute(new RestAPIRunnable(request));
    }

    /**
     * Executes a request to the baseURL
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner     A RestAPIListener to process the returned data when the request finishes
     * @param method     The request method to make the request
     * @param parameters A vararg of Parameters to send with the request
     */
    public void execute(RestAPIListener runner, RequestType method, Parameter... parameters) {
        if (url == null) {
            return; //invalid URL
        }
        execute(runner, this.url, method, parameters);
    }

    /**
     * Executes a request to the requestURL
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner     A RestAPIListener to process the returned data when the request finishes
     * @param url        The url for the request
     * @param method     The request method to make the request
     * @param parameters A vararg of Parameters to send with the request
     */
    public void execute(RestAPIListener runner, String url, RequestType method, Parameter... parameters) {
        execute(runner, url, method, null, parameters);
    }

    /**
     * Executes a request to the baseURL with the defined endpoint
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner     A RestAPIListener to process the returned data when the request finishes
     * @param endPoint   The URL Endpoint to make the request against.
     *                   With a base url of http://example.com/api(/)
     *                   and endpoint of "(/)messages/15(/)"
     *                   will become "http://example.com/api/messages/15"
     * @param method     The request method to make the request
     * @param parameters A vararg of Parameters to send with the request
     */
    public void executeWithEndpoint(RestAPIListener runner, String endPoint, RequestType method, Parameter... parameters) {
        executeWithEndpoint(runner, endPoint, method, null, parameters);
    }

    /**
     * Executes a request to the baseURL with the defined endpoint
     * Will call the passed RestAPIListener success or failure depending on status code
     *
     * @param runner     A RestAPIListener to process the returned data when the request finishes
     * @param endPoint   The URL Endpoint to make the request against.
     *                   With a base url of http://example.com/api(/)
     *                   and endpoint of "(/)messages/15(/)"
     *                   will become "http://example.com/api/messages/15"
     * @param method     The request method to make the request
     * @param extra      A string extra that can be accessed from the RestAPIListener when the request is finished
     * @param parameters A vararg of Parameters to send with the request
     */
    public void executeWithEndpoint(RestAPIListener runner, String endPoint, RequestType method,
                                    String extra, Parameter... parameters) {
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

    /**
     * Creates a request object which can have changed parameters as needed.
     *
     * @param runner     A RestAPIListener to process the returned data when the request finishes
     * @param endPoint   The URL Endpoint to make the request against.
     *                   With a base url of http://example.com/api(/)
     *                   and endpoint of "(/)messages/15(/)"
     *                   will become "http://example.com/api/messages/15"
     * @param method     The request method to make the request
     * @param extra      A string extra that can be accessed from the RestAPIListener when the request is finished
     * @param parameters A vararg of Parameters to send with the request
     */
    public Request requestWithEndpoint(RestAPIListener runner, String endPoint, RequestType method,
                                       String extra, Parameter... parameters) {
        if (endPoint.charAt(0) == '/') {
            endPoint = endPoint.replaceFirst("/", "");
        }
        if (url.charAt(url.length() - 1) != '/') {
            url += "/";
        }
        Request r = new Request(url + endPoint, method, runner, extra);
        r.addAll(new ArrayList<Parameter>(Arrays.asList(parameters)));
        return r;
    }

}
