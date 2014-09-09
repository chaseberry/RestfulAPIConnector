package edu.csh.chase.RestfulAPIConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.Double;
import java.lang.Integer;
import java.lang.String;

public class JSONParameter extends Parameter{

    Object value;//either JSONObject or JSONArray;

    public JSONParameter(final String key, String value) throws RestAPIParemeterException{
        super(key, value, Parameter.JSONBODY);
    }

    public JSONParameter(final String key, JSONObject value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        this.value = value;
    }

    public JSONParameter(final String key, JSONArray value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        this.value = value;
    }

    public JSONParameter(final String key, boolean value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        this.value = new Boolean(value);
    }

    public JSONParameter(final String key, int value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        this.value = new Integer(value);
    }

    public JSONParameter(final String key, double value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        this.value = new Double(value);
    }

    public JSONParameter(final String key, long value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        this.value = new Long(value);
    }

    public int getJsonParameterType(){
        return jsonParameterType;
    }

    public Object getObjectValue(){
        return value;
    }

}