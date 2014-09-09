package edu.csh.chase.RestfulAPIConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.Double;
import java.lang.Integer;
import java.lang.String;

public class JSONParameter extends Parameter{

    public static final int JSONSTRINGBODY = 0;
    public static final int JSONOBJECTPARAMETER = 1;
    public static final int JSONARRAYPARAMETER = 2;
    public static final int JSONINTPARAMETER = 3;
    public static final int JSONBOOLEANPARAMETER = 4;
    public static final int JSONDOUBLEPARAMETER = 5;
    public static final int JSONLONGPARAMETER = 6;
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

    public JSONParameter(final String key, boolean value){
        super(key, "", Parameter.JSONBODY);
        this.value = new Boolean(value);
        jsonParameterType = JSONBOOLEANPARAMETER;
    }

    public JSONParameter(final String key, int value){
        super(key, "", Parameter.JSONBODY);
        this.value = new Integer(value);
        jsonParameterType = JSONINTPARAMETER;
    }

    public JSONParameter(final String key, double value){
        super(key, "", Parameter.JSONBODY);
        this.value = new Double(value);
        jsonParameterType = JSONDOUBLEPARAMETER;
    }

    public JSONParameter(final String key, long value){
        super(key, "", Parameter.JSONBODY);
        this.value = new Long(value);
        jsonParameterType = JSONLONGPARAMETER;
    }

    public int getJsonParameterType(){
        return jsonParameterType;
    }

    public Object getObjectValue(){
        return value;
    }

}