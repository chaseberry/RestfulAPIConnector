package edu.csh.chase.RestfulAPIConnector;


import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONWrapper;

/**
 * Created by Chase on 1/27/14.
 */
public abstract class RestAPIListener {

    protected JSONWrapper data;
    protected String extra;
    protected int statusCode;

    /**
     * The success function
     * This function is called when an request returns with a status code of 200 - 299
     *
     * @param data the returned data from the request
     * @param statusCode the status code from the request
     */
    public abstract void success(JSONWrapper data, final int statusCode);

    /**
     * The failure function
     * This function is called when a request returns with a status code not between 200 and 299
     *
     * @param statusCode the status code from the request
     */
    public abstract void failure(final int statusCode);


    public void start() {
        if(statusCode >= 200 && statusCode <= 299){
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
