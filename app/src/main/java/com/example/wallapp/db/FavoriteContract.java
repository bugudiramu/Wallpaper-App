package com.example.wallapp.db;

import android.provider.BaseColumns;

public class FavoriteContract {
    private FavoriteContract() {
    }

    public static class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "fav";
        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME_IMAGEURL = "image";

    }
}
