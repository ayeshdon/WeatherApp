package wth.com.hseya.weatherapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wth.com.hseya.weatherapp.MainApplication;
import wth.com.hseya.weatherapp.R;
import wth.com.hseya.weatherapp.adapter.SelectedLocationListAdapter;
import wth.com.hseya.weatherapp.bean.LocationDataBean;
import wth.com.hseya.weatherapp.db.table.LocationsTable;
import wth.com.hseya.weatherapp.listener.LocationDeleteListener;
import wth.com.hseya.weatherapp.utils.InternetConnection;

public class MainActivity extends AppCompatActivity implements LocationDeleteListener, AdapterView.OnItemClickListener {

    private TextView noLocationTextView;
    private ListView selectedLocationListView;
    private ArrayList<LocationDataBean> dataList;
    private SelectedLocationListAdapter locationListAdapter;
    private MainApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            application = (MainApplication) getApplication();

            setContentView(R.layout.activity_main);

            UIInitialize();



        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * Initialize All UI component here
     *
     * @throws Exception
     */
    private void UIInitialize() throws  Exception{
        try {

            noLocationTextView       = (TextView) findViewById(R.id.noLocationTextView);
            selectedLocationListView = (ListView) findViewById(R.id.selectedLocationListView);
            selectedLocationListView.setOnItemClickListener(this);

            setupListViewAdapter();


        }catch (Exception e){
            throw  e;
        }
    }

    /**
     * setup cities list adapter
     * @throws Exception
     */
    private void setupListViewAdapter()throws  Exception{
        try {

            LocationsTable table = new LocationsTable(this);
            dataList = table.getAllLocation();

            if (dataList.size() == 0){

                selectedLocationListView.setVisibility(View.INVISIBLE);
                noLocationTextView.setVisibility(View.VISIBLE);

            }else{
                noLocationTextView.setVisibility(View.INVISIBLE);
                selectedLocationListView.setVisibility(View.VISIBLE);
            }

            locationListAdapter = new SelectedLocationListAdapter(this,dataList,this);
            selectedLocationListView.setAdapter(locationListAdapter);



        }catch (Exception e){
            throw  e;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            setupListViewAdapter();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {

            int id = item.getItemId();

            if (id == R.id.action_add_city) {

                Intent callAddCity = new Intent(this, AddCityActivity.class);
                startActivity(callAddCity);

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
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
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnDeleteLocation(int pos) {
        try {

            LocationsTable table = new LocationsTable(this);
            table.deleteSelectedLocation(dataList.get(pos).getId());

            setupListViewAdapter();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {

            if (InternetConnection.getInstance(this).isConnectingToInternet()){


                application.showWeatherLocationBean = dataList.get(position);

                Intent callShowWeather = new Intent(this,WeatherShowActivity.class);

                startActivity(callShowWeather);

            }else{

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_internet));

            }

        }catch (Exception e){
        e.printStackTrace();
        Toast.makeText(this, getResources().getString(R.string.exception),
                Toast.LENGTH_SHORT).show();
    }
    }
}
