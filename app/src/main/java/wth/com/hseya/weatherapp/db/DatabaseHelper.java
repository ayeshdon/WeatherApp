package wth.com.hseya.weatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ayesh on 11/23/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /*keys*/
    public static final String KEY_ID = "_id";
    public static final String LOCATION_NAME = "LOCATION_NAME";
    public static final String LOCATION_LAT = "LOCATION_LAT";
    public static final String LOCATION_LONG = "LOCATION_LONG";
    public static final String LOCATION_TABLE = "LOCATION_TABLE";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RD_LOCATION.DB";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_LOCATION_TABLE = "CREATE TABLE "
                + LOCATION_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + LOCATION_NAME + " TEXT,"
                + LOCATION_LONG + " REAL,"
                + LOCATION_LAT + " REAL"
                + ")";


        db.execSQL(CREATE_LOCATION_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}
