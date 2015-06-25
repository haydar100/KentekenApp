package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import SQLite.MySQLiteHelper;
import domain.Car;

/**
 * Created by Haydar on 24-06-15.
 */
public class KentekenDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_KENTEKEN, MySQLiteHelper.COLUMN_MERK};


    public KentekenDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Car createCar(Car car) {
        Car newCar = null;
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_KENTEKEN, car.getKenteken());
        values.put(MySQLiteHelper.COLUMN_MERK, car.getMerk());
        long insertId = database.insert(MySQLiteHelper.TABLE_CARS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CARS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            newCar = cursorToCar(cursor);
            cursor.close();
        }

        return newCar;
    }

    public void deleteCar(Car car) {
        long id = car.getId();
        System.out.println("Car deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CARS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<Car>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CARS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Car c = cursorToCar(cursor);
            cars.add(c);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return cars;
    }


    private Car cursorToCar(Cursor cursor) {
        Car c = new Car();
        c.setId(cursor.getLong(0));
        c.setKenteken(cursor.getString(1));
        c.setMerk(cursor.getString(2));
        return c;
    }


}
