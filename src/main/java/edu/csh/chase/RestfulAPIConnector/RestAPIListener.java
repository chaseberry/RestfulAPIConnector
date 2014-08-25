package edu.csh.chase.RestfulAPIConnector;


import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONWrapper;

/**
 * Created by Chase on 1/27/14.
 */
public abstract class RestAPIListener {

    private JSONWrapper data;
    private String extra;
    private int statusCode;

    public abstract void success(JSONWrapper data, final int statusCode);

    public abstract void failure(final int statusCode);

    public void start() {
        if(statusCode == 200 || statusCode == 201 || statusCode == 204){
            success(data, statusCode);
        }else{
            failure(statusCode);
        }
    }

    public void setData(JSONWrapper data) {
        this.data = data;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
