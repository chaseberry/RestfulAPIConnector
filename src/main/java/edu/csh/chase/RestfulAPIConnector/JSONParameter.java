package edu.csh.chase.RestfulAPIConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.String;

public class JSONParameter extends Parameter {

    public JSONParameter(final String key, String value) {
        super(key, value, ParameterType.JSONBODY);
    }

    public JSONParameter(final String key, JSONObject value) {
        super(key, value, ParameterType.JSONBODY);
    }

    public JSONParameter(final String key, JSONArray value) {
        super(key, value, ParameterType.JSONBODY);
    }

    public JSONParameter(final String key, boolean value) {
        super(key, value, ParameterType.JSONBODY);
    }

    public JSONParameter(final String key, int value) {
        super(key, value, ParameterType.JSONBODY);
    }

    public JSONParameter(final String key, double value) {
        super(key, value, ParameterType.JSONBODY);
    }

    public JSONParameter(final String key, long value) {
        super(key, value, ParameterType.JSONBODY);
    }

}