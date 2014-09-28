package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectWrapper extends JSONWrapper {

    private JSONObject object;

    public JSONObjectWrapper(JSONObject o) {
        this.object = o;
    }

    public JSONObjectWrapper(String s) throws JSONException {
        this.object = new JSONObject(s);
    }

    public String getValidKey(String... keys) {
        if(object == null){
            return null;
        }
        for (String key : keys) {
            debug("Trying key: " + key);
            try {
                Object data = parseKey(new JSONWrapperKeyset(key), object);
                if (data != null) {
                    return key;
                }
            } catch (JSONException e) {
                debug(e.getMessage());
            }
        }
        return null;
    }

    public Object getObject(String key) throws JSONException {
        if (object == null) {
            throw new JSONException("JSONObject is null");
        }
        JSONWrapperKeyset keySet = new JSONWrapperKeyset(key);
        return parseKey(keySet, object);
    }

    public Object checkAndGetObject(Object failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey == null) {
                return failed;
            }
            Object data = this.getObject(validKey);
            if (data == null) {
                return failed;
            }
            return data;
        } catch (JSONException e) {
            debug(e.getMessage());
        }
        return failed;
    }

    public int length() {
        if (object == null) {
            return -1;
        }
        return object.length();
    }

    public boolean isValid() {
        return object != null;
    }

    public JSONObject getJSONObject() {
        return this.object;
    }

    public String toString() {
        return object.toString();
    }
}
