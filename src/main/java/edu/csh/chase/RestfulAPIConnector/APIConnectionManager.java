package edu.csh.chase.RestfulAPIConnector;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONWrapper;

public class APIConnectionManager {

    private Context parent;
    private String url;

    public APIConnectionManager(Context c, String url) {
        parent = c;
        this.url = url;
    }

    public APIConnectionManager(Context c) {
        parent = c;
    }

    public void setDefaultUrl(String url) {
        this.url = url;
    }

    public void execute(RestAPIListener runner, String url, int method, String extra, Parameter... parameters)
            throws InvalidMethodTypeException {
        if (!isNetworkOnline()) {
            return;
        }

        if (method < 0 || method > 5) {
            throw new InvalidMethodTypeException(method + " is not a valid method. Use RestAPIConnector to get " +
                    "valid methods");
        }

        RestAPIConnector connector = new RestAPIConnector(runner, url, method, parameters);
        if (extra != null) {
            runner.setExtra(extra);
        }
        connector.execute();
    }

    public void execute(RestAPIListener runner, int method, Parameter... parameters)
            throws InvalidMethodTypeException {
        if (url == null) {
            return; //invalid URL
        }
        execute(runner, this.url, method, parameters);
    }

    public void execute(RestAPIListener runner, String url, int method, Parameter... parameters)
            throws InvalidMethodTypeException {
        execute(runner, url, method, null, parameters);
    }

    public void executeWithEndpoint(RestAPIListener runner, String endPoint, int method, Parameter... parameters)
            throws InvalidMethodTypeException {
        executeWithEndpoint(runner, endPoint, method, null, parameters);
    }

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

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) parent.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }

}
