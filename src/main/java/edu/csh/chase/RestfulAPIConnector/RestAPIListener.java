package edu.csh.chase.RestfulAPIConnector;


import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONWrapper;

/**
 * Created by Chase on 1/27/14.
 */
public abstract class RestAPIListener {

    private JSONWrapper data;
    private String extra;
    private int statusCode;

    /**
     * The success function
     * This function is called when an request returns with a status code of 200 - 299
     *
     * @param data
     * @param statusCode
     */
    public abstract void success(JSONWrapper data, final int statusCode);

    /**
     * The failure function
     * This function is called when a request returns with a status code not between 200 and 299
     *
     * @param statusCode
     */
    public abstract void failure(final int statusCode);

    
    public void start() {
        if(statusCode >= 200 || statusCode <= 299){
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
