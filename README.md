RestfulAPIConnector
===================

A simple Android library for making requests to servers using GET, POST, PUT, DELTE, HEAD, and OPTIONS.

To-do
=====
- [x] Fix Success status code check
- [x] Add Javadocs
- [ ] Change how errors work 
- [ ] Add customization for defining success and failure
- [ ] Improve regex on executeWithEndpoint functions
- [x] Optimize the nested keys functions


Usage
=====
There are four main classes needed to use the library: APIConnectionManager, Parameter, RestAPIListener, and JSONWrapper

When retreiving data from a requests a 'Success' is defined as a HTTP Status Code in the range of 200 to 299 and a failure is anything else.

Data retrieved from the server be in JSON format.

```Java
APIConnectionManager apiConnection = new APIConnectionManager(someContext);

FormParameter email = new FormParameter("email", userEmail);
FormParameter password = new FormParameter("password", userPassword);

apiConnection.execute(new RestAPIListener() {
            @Override
            public void success(JSONWrapper data, int statusCode) {
                //do something on success
            }

            @Override
            public void failure(int statusCode) {
                //do something on failure
            }
        }, "http://someurl.com/users" RestAPIRunnable.POST, email, password);

```

But it also uses endpoints!

```Java
APIConnectionManager apiConnection = new APIConnectionManager("http://someurl.com/some/base/api");

apiConnection.executeWithEndpoint(new RestAPIListener() {
            @Override
            public void success(JSONWrapper data, int statusCode) {
                //something on success
            }

            @Override
            public void failure(int statusCode) {
                //something on failure
            }
        }, "/messages", RestAPIRunnable.GET);
```

Form Data, URL Parameters, and Headers all have new creation in the form of URLParameter, HeaderParameter, and FormParameter. These work in the exact same was as delcaring a parameter but it takes care of type handling.
```Java
FormParameter formParam = new FormParameter("key", "value");
URLParameter urlParam = new URLParameter("key", "value");
HeaderParameter header = new HeaderParameter("key", value);
```

JSON parameters also got this update, but with a nice twist. JSONParameters now support their value with JSONObjects, JSONArrays, ints, longs, doubles, and booleans.
```Java
JSONParameter jsonParam = new JSONParameter("key", "value");
JSONParameter jsonParam2 = new JSONParameter("key", 1.52);
```


Data returned from the server is bundled into a JSONWrapper which can contain either a JSONObject or JSONArray with jsons default accessors.

However convience methods have been added to clean up code and make getting data easier.

The 'checkAndGet(Type)' methods take a (Type) failed, and a/some String(s) keys to look up.
This function will return the value associted with the first found key.

```Java
RestAPIListener loginListener = new RestAPIListener(){
    @Override
    public void success(JSONWrapper json, int statusCode) {
        String item1;
        try{
            item1 = json.getString("item1");//data is a JSONObject
            item1 = json.getString(0);//data is a JSONArray
        }catch(JSONException e){
        
        }
        //A failed string, a single key
        String item2 = json.checkAndGetString("failedToGetString", "item2");
        //A failed int with two keys to search for
        int item3 = json.checkAndGetInt(-1, "item3","dataInUnixTimeStamp");
        //This line is the same as the one above
        int item3 = json.checkAndGetInt(-1, new String[]{"item3", "dataInUnixTimeStamp"});
    }

    @Override
    public void failure(int statusCode) {
        //some failure block
    }
};

```

The JSONWrapper also supports nested keys/arrays.

```JSON
{
    "key1":"some string",
    "key2":{
        "key3":"some data"
    }
    
}
```

The following data stored in key3 can be accessed by
```Java
    json.checkAndGetString("failed", "key2:key3");
```
The ':' syntax in a key denotes nested. This can be used to look inside arrays with "messages:0:sender"
The wrapper will look in the messages key, at the 0 index of that array, for the sender key.
