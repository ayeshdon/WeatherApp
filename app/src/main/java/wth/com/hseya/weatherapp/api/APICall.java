package wth.com.hseya.weatherapp.api;
/**
 * this data bean class use to store data which we want to set api call
 */

import android.content.Context;

import org.json.JSONObject;


public class APICall {


    private String url;
    private APICallMethod method = APICallMethod.POST;
    private boolean isJsonArray;
    private JSONObject jsonSend;
    private Context context;

    public APICall(String url) {
        this.url = url;
    }

    public APICall(String url, APICallMethod method, Context context) {
        this.url = url;
        this.method = method;
        this.context = context;
    }

    /**
     * get API call URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * set API Call URL
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * get API call method
     */
    public APICallMethod getMethod() {
        return method;
    }

    /**
     * set API call method
     */
    public void setMethod(APICallMethod method) {
        this.method = method;
    }

    /**
     * is json array
     */
    public boolean isJsonArray() {
        return isJsonArray;
    }

    /**
     * set json array flag
     */
    public void setJsonArray(boolean isJsonArray) {
        this.isJsonArray = isJsonArray;
    }

    /**
     * get send json value
     */
    public JSONObject getJsonSend() {
        return jsonSend;
    }

    /**
     * set send json value
     */
    public void setJsonSend(JSONObject jsonSend) {
        this.jsonSend = jsonSend;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * API call method
     */
    public enum APICallMethod {
        POST, GET, DELETE, PUT
    }


}
