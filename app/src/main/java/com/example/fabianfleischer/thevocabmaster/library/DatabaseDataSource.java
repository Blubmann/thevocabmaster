package com.example.fabianfleischer.thevocabmaster.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabian.fleischer on 12.05.2015.
 */
public class DatabaseDataSource {
    private SQLiteDatabase database;
    private DatabaseOwnWords dbHelper;
    private String[] allColumns={DatabaseOwnWords.COLUMN_ID,DatabaseOwnWords.COLUMN_DEUTSCH,DatabaseOwnWords.COLUMN_ENGLISCH};

    public DatabaseDataSource(Context context){
        dbHelper = new DatabaseOwnWords(context);
    }

    public void open() throws SQLException {
        database=dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Word createWord(String deutsch, String englisch){
        ContentValues values = new ContentValues();
        values.put(DatabaseOwnWords.COLUMN_DEUTSCH, deutsch);
        values.put(DatabaseOwnWords.COLUMN_ENGLISCH, englisch);
        long insertID=database.insert(DatabaseOwnWords.TABLE_WORDS, null, values);
        System.out.println("Das steht in insertID "+values);
        Cursor cursor = database.query(DatabaseOwnWords.TABLE_WORDS,allColumns,DatabaseOwnWords.COLUMN_ID + "=" + insertID,null,null,null,null);
        cursor.moveToFirst();
        Word newWord = cursorToWord(cursor);
        cursor.close();
        return newWord;
    }

    public void deleteWord(Word word){
        long id = word.getID();
        System.out.println("Word deleted with id: "+id);
        database.delete(DatabaseOwnWords.TABLE_WORDS, DatabaseOwnWords.COLUMN_ID + " = " + id, null);
    }

    public List<Word> getAllWords(){
        List<Word> words = new ArrayList<Word>();
        Cursor cursor = database.query(DatabaseOwnWords.TABLE_WORDS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Word word = cursorToWord(cursor);
            words.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return words;
    }

    private Word cursorToWord(Cursor cursor){
        Word word = new Word();
        word.setID(cursor.getLong(0));
        word.setDeutsch(cursor.getString(1));
        word.setEnglisch(cursor.getString(2));
        return word;
    }
}
