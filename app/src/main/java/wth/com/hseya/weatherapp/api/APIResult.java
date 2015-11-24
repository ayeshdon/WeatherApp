package wth.com.hseya.weatherapp.api;

import org.json.JSONObject;

import java.io.InputStream;

public class APIResult {

    private String responseBody;
    private int statusCode;
    private InputStream inputStream;
    private JSONObject resultJson;
    private String URL;


    /*
     * get API Responce body
     */
    public String getResponseBody() {
        return responseBody;
    }

    /*
     * set API responce body
     */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    /*
     * get API responce status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /*
     * set API responce status code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /*
     * get API responce InputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /*
     * set API responce InputStream
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    /*
     * get responce json object
     */
    public JSONObject getResultJson() {
        return resultJson;
    }

    /*
     * set response json object
     */
    public void setResultJson(JSONObject resultJson) {
        this.resultJson = resultJson;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
