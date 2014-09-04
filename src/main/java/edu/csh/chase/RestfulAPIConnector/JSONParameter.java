package edu.csh.chase.RestfulAPIConnector;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParameter extends Parameter{

    public final int JSONBODY = 0;
    public final int JSONOBJECTPARAMETER = 1;
    public final int JSONARRAYPARAMETER = 2;
    Object value;//either JSONObject or JSONArray;
    private int jsonParameterType = 0;

    public JSONParameter(final String key, String value){
        super(key, value, Parameter.JSONBODY)
    }

    public JSONParameter(final String key, JSONObject value){
        jsonParameterType = JSONOBJECTPARAMETER;
        super(key, "", Parameter.JSONBODY);
        value = this.value;
    }

    public JSONParameter(final String key, JSONArray value){
        jsonParameterType = JSONARRAYPARAMETER;
        super(key, "", Parameter.JSONBODY);
        value = this.value;
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