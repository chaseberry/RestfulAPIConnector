package edu.csh.chase.RestfulAPIConnector;

/**
 * Created by chase on 7/15/14.
 */
public class Parameter {

    public static final int URLPARAMETER = 0;
    public static final int FORMDATA = 1;
    public static final int HEADER = 2;
    public static final int JSONBODY = 3;

    private final String key;
    private String value;
    private final int methodType;

    /**
     * Defines a new Parameter to send with a URL Request
     *
     * @param key A String key for the parameter
     * @param value A String value for the parameter
     * @param methodType An int definition of the method type
     *                   Parameter.URLPARAMETER - A parameter added to the end of the url as ?key=value
     *                   Parameter.FORMDATA - A paramter in a POST form. Only works for POST requests
     *                   Parameter.HEADER - A URL Encoded header
     *                   Parameter.JSONBODY - Raw body in JSON Format
     * @throws RestAPIParemeterException If you pass an invalid Parameter type
     */
    public Parameter(final String key, String value, int methodType) throws RestAPIParemeterException{
        if(methodType < 0 || methodType > 3){
            throw new RestAPIParemeterException(methodType + " is not a valid parameter type.");
        }
        this.key = key;
        this.value = value;
        this.methodType = methodType;
    }


    /**
     *
     * @return They key associted with this Parameter
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @return The value associted with this Parameter
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of this Parameter
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return The int method type
     */
    public int getMethodType() {
        return methodType;
    }

    /**
     *
     * @return A string defination of this Parameter
     */
    public String toString(){
        return "Paremter of type " + getParemeterType() + " with data as key:value, " + key + ":" + value;
    }

    /**
     *
     * @return A string representation of the method type
     */
    public String getParemeterType() {
        switch (methodType) {
            case 0: return "URL";
            case 1: return "Post Form Data";
            case 2: return "Header";
            case 3: return "Raw JSON Body";
        }
        return "";
    }
}
