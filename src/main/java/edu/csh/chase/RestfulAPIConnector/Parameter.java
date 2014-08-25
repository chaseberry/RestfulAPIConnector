package edu.csh.chase.RestfulAPIConnector;

/**
 * Created by chase on 7/15/14.
 */
public class Parameter {

    public static final int URLPARAMETER = 0;//get
    public static final int FORMDATA = 1;//post
    public static final int HEADER = 2;
    public static final int JSONBODY = 3;

    private final String key;
    private String value;
    private final int methodType;

    public Parameter(final String key, String value, int methodType) throws RestAPIParemeterException{
        if(methodType < 0 || methodType > 3){
            throw new RestAPIParemeterException(methodType + " is not a valid parameter type.");
        }
        this.key = key;
        this.value = value;
        this.methodType = methodType;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getMethodType() {
        return methodType;
    }

    public String toString(){
        return "Paremter of type " + getParemeterType() + " with data as key:value, " + key + ":" + value;
    }

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
