package com.example.fabianfleischer.thevocabmaster;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;


import com.example.R;
import com.example.fabianfleischer.thevocabmaster.library.DatabaseDataSource;
import com.example.fabianfleischer.thevocabmaster.library.Word;

import java.util.List;

/**
 * BlubberBlubber
 * Created by fabian.fleischer on 22.11.2014.
 */
public class ListeActivity extends ListActivity implements View.OnClickListener {
    private DatabaseDataSource dataSource;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        dataSource= new DatabaseDataSource(this);
        dataSource.open();

        List<Word> values=dataSource.getAllWords();

        ArrayAdapter<Word> adapter= new ArrayAdapter<Word>(this,android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);
        Button addBtn;
        Button delBtn;
        addBtn = (Button) findViewById(R.id.addBtn);
        delBtn = (Button) findViewById(R.id.delBtn);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);

    }

    public void addWord(){

    }

    public void deleteFirstWord() {

    }



    @Override
    public void onClick(View view) {
        ArrayAdapter<Word> adapter = (ArrayAdapter<Word>) getListAdapter();
        Word word =null;
        switch (view.getId()) { //Checkt welches Element geklickt wurde.
            case R.id.addBtn:
                String[] words = new String[]{"Deutsch", "german"};
                word = dataSource.createWord(words[0],words[1]);
                System.out.println("Das steht in word "+word);
                adapter.add(word);
                addWord();
                break;
            case R.id.delBtn:
                deleteFirstWord();
                break;
        }
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ListeActivity.this, MenuActivity.class));
        finish();

    }
}
