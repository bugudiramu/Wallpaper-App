package com.example.wallapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static com.example.wallapp.db.FavoriteContract.*;

public class FavoriteDbHelper extends SQLiteOpenHelper {
    Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favorites.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                    FavoriteEntry._ID + " INTEGER PRIMARY KEY," +
                    FavoriteEntry.COLUMN_NAME_IMAGEURL + " TEXT);";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // TODO Add Favorite to DB
    public void addTask(String imgURL, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry.COLUMN_NAME_IMAGEURL, imgURL);
        long newRowId = db.insert(FavoriteEntry.TABLE_NAME, null, values);
        Log.v("Db Operations : ", "One Row Inserted" + newRowId);
    }

    public Cursor displayTasks(SQLiteDatabase db) {
        String[] projections = {FavoriteEntry._ID, FavoriteEntry.COLUMN_NAME_IMAGEURL};
        Cursor cursor = db.query(FavoriteEntry.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }
}