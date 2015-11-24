package wth.com.hseya.weatherapp.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import wth.com.hseya.weatherapp.bean.LocationDataBean;
import wth.com.hseya.weatherapp.db.DatabaseHelper;


public class LocationsTable {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;


    public LocationsTable(Context context) {


        if (dbHelper == null)
            dbHelper = new DatabaseHelper(context);
    }

    /*
     * open db  connection
     */
    private void open() throws SQLException {
        try {

            database = dbHelper.getWritableDatabase();

        } catch (Exception e) {
            throw e;
        }

    }

    /*
     * close db connection
     */
    private void close() {
        dbHelper.close();
        database.close();
    }


    /*
     * delete all rows
     */
    public void deleteALL() throws Exception {

        try {

            open();
            database.delete(DatabaseHelper.LOCATION_TABLE, null, null);


        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    /*
	 * Delete Selected Location
	 */
    public void deleteSelectedLocation(int id) throws Exception {

        try {

            open();
         database.delete(DatabaseHelper.LOCATION_TABLE, DatabaseHelper.KEY_ID+ " =? ",
                    new String[]{String.valueOf(id)});


        } catch (Exception e) {
            throw e;
        }finally{
            close();
        }

    }


    /*
     * Insert  Location to database
     */
    public Long insertContactList(LocationDataBean dataBean) throws Exception {

        try {
            open();

            ContentValues values = new ContentValues();


            values.put(DatabaseHelper.LOCATION_NAME, dataBean.getName());
            values.put(DatabaseHelper.LOCATION_LAT, dataBean.getLat());
            values.put(DatabaseHelper.LOCATION_LONG, dataBean.getLon());


           Long i = database.insert(DatabaseHelper.LOCATION_TABLE, null, values);


            Log.e("INSERT",i+"");

            return i;

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }


    }


    /**
     * check given location already store in to DB.If yes the return true,
     * else return  false
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public boolean isExitLocation(LocationDataBean bean) throws Exception {

        Cursor cursor = null;

        try {

            open();

            String phoneNumber = bean.getName();

            cursor = database.query(DatabaseHelper.LOCATION_TABLE,
                    new String[]{DatabaseHelper.KEY_ID},
                    DatabaseHelper.LOCATION_NAME + " =? ",
                    new String[]{
                            String.valueOf(phoneNumber)},
                    null, null, null, null);

            if (cursor.getCount() > 0) {
                return true;
            }

            return false;

        } catch (Exception e) {
            throw e;
        } finally {
            close();

            if (cursor != null) {

                cursor.close();
            }
        }
    }


    /**
     * get all Location list
     *
     * @return
     * @throws Exception
     */
    public ArrayList<LocationDataBean> getAllLocation() throws Exception {
        Cursor cursor = null;

        try {
            open();

            ArrayList<LocationDataBean> dataList = new ArrayList<LocationDataBean>();


            String query = "SELECT " +
                    DatabaseHelper.LOCATION_NAME + " AS " + DatabaseHelper.LOCATION_NAME + ", " +
                    DatabaseHelper.LOCATION_LAT + " AS " + DatabaseHelper.LOCATION_LAT + ", " +
                    DatabaseHelper.KEY_ID + " AS " + DatabaseHelper.KEY_ID + ", " +
                    DatabaseHelper.LOCATION_LONG + " AS " + DatabaseHelper.LOCATION_LONG + " " +
                    " FROM " + DatabaseHelper.LOCATION_TABLE +
                    " ORDER BY " + DatabaseHelper.LOCATION_NAME;


            cursor = database.rawQuery(query, null);


            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                LocationDataBean dataBean = new LocationDataBean();

                dataBean.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION_NAME)));
                dataBean.setLat(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.LOCATION_LAT)));
                dataBean.setLon(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.LOCATION_LONG)));
                dataBean.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));


                dataList.add(dataBean);


            }


            return dataList;
        } catch (Exception e) {
            throw e;
        } finally {
            close();
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
