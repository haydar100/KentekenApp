package SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Haydar on 24-06-15.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {


    public static final String TABLE_CARS = "cars";
    public static final String COLUMN_ID = "cars_id";
    public static final String COLUMN_KENTEKEN = "kenteken";
    // Database creation sql statement

    public static final String COLUMN_VOERTUIGSOORT = "voertuigsoort";
    public static final String COLUMN_MERK = "merk";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CARROSSIE = "carrossie";
    public static final String COLUMN_BRANDSTOF = "brandstof";
    public static final String COLUMN_KLEUR = "kleur";
    public static final String COLUMN_CILINDER = "cilinder";
    public static final String COLUMN_ZITPLAATSEN = "zitplaatsen";
    public static final String COLUMN_VERMOGEN = "vermogen";
    public static final String COLUMN_MASSA = "massa";
    public static final String COLUMN_MAXMASSA = "maxmassa";
    public static final String COLUMN_BPM = "bpm";
    public static final String COLUMN_GESTOLEN = "gestolen";
    private static final String DATABASE_NAME = "cars.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_CARS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_KENTEKEN
            + " text not null, " + COLUMN_MERK + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        onCreate(db);
    }

}