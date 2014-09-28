package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONArrayWrapper extends JSONWrapper {

    private JSONArray array;

    public JSONArrayWrapper(JSONArray o) {
        this.array = o;
    }

    public JSONArrayWrapper(String s) throws JSONException {
        this.array = new JSONArray(s);
    }

    public boolean validIndex(int i) {
        if (array == null) {
            return false;
        }
        return i >= 0 && i < array.length();
    }

    public String getValidKey(String... keys) {
        for (String key : keys) {
            try {
                Object data = parseKey(new JSONWrapperKeyset(key), array);
                if (data != null) {
                    return key;
                }
            } catch (JSONException e) {

            }
        }
        return null;
    }

    public Object getObject(int i) throws JSONException {
        return array.get(i);
    }

    public Object getObject(String key) throws JSONException {
        if (array == null) {
            throw new JSONException("JSONArray is null");
        }
        JSONWrapperKeyset keySet = new JSONWrapperKeyset(key);
        return parseKey(keySet, array);
    }

    public Object checkAndGetObject(Object failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            Object data = this.getObject(key);
            if (data == null) {
                return failed;
            }
            return data;
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public int length() {
        if (array == null) {
            return -1;
        }
        return array.length();
    }

    public boolean isValid() {
        return array != null;
    }

    public JSONArray getJSONArray() {
        return array;
    }

    public String toString() {
        return array.toString();
    }

}