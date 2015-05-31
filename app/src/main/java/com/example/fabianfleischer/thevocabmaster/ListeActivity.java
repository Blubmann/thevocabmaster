package com.example.fabianfleischer.thevocabmaster;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * BlubberBlubber
 * Created by fabian.fleischer on 22.11.2014.
 */
public class ListeActivity extends ListActivity implements View.OnClickListener {
    private DatabaseDataSource dataSource;
    EditText deutsch;
    EditText englisch;
    Button addBtn;
    Button delBtn;
    ListView wordList;
    ArrayList liste;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        dataSource= new DatabaseDataSource(this);
        dataSource.open();
        wordList= (ListView) findViewById(android.R.id.list);
        List<Word> values=dataSource.getAllWords();
        ArrayAdapter<Word> adapter= new ArrayAdapter<Word>(this,android.R.layout.simple_list_item_1,values);
        wordList.setAdapter(adapter);

        liste = new ArrayList();

        deutsch = (EditText) findViewById(R.id.deutsch);
        englisch = (EditText) findViewById(R.id.englisch);
        addBtn = (Button) findViewById(R.id.addBtn);
        delBtn = (Button) findViewById(R.id.delBtn);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        ArrayAdapter adapter;
        Word word =null;
        switch (view.getId()) { //Checkt welches Element geklickt wurde.
            case R.id.addBtn:
                String[] words = new String[]{deutsch.getText().toString(), englisch.getText().toString()};
                word = dataSource.createWord(words[0],words[1]);
                System.out.println("Das steht in word " + word);
                String en = word.gerEnglisch();
                String de = word.getGerman();
                String ausgabe = de + " / "+ en;
                liste.add(ausgabe);
                adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,liste);;
                wordList.setAdapter(adapter);
                Toast.makeText(this, "Wörter hinzugefügt", Toast.LENGTH_LONG).show();
                break;
            case R.id.delBtn:
             /**   if (getListAdapter().getCount() > 0) {
                    word = (Word) getListAdapter().getItem(0);
                    dataSource.deleteWord(word);
                    adapter.remove();
                }**/
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
