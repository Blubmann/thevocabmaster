package com.example.fabianfleischer.thevocabmaster.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fabian.fleischer on 23.11.2014.
 */
public class DatabaseHandler_intern extends SQLiteOpenHelper{

    public static final String WORDS ="Words";
    public static final String DEUTSCH ="deutsch";
    public static final String ENGLISCH ="englisch";
    public static final String FEHLER="fehler";
    public static final String AUFRUFE="aufrufe";
    public static final String WORD_ID="_id";

    public static final String DATABASE_NAME="TEST.db";
    public static final int DATABASE_VERSION =2;

    private static final String DATABASE_CREATE ="create table " + WORDS + "(" + WORD_ID + " integer primary key autoincrement, "+ DEUTSCH + " text not null, "+ ENGLISCH + " text not null, "+FEHLER + " Integer, "+AUFRUFE + " Integer);";
    public DatabaseHandler_intern(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + WORDS);
        onCreate(db);
    }
}
