package edu.csh.chase.RestfulAPIConnector;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParameter extends Parameter{

    public static final int JSONSTRINGBODY = 0;
    public static final int JSONOBJECTPARAMETER = 1;
    public static final int JSONARRAYPARAMETER = 2;
    Object value;//either JSONObject or JSONArray;
    private int jsonParameterType = 0;

    public JSONParameter(final String key, String value) throws RestAPIParemeterException{
        super(key, value, Parameter.JSONBODY);
    }

    public JSONParameter(final String key, JSONObject value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        jsonParameterType = JSONOBJECTPARAMETER;
        this.value = value;
    }

    public JSONParameter(final String key, JSONArray value) throws RestAPIParemeterException{
        super(key, "", Parameter.JSONBODY);
        jsonParameterType = JSONARRAYPARAMETER;
        this.value = value;
    }

    public int getJsonParameterType(){
        return jsonParameterType;
    }

    public JSONArray getJsonArrayValue(){
        return (JSONArray) value;
    }

    public JSONObject getJsonObjectValue(){
        return (JSONObject) value;
    }

}