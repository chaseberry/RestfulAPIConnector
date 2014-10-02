package edu.csh.chase.RestfulAPIConnector.JSONWrapper;

import android.util.Log;

import java.util.Iterator;

/**
 * Created by chase on 9/27/14.
 */
public class JSONWrapperKeyset implements Iterator<String> {

    private String[] keyset;
    private int currentIndex = 0;
    private final String KEYSPLIT = ":";

    /**
     * Creates a keyset of the given key. The key will be split on ':' if it is a multikey.
     * This class is an instance of an Iterator
     *
     * @param key The key or multikey for this keyset
     */
    public JSONWrapperKeyset(String key){
        keyset = parseKeyIntoKeySet(key);
    }

    private String[] parseKeyIntoKeySet(String key){
        return key.split(KEYSPLIT);
    }

    /**
     * Rebuilds the key from the keyset
     *
     * @return The single String key. Multikeys will be reformed with ':'
     */
    public String getKeySet(){
        String key = "";
        for(String s:keyset){
            key += KEYSPLIT + s;
        }
        return key.replaceFirst(KEYSPLIT, "");
    }

    /**
     * A string represention of the keyset
     *
     * @return The string key
     */
    public String toString(){
        return getKeySet();
    }


    /**
     * A boolean whether there is another element in this keySet
     *
     * @return true if there is another element, false otherwise
     */
    @Override
    public boolean hasNext() {
        return currentIndex + 1 < keyset.length;
    }

    /**
     * Get the next String in this keyset or null
     *
     * @return the next String in the keyset or null
     */
    @Override
    public String next() {
        if(currentIndex >= keyset.length){
            return null;
        }
        currentIndex++;
        return keyset[currentIndex-1];
    }


    /**
     * Does nothing
     */
    @Override
    public void remove() {
    }
}
