package com.example.fabianfleischer.thevocabmaster.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabian.fleischer on 23.11.2014.
 */


public class WordListOperation {
    private DatabaseHandler_intern dbHelper;
    private String[] WORDS_TABLE_COLUMNS = {DatabaseHandler_intern.WORD_ID, DatabaseHandler_intern.DEUTSCH, DatabaseHandler_intern.ENGLISCH, DatabaseHandler_intern.FEHLER, DatabaseHandler_intern.AUFRUFE};
    private SQLiteDatabase database;

    public WordListOperation(Context context){
        dbHelper = new DatabaseHandler_intern(context);
    }

    public void open() throws SQLException{
        database=dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Word addWord(String deutsch, String englisch){
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler_intern.DEUTSCH,deutsch);
        values.put(DatabaseHandler_intern.ENGLISCH,englisch);
        values.put(DatabaseHandler_intern.AUFRUFE,0);
        values.put(DatabaseHandler_intern.FEHLER,0);
        long wordId = database.insert(DatabaseHandler_intern.WORDS,null,values);
        Cursor cursor = database.query(DatabaseHandler_intern.WORDS,WORDS_TABLE_COLUMNS,DatabaseHandler_intern.WORD_ID+ "=" +wordId,null,null,null,null);
        cursor.moveToFirst();
        Word newComment = parseWord(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteWord(Word comment){
        int id = comment.getId();
        System.out.println("Wort mit der id "+id+ " gelöscht");
        database.delete(DatabaseHandler_intern.WORDS,DatabaseHandler_intern.WORD_ID+ "="+id,null);
    }

    public List getAllWords(){
        List words = new ArrayList();
        Cursor cursor =database.query(DatabaseHandler_intern.WORDS,WORDS_TABLE_COLUMNS,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Word word =parseWord(cursor);
            words.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return words;
    }

    private Word parseWord(Cursor cursor){
        Word word = new Word();
        word.setId((cursor.getInt(0)));
        word.setDeutsch((cursor.getString(1)));
        word.setEnglisch((cursor.getString(2)));
        word.setFehler((cursor.getInt(3)));
        word.setAufrufe((cursor.getInt(4)));
        return word;
    }
}
