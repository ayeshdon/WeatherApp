package wth.com.hseya.weatherapp.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import wth.com.hseya.weatherapp.MainApplication;
import wth.com.hseya.weatherapp.R;
import wth.com.hseya.weatherapp.api.API;
import wth.com.hseya.weatherapp.api.APICall;
import wth.com.hseya.weatherapp.api.APICallback;
import wth.com.hseya.weatherapp.api.APICaller;
import wth.com.hseya.weatherapp.api.APIResult;
import wth.com.hseya.weatherapp.bean.LocationDataBean;
import wth.com.hseya.weatherapp.bean.WeatherDataBean;
import wth.com.hseya.weatherapp.json.JsonParser;
import wth.com.hseya.weatherapp.ui.CircularImageView;
import wth.com.hseya.weatherapp.ui.CommonUI;

public class WeatherShowActivity extends AppCompatActivity implements APICallback {


    private ProgressDialog progressDialog;
    private MainApplication application;
    private LocationDataBean dataBean;
    private TextView tempCTextView,temFTextView,feelLikeCTextView,feelLikeFTextView,humidityTextView,precipitationTextView,
            pressureTextView,windTextView,windParaTextView,visibilityTextView,cloudTextView,timeTextView,destextView;
    private CircularImageView weatherIconImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {

            application = (MainApplication) getApplication();

            dataBean = application.showWeatherLocationBean;



            final ActionBar customActionBar = getSupportActionBar();


            customActionBar.hide();
            customActionBar.setIcon(null);


            CommonUI.getInstance(this).setCommonActionBar(customActionBar, this, true,
                    dataBean.getName(), true);


            customActionBar.show();

            setContentView(R.layout.activity_weather_show);

            UIIniitailize();


            showProgress();

            APICaller apiCaller = new APICaller(this);
            APICall apiCall = new APICall(API.WEATHER_API +
                    dataBean.getLat()+","+dataBean.getLon() +API.API_KEY,
                    APICall.APICallMethod.GET, this);
            apiCall.setJsonSend(null);
            apiCaller.execute(apiCall);


        }catch (Exception e){
            hideProgress();
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
            callBack();
        }


    }

    /**
     * Initialize UI Component
     * @throws Exception
     */
    private void UIIniitailize()throws Exception{
        try {

            tempCTextView         = (TextView) findViewById(R.id.tempCTextView);
            temFTextView          = (TextView) findViewById(R.id.temFTextView);
            feelLikeCTextView     = (TextView) findViewById(R.id.feelLikeCTextView);
            feelLikeFTextView     = (TextView) findViewById(R.id.feelLikeFTextView);
            humidityTextView      = (TextView) findViewById(R.id.humidityTextView);
            precipitationTextView = (TextView) findViewById(R.id.precipitationTextView);
            windTextView          = (TextView) findViewById(R.id.windTextView);
            pressureTextView      = (TextView) findViewById(R.id.pressureTextView);
            windParaTextView      = (TextView) findViewById(R.id.windParaTextView);
            visibilityTextView    = (TextView) findViewById(R.id.visibilityTextView);
            cloudTextView         = (TextView) findViewById(R.id.cloudTextView);
            timeTextView          = (TextView) findViewById(R.id.timeTextView);
            destextView           = (TextView) findViewById(R.id.destextView);

            weatherIconImageView = (CircularImageView) findViewById(R.id.weatherIconImageView);


        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            callBack();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        callBack();
    }

    /**
     * Call previous activity
     */
    private void callBack() {

        finish();

    }


    private void showProgress() {
        try {

            progressDialog = ProgressDialog.show(this, "", "Please wait...");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideProgress() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void onAPICallComplete(APIResult apiResult) {
        hideProgress();
        try {

            WeatherDataBean showDataBean = JsonParser.getInsatance().getWeatherResult(apiResult.getResultJson());


            tempCTextView.setText(showDataBean.getTempC()+'\u00B0'+"C");
            temFTextView.setText(showDataBean.getTempF()+'\u00B0'+"F");
            feelLikeCTextView.setText(showDataBean.getFeelsLikeC()+'\u00B0'+"C");
            feelLikeFTextView.setText(showDataBean.getFeelsLikeF()+'\u00B0'+"F");
            humidityTextView.setText(showDataBean.getHumidity());
            precipitationTextView.setText(showDataBean.getPrecipMM());
            pressureTextView.setText(showDataBean.getPressure());
            windTextView.setText(showDataBean.getWinddirDegree());
            visibilityTextView.setText(showDataBean.getVisibility());
            cloudTextView.setText(showDataBean.getCloudOver());
            timeTextView.setText(showDataBean.getObservationTime());
            destextView.setText(showDataBean.getWeatherDesc());
            windParaTextView.setText(" km/h" + showDataBean.getWinddir16Point());


            imageLoader(showDataBean.getWeatherIconUrl(),weatherIconImageView);


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
            callBack();
        }
    }


    /**
     * Load weather image to image view
     * @param imageUri
     * @param imageView
     */
    private void imageLoader(String imageUri, ImageView imageView) {

        try {

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
            .build();

            ImageLoader imageLoader = ImageLoader.getInstance();


            imageLoader.init(config);

            imageLoader.displayImage(imageUri, imageView);
            imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                }
            });
           imageLoader.loadImageSync(imageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
