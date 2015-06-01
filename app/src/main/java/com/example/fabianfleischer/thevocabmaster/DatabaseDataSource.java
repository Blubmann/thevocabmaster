package com.example.fabianfleischer.thevocabmaster;

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
    private String[] allColumns={DatabaseOwnWords.COLUMN_ID,DatabaseOwnWords.COLUMN_DEUTSCH,DatabaseOwnWords.COLUMN_ENGLISCH,DatabaseOwnWords.COLUMN_ANZ_RICHTIG,DatabaseOwnWords.COLUMN_ANZ_FALSCH};

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
        values.put(DatabaseOwnWords.COLUMN_ANZ_RICHTIG, 0);
        values.put(DatabaseOwnWords.COLUMN_ANZ_FALSCH, 0);
        long insertID=database.insert(DatabaseOwnWords.TABLE_WORDS, null, values);
        System.out.println("Das steht in insertID "+values);
        Cursor cursor = database.query(DatabaseOwnWords.TABLE_WORDS,allColumns,DatabaseOwnWords.COLUMN_ID + "=" + insertID,null,null,null,null);
        cursor.moveToFirst();
        Word newWord = cursorToWord(cursor);
        cursor.close();
        System.out.println("Das steht in newWord " + newWord);
        return newWord;
    }

    public void updateWord(Word word){
        ContentValues values = new ContentValues();
        values.put(DatabaseOwnWords.COLUMN_ANZ_RICHTIG, word.getRichtig());
        values.put(DatabaseOwnWords.COLUMN_ANZ_FALSCH, word.getFalsch());
        database.update(DatabaseOwnWords.TABLE_WORDS,values, DatabaseOwnWords.COLUMN_ID + " = ?",
                new String[] { String.valueOf(word.getID())});
    }

    public String[] getSingleWords(int id)
    {
        String[] words = new String[2];
        Cursor cursor = database.rawQuery("SELECT * FROM words WHERE _id="+id+"", null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            words[0] = cursor.getString(cursor.getColumnIndex(DatabaseOwnWords.COLUMN_DEUTSCH));
            words[1] = cursor.getString(cursor.getColumnIndex(DatabaseOwnWords.COLUMN_ENGLISCH));
            cursor.close();
            System.out.println(words[0]);
            System.out.println(words[1]);
        }
        return words;
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
        word.setRichitg(cursor.getInt(3));
        word.setFalsch(cursor.getInt(4));
        return word;
    }
}
