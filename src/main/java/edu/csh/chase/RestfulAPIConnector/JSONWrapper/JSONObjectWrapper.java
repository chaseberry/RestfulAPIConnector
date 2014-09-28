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

<<<<<<< HEAD
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
=======
    public String getValidKey(String... keys){
        for(String key : keys){
            debug("Trying key: " + key);
            try {
                Object data = parseKey(new JSONWrapperKeyset(key), object);
                if(data != null){
                    return key;
                }
            } catch (JSONException e) {
                e.printStackTrace();
>>>>>>> 9258a3add5ee270c55085104e7f02670ce793f0a
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
<<<<<<< HEAD
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
=======
            if (validKey != null) {
                Object data = this.getObject(validKey);
                if(data == null){
                    return failed;
                }
                return data;
            }
        } catch (JSONException e) {
            debug(e.toString());
>>>>>>> 9258a3add5ee270c55085104e7f02670ce793f0a
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

<<<<<<< HEAD
    public String toString() {
=======
    public String toString(){
>>>>>>> 9258a3add5ee270c55085104e7f02670ce793f0a
        return object.toString();
    }
}
