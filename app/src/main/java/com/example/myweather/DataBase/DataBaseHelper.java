/**
 * 施工中
 */
package com.example.myweather.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// TODO: 完成天气数据的本地保存，在无网络时就显示本地数据，在有网络时先显示本地数据，在通过网络获取新数据更新

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "WeatherLab";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DataBaseSchema.WeatherLab.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DataBaseSchema.WeatherLab.Cols.Now + ", " +
                DataBaseSchema.WeatherLab.Cols.Day + ", " +
                DataBaseSchema.WeatherLab.Cols.Hour +
                ")"
        );

        ContentValues values = new ContentValues();
        values.put(DataBaseSchema.WeatherLab.Cols.Now, "");
        values.put(DataBaseSchema.WeatherLab.Cols.Day, "");
        values.put(DataBaseSchema.WeatherLab.Cols.Hour, "");

        db.insert(DataBaseSchema.WeatherLab.NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
