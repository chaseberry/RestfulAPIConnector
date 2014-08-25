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
There are four main classes needed to use the library: APIConnectionManager, Parameter, RestfulAPIListener, and JSONWrapper



```Java
APIConnectionManager apiConnection = new APIConnectionManager(someContext);
Parameter email, password;
try{
    email = new Parameter("email", userEmail, Parameter.FORMDATA);
    password = new Parameter("password", userPassword, Parameter.FORMDATA);
}catch(RestAPIParemeterException e){
    //some catch code
}
apiConnection.execute(new RestfulAPIListener(), "http://someurl.com/users" APIConnectionManager.POST, email, password);


```
