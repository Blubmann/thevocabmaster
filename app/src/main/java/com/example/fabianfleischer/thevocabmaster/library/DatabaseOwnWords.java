package com.example.fabianfleischer.thevocabmaster.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by fabian.fleischer on 12.05.2015.
 */
public class DatabaseOwnWords extends SQLiteOpenHelper{
    public static final String TABLE_WORDS = "words";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DEUTSCH = "deutsch";
    public static final String COLUMN_ENGLISCH = "englisch";
    public static final String DATABASE_NAME = "words.db";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + TABLE_WORDS + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_DEUTSCH + " text not null,"
            + COLUMN_ENGLISCH+ " text not null);";

    public DatabaseOwnWords(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(DatabaseOwnWords.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        onCreate(db);
    }
}
