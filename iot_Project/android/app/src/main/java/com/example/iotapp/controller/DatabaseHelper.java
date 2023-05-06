package com.example.iotapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.iotapp.model.HumidityData;
import com.example.iotapp.model.TemperatureData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TEMPERATURE = "TEMPERATURE";
    public static final String ID = "ID";
    public static final String VALUE = "VALUE";
    public static final String DATE = "DATE";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + "TEMPERATURE" +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, VALUE DOUBLE, DATE STRING)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne (TemperatureData temperatureData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VALUE, temperatureData.getValue());
        cv.put(DATE, temperatureData.getDate());
        long insert = db.insert(TEMPERATURE, null,cv);
        if(insert == -1) {
            return false;
        }
        else return true;
    }
    public List<TemperatureData> getTemperature () {
        List<TemperatureData> returnTemperature = new ArrayList<>();

        String queryData = "SELECT * FROM " + TEMPERATURE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryData, null);

        if(cursor.moveToFirst()){
            do{

                double value = cursor.getDouble(1);
                String date = cursor.getString(2);

                TemperatureData temperatureData = new TemperatureData(value, date);
                returnTemperature.add(temperatureData);
            }
            while (cursor.moveToNext());
        }
        else {

        }
            cursor.close();
            db.close();
        return returnTemperature;
    }
}
