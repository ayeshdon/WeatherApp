package wth.com.hseya.weatherapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wth.com.hseya.weatherapp.R;
import wth.com.hseya.weatherapp.adapter.LocationSearchListAdapter;
import wth.com.hseya.weatherapp.api.APICall;
import wth.com.hseya.weatherapp.api.APICallback;
import wth.com.hseya.weatherapp.api.APICaller;
import wth.com.hseya.weatherapp.api.APIResult;
import wth.com.hseya.weatherapp.bean.LocationDataBean;
import wth.com.hseya.weatherapp.db.table.LocationsTable;
import wth.com.hseya.weatherapp.json.JsonParser;
import wth.com.hseya.weatherapp.ui.CommonUI;
import wth.com.hseya.weatherapp.utils.InternetConnection;

public class AddCityActivity extends AppCompatActivity implements View.OnClickListener, APICallback, AdapterView.OnItemClickListener {

    private Button searchButton;
    private EditText citySearchEditText;
    private ProgressDialog progressDialog;
    private ListView locationListView;
    private ArrayList<LocationDataBean> locationList;
    private LocationSearchListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            final ActionBar customActionBar = getSupportActionBar();


            customActionBar.hide();
            customActionBar.setIcon(null);


            CommonUI.getInstance(this).setCommonActionBar(customActionBar, this, true,
                    getResources().getString(R.string.add_new_cities), true);


            customActionBar.show();


            setContentView(R.layout.activity_add_city);

            UIInitialize();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
            callBack();

        }


    }

    /**
     * Initialize All UI component here
     *
     * @throws Exception
     */
    private void UIInitialize() throws Exception {
        try {

            searchButton = (Button) findViewById(R.id.searchButton);
            searchButton.setOnClickListener(this);


            citySearchEditText = (EditText) findViewById(R.id.citySearchEditText);
            citySearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        try {

                            searchCityAction();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return true;
                    }
                    return false;
                }
            });

            locationListView = (ListView) findViewById(R.id.locationListView);
            locationListView.setOnItemClickListener(this);


        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Call previous activity
     */
    private void callBack() {

        finish();

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
    public void onClick(View v) {
        try {

            switch (v.getId()) {
                case R.id.searchButton:
                    /* search button click action */

                    searchCityAction();


                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * cities search action(by google map API)
     *
     * @throws Exception
     */
    private void searchCityAction() throws Exception {

        try {


            if (InternetConnection.getInstance(this).isConnectingToInternet()) {

                String cityName = citySearchEditText.getText().toString();

                if (cityName.isEmpty()) {

                    Toast.makeText(this, getResources().getString(R.string.city_empty),
                            Toast.LENGTH_LONG).show();

                } else {


                    showProgress();

                    APICaller apiCaller = new APICaller(this);
                    APICall apiCall = new APICall("https://maps.googleapis.com/maps/api/geocode/json?address=" + cityName +
                            "&sensor=true",
                            APICall.APICallMethod.GET, this);
                    apiCall.setJsonSend(null);
                    apiCaller.execute(apiCall);


                }

            } else {

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_internet));

            }


        } catch (Exception e) {
            hideProgress();
            throw e;
        }
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

            locationList = JsonParser.getInsatance().getLocationDataList(apiResult.getResultJson());

            locationListAdapter();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void locationListAdapter() {

        try {

            listAdapter = new LocationSearchListAdapter(this, locationList);
            locationListView.setAdapter(listAdapter);


        } catch (Exception e) {
            throw e;
        }
    }

    private void showAlert(String title, String msg) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle(title);

            alertDialogBuilder
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {


            final LocationDataBean dataBean = locationList.get(position);


            String locName = dataBean.getName();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle(getResources().getString(R.string.save_location));

            alertDialogBuilder
                    .setMessage(getResources().getString(R.string.save_location_confirm) + " " + locName + " ?")
                    .setCancelable(false)
                    .setNegativeButton("ON", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            try {
                                LocationsTable table = new LocationsTable(AddCityActivity.this);

                                if (table.isExitLocation(dataBean)){

                                    Toast.makeText(AddCityActivity.this, getResources().getString(R.string.location_already_exists),
                                            Toast.LENGTH_SHORT).show();
                                }else{

                                  long value =  table.insertContactList(dataBean);

                                    if (value > 0){

                                        callBack();

                                        Toast.makeText(AddCityActivity.this, getResources().getString(R.string.location_add_success),
                                                Toast.LENGTH_SHORT).show();

                                    }else{

                                        Toast.makeText(AddCityActivity.this, getResources().getString(R.string.exception),
                                                Toast.LENGTH_SHORT).show();

                                    }


                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(AddCityActivity.this, getResources().getString(R.string.exception),
                                        Toast.LENGTH_SHORT).show();
                            }


                            dialog.cancel();
                        }
                    }

            );

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
