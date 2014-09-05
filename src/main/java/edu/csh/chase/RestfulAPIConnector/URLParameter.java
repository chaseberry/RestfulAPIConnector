package edu.csh.chase.RestfulAPIConnector;

public class URLParameter extends Parameter{

    public URLParameter(final String key, String value) throws RestAPIParemeterException {
        super(key, value, Parameter.URLPARAMETER);
    }



}