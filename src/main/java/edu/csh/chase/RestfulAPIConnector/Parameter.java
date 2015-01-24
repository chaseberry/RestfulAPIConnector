package edu.csh.chase.RestfulAPIConnector;

/**
 * Created by chase on 7/15/14.
 */
public abstract class Parameter {

    private final String key;
    private Object value;
    private final ParameterType methodType;

    /**
     * Defines a new Parameter to send with a URL Request
     *
     * @param key        A String key for the parameter
     * @param value      A String value for the parameter
     * @param methodType An int definition of the method type
     *                   Parameter.URLPARAMETER - A parameter added to the end of the url as ?key=value
     *                   Parameter.FORMDATA - A paramter in a POST form. Only works for POST requests
     *                   Parameter.HEADER - A URL Encoded header
     *                   Parameter.JSONBODY - Raw body in JSON Format
     */
    protected Parameter(final String key, Object value, ParameterType methodType) {
        this.key = key;
        this.value = value;
        this.methodType = methodType;
    }

    /**
     * @return They key associted with this Parameter
     */
    public String getKey() {
        return key;
    }

    /**
     * @return The value associted with this Parameter
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of this Parameter
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return The int method type
     */
    public ParameterType getMethodType() {
        return methodType;
    }

    /**
     * @return A string defination of this Parameter
     */
    public String toString() {
        return "Paremter of type " + getParemeterType() + " with data as key:value, " + key + ":" + value;
    }

    /**
     * @return A string representation of the method type
     */
    public String getParemeterType() {
        switch (methodType) {
            case URL:
                return "URL";
            case FORMDATA:
                return "Post Form Data";
            case HEADER:
                return "Header";
            case JSONBODY:
                return "Raw JSON Body";
        }
        return "";
    }
}
