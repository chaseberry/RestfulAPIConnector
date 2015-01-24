package edu.csh.chase.RestfulAPIConnector;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONArrayWrapper;
import edu.csh.chase.RestfulAPIConnector.JSONWrapper.JSONWrapper;

/**
 * Created by chase on 9/27/14.
 */
public class RestAPIRunnable implements Runnable {

    private RestAPIListener runner;
    private HttpRequestBase httpRequest;

    public RestAPIRunnable(RestAPIListener runner, HttpRequestBase request) {
        this.httpRequest = request;
        this.runner = runner;
    }

    @Override
    public void run() {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httpRequest);
            StatusLine status = response.getStatusLine();
            if (runner != null) {
                runner.setStatusCode(status.getStatusCode());
            }
            if (response.getEntity() == null) {
                postExecute(null);
                return;
            }
            String res = EntityUtils.toString(response.getEntity());
            postExecute(JSONWrapper.parseJSON(res));
            return;
        } catch (JSONException ex) {
            postExecute(null);
            return;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (runner != null) {
            runner.setStatusCode(1);
        }
        postExecute(null);
    }

    public void postExecute(JSONWrapper object) {
        if (runner != null) {
            runner.setData(object);
            runner.start();
        }
    }

}
