package edu.csh.chase.RestfulAPIConnector;

public class HeaderParameter extends Parameter{

    public HeaderParameter(final String key, String value) throws RestAPIParemeterException{
        super(key, value, Parameter.HEADER);
    }

}
