package wth.com.hseya.weatherapp.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class APICaller extends AsyncTask<APICall, String, APIResult> {

    private APICallback apiCallback = null;


    public APICaller(APICallback apiCallback) {

        this.apiCallback = apiCallback;

    }

    @Override
    protected APIResult doInBackground(APICall... params) {


        APIResult apiDataBean = new APIResult();
        InputStream is = null;
        APICall apiCall = null;


        try {

            apiCall = params[0];

            Log.e("URL SEND  ", apiCall.getUrl());

            apiDataBean.setURL(apiCall.getUrl());
            URL url = new URL(apiCall.getUrl());


            if (apiCall.getMethod() == APICall.APICallMethod.GET) {


                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");

                urlConnection.connect();

                is = urlConnection.getInputStream();


            }


            apiDataBean.setResultJson(getResultJsonObject(is));


        } catch (Exception e) {
            e.printStackTrace();
            apiDataBean.setResultJson(null);

        }

        return apiDataBean;


    }


    @Override
    protected void onPostExecute(APIResult result) {
        super.onPostExecute(result);

        if (result != null) {

            if (apiCallback != null) {

                apiCallback.onAPICallComplete(result);
            }
        }
    }





    /*
     * get json object form API response
     */
    private JSONObject getResultJsonObject(InputStream is) {

        JSONObject jObj = null;
        String json = "";

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            is.close();
            json = sb.toString();


            Log.e("API RESULT", json);

            jObj = null;
            jObj = new JSONObject(json);

            return jObj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}