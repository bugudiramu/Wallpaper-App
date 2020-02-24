package com.example.wallapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.wallapp.db.FavoriteContract;
import com.example.wallapp.db.FavoriteContract.FavoriteEntry;
import com.example.wallapp.db.FavoriteDbHelper;
import com.squareup.picasso.Picasso;

public class FavoriteActivity extends AppCompatActivity {
    ImageView favoriteImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // GET the views
        favoriteImg = findViewById(R.id.favorite_img);
        readFavorites();
    }

    @Override
    protected void onStart() {
        super.onStart();
        readFavorites();
    }

    public void readFavorites() {
        String info = "";
        FavoriteDbHelper dbHelper = new FavoriteDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.displayTasks(db);
        while (cursor.moveToNext()) {
            String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(FavoriteEntry.ID)));
            String imgUrl = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_NAME_IMAGEURL));
            Picasso.with(this).load(imgUrl).into(favoriteImg);
//            info = info + "ID " + id + "\n" + "ImageURL " + imgUrl;
        }
    }
}
