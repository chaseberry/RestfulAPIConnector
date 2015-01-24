package edu.csh.chase.RestfulAPIConnector;

public class URLParameter extends Parameter {

    public URLParameter(final String key, String value) {
        super(key, value, ParameterType.URL);
    }


}