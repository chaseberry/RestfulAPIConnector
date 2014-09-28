package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;
<<<<<<< HEAD
import java.util.Iterator;
=======
>>>>>>> 9258a3add5ee270c55085104e7f02670ce793f0a

/**
 * Created by chase on 9/27/14.
 */
<<<<<<< HEAD
public class JSONWrapperKeyset implements Iterator<String> {
=======
public class JSONWrapperKeyset {
>>>>>>> 9258a3add5ee270c55085104e7f02670ce793f0a

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

<<<<<<< HEAD
=======
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

>>>>>>> 9258a3add5ee270c55085104e7f02670ce793f0a
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
<<<<<<< HEAD

    @Override
    public boolean hasNext() {
        return currentIndex + 1 < keyset.length;
    }

    @Override
    public String next() {
        if(currentIndex >= keyset.length){
            return null;
        }
        currentIndex++;
        return keyset[currentIndex-1];
    }

    @Override
    public void remove() {
    }
=======
>>>>>>> 9258a3add5ee270c55085104e7f02670ce793f0a
}
