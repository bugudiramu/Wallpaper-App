package com.example.wallapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallapp.db.FavoriteContract;
import com.example.wallapp.db.FavoriteContract.FavoriteEntry;
import com.example.wallapp.db.FavoriteDbHelper;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WallpaperDetail extends AppCompatActivity {
    ImageView imageView;
    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_detail);

        // Remove the acion bar
        getSupportActionBar().hide();

        // TODO Database Instantiation
        final FavoriteDbHelper dbHelper = new FavoriteDbHelper(getApplicationContext());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        // GET the Views
        CardView setAsWallPaperBtn = findViewById(R.id.set_as_wallpaper);
        CardView share = findViewById(R.id.share);
        CardView saveFavorite = findViewById(R.id.save);

        Intent intent = getIntent();
        imageURL = intent.getStringExtra("image");
//        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
//        int likeCount = intent.getIntExtra(EXTRA_LIKES, 0);

        imageView = findViewById(R.id.detail_img);
//        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
//        TextView textViewLikes = findViewById(R.id.text_view_like_detail);

        Picasso.with(this).load(imageURL).fit().centerInside().into(imageView);
//        textViewCreator.setText(creatorName);
//        textViewLikes.setText("Likes: " + likeCount);
// TODO Onclicklisteners for Set as Wallpaper,Share and Save/Favorite
        setAsWallPaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWall();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
        saveFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addTask(imageURL, db);
                Toast.makeText(getApplicationContext(), "Item added to Favorites!", Toast.LENGTH_SHORT).show();

//                readFavorites();
            }
        });


    }

    public void setWall() {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        try {
            manager.setBitmap(bitmap);
            Toast.makeText(getApplicationContext(), "Set Wallpaper Successfully ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Wallpaper not load yet!", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareApp() {
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, Uri.parse(imageURL).toString());
//        intent.putExtra(Intent.EXTRA_STREAM,bitmap);
        startActivity(intent);
//        startActivity(Intent.createChooser(intent, "choose one"));
    }


//    public void readFavorites() {
//        String info = "";
//        FavoriteDbHelper dbHelper = new FavoriteDbHelper(getApplicationContext());
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = dbHelper.displayTasks(db);
//        while (cursor.moveToNext()) {
//            String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(FavoriteEntry.ID)));
//            String imgUrl = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_NAME_IMAGEURL));
//            info = info + "ID " + id + "\n" + "ImageURL " + imgUrl;
//        }
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        readFavorites();
//    }


    public void shareWall() {
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Image Demo.jpg");
            try {
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(byteArrayOutputStream.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/ImageDemo.jpg"));
            startActivity(Intent.createChooser(intent, "Share using"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private File getDisc() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file, "Image Demo");
    }

    private void startWall() {
        FileOutputStream outputStream = null;
        File file = getDisc();
        if (!file.exists() && !file.mkdirs()) {
            Toast.makeText(this, "Can't create dir to save image", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-mm-dd hh:mm:ss");
        String date = simpleDateFormat.format(new Date());
        String name = "Img" + date + ".jpg";
        String fileName = file.getAbsolutePath() + "/" + name;
        File newFile = new File(fileName);
        try {
            outputStream = new FileOutputStream(newFile);
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshGallery(newFile);
    }

    private void refreshGallery(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }
}