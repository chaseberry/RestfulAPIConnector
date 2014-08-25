package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONArrayWrapper {

    private JSONArray array;

    private boolean debugMode = false;
    private final String TAG = "JSONARRAYWRAPPER";

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

    public boolean hasKey(String... key) {
        return this.getValidKey(key) != null;
    }

    public String getValidKey(String... keys) {
        for (int z = 0; z < keys.length; z++) {
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
                int index;
                try {
                    index = Integer.parseInt(keys[z]);
                    if (validIndex(index)) {
                        return keys[z];
                    }
                } catch (NumberFormatException ex) {

                }
            }
        }
        return null;
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

    private String validKeySet(String[] keys) throws JSONException {
        JSONObject tempObject = null;
        JSONArray tempArray = array;
        for (int z = 0; z < keys.length; z++) {
            boolean isCurrent = false;
            int index = -1;
            try {
                index = Integer.parseInt(keys[z]);
                isCurrent = true;
            } catch (NumberFormatException ex) {

            }
            if (z == keys.length - 1) {
                if (isCurrent) {
                    if (index >= 0 && index < tempArray.length()) {
                        return createKeySet(keys);
                    } else {
                        return null;
                    }
                } else {
                    if (tempObject == null) {
                        return null;
                    }
                    if (tempObject.has(keys[z]) && !tempObject.isNull(keys[z])) {
                        return createKeySet(keys);
                    } else {
                        return null;
                    }
                }
            } else {
                boolean isNext = false;
                try {
                    Integer.parseInt(keys[z + 1]);
                    isNext = true;
                } catch (NumberFormatException ex) {

                }
                if (z == 0 && !isCurrent) {
                    return null;
                }
                if (isCurrent) {
                    if (tempArray == null) {
                        return null;
                    }
                    if (!(tempArray.length() > index)) {
                        return null;
                    }
                    if (isNext) {
                        tempArray = tempArray.getJSONArray(index);
                        tempObject = null;
                    } else {
                        tempObject = tempArray.getJSONObject(index);
                        tempArray = null;
                    }
                } else {
                    if (tempObject == null) {
                        return null;
                    }
                    if (!tempObject.has(keys[z])) {
                        return null;
                    }
                    if (isNext) {
                        tempArray = tempObject.getJSONArray(keys[z]);
                        tempObject = null;
                    } else {
                        tempObject = tempObject.getJSONObject(keys[z]);
                        tempArray = null;
                    }
                }
            }
        }
        return null;
    }

    public void setDebugMode(boolean debug) {
        debugMode = debug;
    }

    private void debug(String message) {
        if (debugMode) {
            Log.d(TAG, message);
        }
    }

    public Object getObject(int i) throws JSONException {
        return array.get(i);

    }

    public boolean getBoolean(int i) throws JSONException {
        return array.getBoolean(i);
    }

    public int getInt(int i) throws JSONException {
        return array.getInt(i);
    }

    public double getDouble(int i) throws JSONException {
        return array.getDouble(i);
    }

    public long getLong(int i) throws JSONException {
        return array.getLong(i);
    }

    public JSONObject getJSONObject(int i) throws JSONException {
        return array.getJSONObject(i);
    }

    public JSONArray getJSONArray(int i) throws JSONException {
        return array.getJSONArray(i);
    }

    public String getString(int i) throws JSONException {
        return array.getString(i);
    }

    public String[] getStringArray(int i) throws JSONException {
        return JSONArrayToStringArray(array.getJSONArray(i));
    }

    public Object getObject(String key) throws JSONException {
        if (array == null) {
            throw new JSONException("Array does not exist");
        }
        if (key.contains(":")) {
            String[] keys = key.split(":");
            JSONObject tempObject = null;
            JSONArray tempArray = array;
            for (int z = 0; z < keys.length; z++) {
                boolean isCurrent = false;
                int index = -1;
                try {
                    index = Integer.parseInt(keys[z]);
                    isCurrent = true;
                } catch (NumberFormatException ex) {

                }
                if (z == keys.length - 1) {
                    if (isCurrent) {
                        return tempArray.get(index);
                    } else {
                        return tempObject.get(keys[z]);
                    }
                } else {
                    boolean isNext = false;
                    try {
                        Integer.parseInt(keys[z + 1]);
                        isNext = true;
                    } catch (NumberFormatException ex) {

                    }
                    if (z == 0 && !isCurrent) {
                        break;
                    }
                    if (isCurrent) {
                        if (tempArray == null) {
                            break;
                        }
                        if (!(tempArray.length() > index)) {
                            break;
                        }
                        if (isNext) {
                            tempArray = tempArray.getJSONArray(index);
                            tempObject = null;
                        } else {
                            tempObject = tempArray.getJSONObject(index);
                            tempArray = null;
                        }
                    } else {
                        if (tempObject == null) {
                            break;
                        }
                        if (!tempObject.has(keys[z])) {
                            break;
                        }
                        if (isNext) {
                            tempArray = tempObject.getJSONArray(keys[z]);
                            tempObject = null;
                        } else {
                            tempObject = tempObject.getJSONObject(keys[z]);
                            tempArray = null;
                        }
                    }
                }
            }
        } else {
            try {
                int index = Integer.parseInt(key);
                return array.get(index);
            } catch (NumberFormatException e) {
                throw new JSONException(key + " is an invalid array index");
            }
        }
        throw new JSONException(key + " in an invalid multikey");
    }

    public String getString(String key) throws JSONException {
        return String.valueOf(getObject(key));
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        try {
            return (JSONObject) getObject(key);
        } catch (ClassCastException ex) {
            throw new JSONException(key + " does not link to a JSONObject");
        }
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        try {
            return (JSONArray) getObject(key);
        } catch (ClassCastException ex) {
            throw new JSONException(key + " does not link to a JSONArray");
        }
    }

    public String[] getStringArray(String key) throws JSONException {
        try {
            return JSONArrayToStringArray((JSONArray) getObject(key));
        } catch (ClassCastException ex) {
            throw new JSONException(key + " does not link to a String array");
        }
    }

    public int getInt(String key) throws JSONException {
        Object integer = getObject(key);
        try {
            return Integer.parseInt(String.valueOf(integer));
        } catch (NumberFormatException ex) {
            throw new JSONException(integer + " in an invalid int");
        }
    }

    public double getDouble(String key) throws JSONException {
        Object doubleValue = getObject(key);
        try {
            return Double.parseDouble(String.valueOf(doubleValue));
        } catch (NumberFormatException ex) {
            throw new JSONException(doubleValue.toString() + " in an invalid double");
        }
    }

    public long getLong(String key) throws JSONException {
        Object longValue = getObject(key);
        try {
            return Long.parseLong(String.valueOf(longValue));
        } catch (NumberFormatException ex) {
            throw new JSONException(longValue.toString() + " is an invalid long");
        }
    }

    public boolean getBoolean(String key) throws JSONException {
        return Boolean.parseBoolean(String.valueOf(getObject(key)));
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
            return this.getObject(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public JSONArray checkAndGetJSONArray(JSONArray failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getJSONArray(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public String[] checkAndGetStringArray(String[] failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getStringArray(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public JSONObject checkAndGetJSONObject(JSONObject failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getJSONObject(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public String checkAndGetString(String failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getString(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public int checkAndGetInt(int failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getInt(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public double checkAndGetDouble(double failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getDouble(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public long checkAndGetLong(long failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getLong(key);
        } catch (JSONException e) {
            debug(e.toString());
        }
        return failed;
    }

    public boolean checkAndGetBoolean(boolean failed, String... keys) {
        if (array == null) {
            return failed;
        }
        String key = this.getValidKey(keys);
        if (key == null) {
            return failed;
        }
        try {
            return this.getBoolean(key);
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
}