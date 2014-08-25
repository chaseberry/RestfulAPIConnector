package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWrapper {

    private JSONException noObjectOrArrayExccetion = new JSONException("No JSONObjecr or JSONArray");
    private JSONException noArrayExcetion = new JSONException("No JSONArray");

    private JSONObjectWrapper object;
    private JSONArrayWrapper array;

    private boolean debugMode = false;
    private final String TAG = "JSONWRAPPER";

    public JSONWrapper(JSONObject ob) {
        object = new JSONObjectWrapper(ob);
    }

    public JSONWrapper(JSONArray ar) {
        array = new JSONArrayWrapper(ar);
    }

    public JSONWrapper(String data) throws JSONException {
        try {
            object = new JSONObjectWrapper(data);
        } catch (JSONException e) {
            object = null;
            try {
                array = new JSONArrayWrapper(data);
            } catch (JSONException ex) {
                array = null;
                throw new JSONException("Must provide a valid JSONArray or JSONObject");
            }
        }
    }

    public JSONObjectWrapper getJSONObjectWrapper() {
        return object;
    }

    public JSONArrayWrapper getJSONArrayWrapper() {
        return array;
    }

    public JSONObject getJSONObject() {
        return object.getJSONObject();
    }

    public JSONArray getJSONArray() {
        return array.getJSONArray();
    }
    //***************************Debug functions****************************************************

    public void setDebugMode(boolean debug) {
        debugMode = debug;
        if (object != null) {
            object.setDebugMode(debug);
        } else if (array != null) {
            array.setDebugMode(debug);
        }
    }

    private void debug(String message) {
        if (debugMode) {
            Log.d(TAG, message);
        }
    }

    //***************************Valid Stuff********************************************************
    public String getValidKey(String... keys) {
        if (object != null) {
            return object.getValidKey(keys);
        } else if (array != null) {
            return array.getValidKey(keys);
        }
        return null;
    }

    public boolean hasKey(String... keys) {
        if (object != null) {
            return object.hasKey(keys);
        } else if (array != null) {
            return array.hasKey(keys);
        }
        return false;
    }


    //****************************Getters***********************************************************

    public Object getObject(String key) throws JSONException {
        if (object != null) {
            return object.getObject(key);
        } else if (array != null) {
            return array.getObject(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public Object getObject(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getObject(i);
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        if (object != null) {
            return object.getJSONObject(key);
        } else if (array != null) {
            return array.getJSONObject(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public JSONObject getJSONObject(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getJSONObject(i);
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        if (object != null) {
            return object.getJSONArray(key);
        } else if (array != null) {
            return array.getJSONArray(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public JSONArray getJSONArray(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getJSONArray(i);
    }

    public String[] getStringArray(String key) throws JSONException {
        if (object != null) {
            return object.getStringArray(key);
        } else if (array != null) {
            return array.getStringArray(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public String[] getStringArray(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getStringArray(i);
    }

    public String getString(String key) throws JSONException {
        if (object != null) {
            return object.getString(key);
        } else if (array != null) {
            return array.getString(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public String getString(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getString(i);
    }

    public int getInt(String key) throws JSONException {
        if (object != null) {
            return object.getInt(key);
        } else if (array != null) {
            return array.getInt(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public int getInt(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getInt(i);
    }

    public double getDouble(String key) throws JSONException {
        if (object != null) {
            return object.getDouble(key);
        } else if (array != null) {
            return array.getDouble(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public double getDouble(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getDouble(i);
    }

    public long getLong(String key) throws JSONException {
        if (object != null) {
            return object.getLong(key);
        } else if (array != null) {
            return array.getLong(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public long getLong(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getLong(i);
    }

    public boolean getBoolean(String key) throws JSONException {
        if (object != null) {
            return object.getBoolean(key);
        } else if (array != null) {
            return array.getBoolean(key);
        }
        throw noObjectOrArrayExccetion;
    }

    public boolean getBolean(int i) throws JSONException {
        if (array == null) {
            throw noArrayExcetion;
        }
        return array.getBoolean(i);
    }

    //************************checkAndGetters*******************************************************
    public Object checkAndGetObject(Object failed, String... keys) {
        if (object != null) {
            return object.checkAndGetObject(failed, keys);
        } else if (array != null) {
            return array.checkAndGetObject(failed, keys);
        }
        return failed;
    }

    public JSONObject checkAndGetJSONObject(JSONObject failed, String... keys) {
        if (object != null) {
            return object.checkAndGetJSONObject(failed, keys);
        } else if (array != null) {
            return array.checkAndGetJSONObject(failed, keys);
        }
        return failed;
    }

    public JSONArray checkAndGetJSONArray(JSONArray failed, String... keys) {
        if (object != null) {
            return object.checkAndGetJSONArray(failed, keys);
        } else if (array != null) {
            return array.checkAndGetJSONArray(failed, keys);
        }
        return failed;
    }

    public String[] checkAndGetStringArray(String[] failed, String... keys) {
        if (object != null) {
            return object.checkAndGetStringArray(failed, keys);
        } else if (array != null) {
            return array.checkAndGetStringArray(failed, keys);
        }
        return failed;
    }

    public String checkAndGetString(String failed, String... keys) {
        if (object != null) {
            return object.checkAndGetString(failed, keys);
        } else if (array != null) {
            return array.checkAndGetString(failed, keys);
        }
        return failed;
    }

    public int checkAndGetInt(int failed, String... keys) {
        if (object != null) {
            return object.checkAndGetInt(failed, keys);
        } else if (array != null) {
            return array.checkAndGetInt(failed, keys);
        }
        return failed;
    }

    public double checkAndGetDouble(double failed, String... keys) {
        if (object != null) {
            return object.checkAndGetDouble(failed, keys);
        } else if (array != null) {
            return array.checkAndGetDouble(failed, keys);
        }
        return failed;
    }

    public long checkAndGetLong(long failed, String... keys) {
        if (object != null) {
            return object.checkAndGetLong(failed, keys);
        } else if (array != null) {
            return array.checkAndGetLong(failed, keys);
        }
        return failed;
    }

    public boolean checkAndGetBoolean(boolean failed, String... keys) {
        if (object != null) {
            return object.checkAndGetBoolean(failed, keys);
        } else if (array != null) {
            return array.checkAndGetBoolean(failed, keys);
        }
        return failed;
    }

    public int length() {
        if (object != null) {
            return object.length();
        } else if (array != null) {
            return array.length();
        }
        return -1;
    }

    public boolean validWrapper() {
        if (object != null) {
            return object.isValid();
        } else if (array != null) {
            return array.isValid();
        }
        return false;
    }

}