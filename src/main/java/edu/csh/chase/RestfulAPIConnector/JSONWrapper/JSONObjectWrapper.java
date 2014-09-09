package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectWrapper {
    private JSONObject object;

    private boolean debugMode = false;
    private final String TAG = "JSONOBJECTWRAPPER";

    public JSONObjectWrapper(JSONObject o) {
        this.object = o;
    }

    public JSONObjectWrapper(String s) throws JSONException {
        this.object = new JSONObject(s);
    }

    public void setDebugMode(boolean debug) {
        debugMode = debug;
    }

    private void debug(String message) {
        if (debugMode) {
            Log.d(TAG, message);
        }
    }

    public boolean hasKey(String... keys) {
        String key = this.getValidKey(keys);
        return key != null;
    }

    public String getValidKey(String... keys) {
        for (int z = 0; z < keys.length; z++) {
            debug("Trying key: " + keys[z]);
            if (keys[z].contains(":")) {
                String[] keyset = parseKeySet(keys[z]);
                try {
                    String validKey = validKeySet(keyset);
                    if (validKey != null) {
                        return validKey;
                    }
                } catch (JSONException ex) {

                }
            } else {
                if (object.has(keys[z]) && !object.isNull(keys[z])) {
                    debug(keys[z] + " was a valid key");
                    return keys[z];
                }
            }
        }
        return null;
    }

    private String validKeySet(String[] keyset) throws JSONException {
        JSONObject temp = object;
        JSONArray tempArray = null;
        for (int z = 0; z < keyset.length; z++) {
            debug("checking key number: " + (z + 1) + " with value of: " + keyset[z]);
            if (z == keyset.length - 1) {
                if (tempArray != null) {
                    int index = Integer.parseInt(keyset[z]);
                    if (index < tempArray.length() && !String.valueOf(tempArray.get(index)).isEmpty()) {
                        return createKeySet(keyset);
                    } else {
                        debug("Array index failed with invalid data");
                    }
                } else {
                    if (temp.has(keyset[z]) && !temp.isNull(keyset[z])) {
                        return createKeySet(keyset);
                    } else {
                        debug("Key failed with invalid value");
                    }
                }
            } else {
                boolean isNextArray = true;
                try {
                    Integer.parseInt(keyset[z + 1]);
                } catch (NumberFormatException e) {
                    isNextArray = false;
                }
                if (isNextArray) {
                    if (tempArray == null) {
                        if (!temp.has(keyset[z])) {
                            return null;
                        }
                        tempArray = temp.getJSONArray(keyset[z]);
                    } else {
                        tempArray = tempArray.getJSONArray(Integer.parseInt(keyset[z]));
                    }
                } else {
                    if (tempArray != null) {
                        temp = tempArray.getJSONObject(Integer.parseInt(keyset[z]));
                        tempArray = null;
                    } else {
                        if (!temp.has(keyset[z])) {
                            return null;
                        }
                        temp = temp.getJSONObject(keyset[z]);
                    }
                }
            }
        }
        return null;
    }

    public Object getObject(String key) throws JSONException {
        if (object == null) {
            throw new JSONException("JSONObject is null");
        }
        if (key.contains(":")) {
            String[] keyset = parseKeySet(key);
            JSONObject tempObject = object;
            JSONArray tempArray = null;
            for (int z = 0; z < keyset.length; z++) {
                if (z == keyset.length - 1) {
                    if (tempArray != null) {
                        int index = Integer.parseInt(keyset[z]);
                        return tempArray.get(index);
                    } else {
                        return tempObject.get(keyset[z]);
                    }
                } else {
                    boolean isNextArray = true;
                    try {
                        Integer.parseInt(keyset[z + 1]);
                    } catch (NumberFormatException e) {
                        isNextArray = false;
                    }
                    if (isNextArray) {
                        if (tempArray == null) {
                            tempArray = tempObject.getJSONArray(keyset[z]);
                        } else {
                            tempArray = tempObject.getJSONArray(keyset[z]);
                        }
                    } else {
                        if (tempArray != null) {
                            tempObject = tempArray.getJSONObject(Integer.parseInt(keyset[z]));
                            tempArray = null;
                        } else {
                            tempObject = tempObject.getJSONObject(keyset[z]);
                        }
                    }
                }
            }
        } else {
            return object.get(key);
        }
        throw new JSONException("Invalid multikey:" + key);
    }//TODO re-write this function

    public String getString(String key) throws JSONException {
        Object string = this.getObject(key);
        if (string == null) {
            throw new JSONException("Invalid key:" + key);
        }
        return String.valueOf(string);
    }

    public boolean getBoolean(String key) throws JSONException {
        Object bool = this.getObject(key);
        if (bool == null) {
            throw new JSONException("Invalid key:" + key);
        }
        return Boolean.parseBoolean(String.valueOf(bool));
    }

    public int getInt(String key) throws JSONException {
        Object integer = this.getObject(key);
        if (integer == null) {
            throw new JSONException("Invalid key:" + key);
        }
        try {
            return Integer.parseInt(String.valueOf(integer));
        } catch (NumberFormatException ex) {
            throw new JSONException(integer + " in an invalid int");
        }
    }

    public double getDouble(String key) throws JSONException {
        Object doubleValue = this.getObject(key);
        if (doubleValue == null) {
            throw new JSONException("Invalid key:" + key);
        }
        try {
            return Double.parseDouble(String.valueOf(doubleValue));
        } catch (NumberFormatException ex) {
            throw new JSONException(doubleValue + " is an invalid double");
        }
    }

    public long getLong(String key) throws JSONException {
        Object longValue = this.getObject(key);
        if (longValue == null) {
            throw new JSONException("Invalid key:" + key);
        }
        try {
            return Long.parseLong(String.valueOf(longValue));
        } catch (NumberFormatException ex) {
            throw new JSONException(longValue.toString() + " is an invalid long");
        }
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        Object jsonArray = this.getObject(key);
        if (jsonArray == null) {
            throw new JSONException("Invalid key: " + key);
        }
        try {
            return (JSONArray) jsonArray;
        } catch (ClassCastException ex) {
            throw new JSONException(key + " does not link to a JSONArray");
        }
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        Object jsonObject = this.getObject(key);
        if (jsonObject == null) {
            throw new JSONException("Invalid key: " + key);
        }
        try {
            return (JSONObject) jsonObject;
        } catch (ClassCastException ex) {
            throw new JSONException(key + " does not link to a JSONArray");
        }
    }

    public String[] getStringArray(String key) throws JSONException {
        JSONArray jsonArray = this.getJSONArray(key);
        if (jsonArray == null) {
            throw new JSONException("Invalid key:" + key);
        }
        return JSONArrayToStringArray(jsonArray);
    }

    private String[] JSONArrayToStringArray(JSONArray array) {
        String[] data = new String[array.length()];
        for (int z = 0; z < array.length(); z++) {
            try {
                data[z] = String.valueOf(array.get(z));
            } catch (JSONException e) {
            }
        }
        return data;
    }

    private String createKeySet(String[] keys) {
        String key = "";
        for (int z = 0; z < keys.length; z++) {
            if (z != 0) {
                key += ":";
            }
            key += keys[z];
        }
        debug("Created keyset: " + key);
        return key;
    }

    private String[] parseKeySet(String key) {
        String[] data = key.split(":");
        return data;
    }

    public Object checkAndGetObject(Object failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return this.getObject(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public JSONObject checkAndGetJSONObject(JSONObject failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return this.getJSONObject(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public long checkAndGetLong(long failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return this.getLong(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public String checkAndGetString(String failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return getString(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public double checkAndGetDouble(double failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return getDouble(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public String[] checkAndGetStringArray(String[] failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return getStringArray(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public JSONArray checkAndGetJSONArray(JSONArray failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return getJSONArray(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public int checkAndGetInt(int failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return getInt(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public boolean checkAndGetBoolean(boolean failed, String... keys) {
        if (object == null) {
            return failed;
        }
        try {
            String validKey = getValidKey(keys);
            if (validKey != null) {
                return getBoolean(validKey);
            }
        } catch (JSONException e) {
            debug(e.toString());
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
}
