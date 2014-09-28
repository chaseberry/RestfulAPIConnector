package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by chase on 9/27/14.
 */
public class JSONWrapperKeyset {

    private String[] keyset;
    private int currentIndex = 0;
    private final String KEYSPLIT = ":";

    private final String TAG = "JSONWRAPPERKEYSET";
    private boolean debugMode = false;

    public void setDebugMode(boolean debug) {
        debugMode = debug;
    }

    private void debug(String message) {
        if (debugMode) {
            Log.d(TAG, message);
        }
    }

    public JSONWrapperKeyset(String key){
        keyset = parseKeyIntoKeySet(key);
    }

    private String[] parseKeyIntoKeySet(String key){
        return key.split(KEYSPLIT);
    }

    public String getCurrentKey(){
        if(currentIndex >= keyset.length){
            return null;
        }
        currentIndex++;
        return keyset[currentIndex-1];
    }

    public boolean hasNextKey(){
        return currentIndex + 1 < keyset.length;
    }

    public String getKeySet(){
        String key = "";
        for(String s:keyset){
            key += ":" + s;
        }
        return key.replaceFirst(":", "");
    }

    public String toString(){
        return getKeySet();
    }
}
