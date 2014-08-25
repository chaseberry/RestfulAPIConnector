RestfulAPIConnector
===================

A simple Android library for making requests to servers using GET, POST, PUT, DELTE, HEAD, and OPTIONS.

To-do
=====
- [ ] Add Javadocs
- [ ] Change how errors work 
- [ ] Add customization for defining success and failure
- [ ] Improve regex on executeWithEndpoint functions


Usage
=====
There are four main classes needed to use the library: APIConnectionManager, Parameter, RestAPIListener, and JSONWrapper



```Java
APIConnectionManager apiConnection = new APIConnectionManager(someContext);
Parameter email, password;

try{
    email = new Parameter("email", userEmail, Parameter.FORMDATA);
    password = new Parameter("password", userPassword, Parameter.FORMDATA);
}catch(RestAPIParemeterException e){
    //some catch code
}

try{
    apiConnection.execute(new RestAPIListener(), "http://someurl.com/users" APIConnectionManager.POST, email, password);
}catch(InvalidMethodTypeException e){
    //Don't use invalid method types
}

```

But it also uses endpoints!

```Java
APIConnectionManager apiConnection = new APIConnectionManager("http://someurl.com/some/base/api");

try{
    apiConnection.executeWithEndpoint(new RestAPIListener(), "/messages", APIConnectionManager.GET);
}catch(InvalidMethodTypeException e){
    //Don't use invalid method types
}

```
