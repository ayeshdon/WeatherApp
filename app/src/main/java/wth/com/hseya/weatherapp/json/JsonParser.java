package wth.com.hseya.weatherapp.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wth.com.hseya.weatherapp.bean.LocationDataBean;
import wth.com.hseya.weatherapp.bean.WeatherDataBean;

/**
 * Created by ayesh on 11/19/15.
 */
public class JsonParser {

    private static JsonParser instace;


    public static synchronized JsonParser getInsatance() {

        if (instace == null) {

            instace = new JsonParser();

        }

        return instace;
    }


    /**
     * process location data json and return arraylist
     *
     * @param rootJson
     * @return
     */
    public ArrayList<LocationDataBean> getLocationDataList(JSONObject rootJson) throws JSONException {
        try {

            ArrayList<LocationDataBean> dataList = new ArrayList<LocationDataBean>();

            JSONArray jsonArray = (JSONArray) rootJson.get("results");


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                JSONObject locationJson = (JSONObject) jsonObj.get("geometry");


                JSONObject locationW = (JSONObject) locationJson.get("location");
                double lat = (Double) locationW.get("lat");
                double lng = (Double) locationW.get("lng");

                String address = jsonObj.getString("formatted_address");


                LocationDataBean bean = new LocationDataBean();


                bean.setLat(lat);
                bean.setLon(lng);
                bean.setName(address);

                dataList.add(bean);

            }


            return dataList;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * get  WeatherDataBean from response JSON from API
     * @param rootJson
     * @return
     * @throws Exception
     */
    public WeatherDataBean getWeatherResult(JSONObject rootJson)throws  Exception{
        try {

            WeatherDataBean dataBean = new WeatherDataBean();

            JSONArray currentJsonArray = rootJson.getJSONObject("data").getJSONArray("current_condition");

            JSONObject childObjct = currentJsonArray.getJSONObject(0);


            dataBean.setCloudOver(childObjct.getString("cloudcover"));
            dataBean.setFeelsLikeC(childObjct.getString("FeelsLikeC"));
            dataBean.setFeelsLikeF(childObjct.getString("FeelsLikeF"));
            dataBean.setHumidity(childObjct.getString("humidity"));
            dataBean.setObservationTime(childObjct.getString("observation_time"));
            dataBean.setPrecipMM(childObjct.getString("precipMM"));
            dataBean.setPressure(childObjct.getString("pressure"));
            dataBean.setTempC(childObjct.getString("temp_C"));
            dataBean.setTempF(childObjct.getString("temp_F"));
            dataBean.setVisibility(childObjct.getString("visibility"));
            dataBean.setWeatherCode(childObjct.getString("weatherCode"));
            dataBean.setWinddir16Point(childObjct.getString("winddir16Point"));
            dataBean.setWinddirDegree(childObjct.getString("winddirDegree"));
            dataBean.setWindspeedKmph(childObjct.getString("windspeedKmph"));
            dataBean.setWindspeedMiles(childObjct.getString("windspeedMiles"));
            dataBean.setWeatherDesc(childObjct.getJSONArray("weatherDesc").getJSONObject(0).getString("value"));
            dataBean.setWeatherIconUrl(childObjct.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));



            return dataBean;

        } catch (Exception e) {
        throw e;
    }
    }
}
