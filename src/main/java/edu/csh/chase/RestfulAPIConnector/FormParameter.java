package edu.csh.chase.RestfulAPIConnector;

public class FormParameter extends Parameter {

    public FormParameter(final String key, String value) {
        super(key, value, ParameterType.FORMDATA);
    }


}