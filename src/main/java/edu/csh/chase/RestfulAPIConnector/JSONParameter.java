package edu.csh.chase.RestfulAPIConnector;

public class JSONParameter extends Parameter{

    public JSONParameter(final String key, String value){
        super(key, value, Parameter.JSONBODY)
    }

}