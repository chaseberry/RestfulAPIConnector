package edu.csh.chase.RestfulAPIConnector;

public class FormParameter extends Parameter{

    public FormParameter(final String key, String value) throws RestAPIParemeterException{
        super(key, value, Parameter.FORMDATA);
    }


}