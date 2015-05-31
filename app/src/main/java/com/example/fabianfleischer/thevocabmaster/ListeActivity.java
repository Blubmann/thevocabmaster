package com.example.fabianfleischer.thevocabmaster;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    ArrayList liste = new ArrayList();;
    Word choosenWord;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        dataSource= new DatabaseDataSource(this);
        dataSource.open();
        wordList= (ListView) findViewById(android.R.id.list);
        List<Word> values= dataSource.getAllWords();
        liste= (ArrayList) values;
        ArrayAdapter<Word> adapter= new ArrayAdapter<Word>(this,android.R.layout.simple_list_item_1,liste);
        wordList.setAdapter(adapter);
        wordList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                choosenWord = (Word) parent.getAdapter().getItem(position);
                openDeleteWordAlert();
            }
        });

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
                liste.add(word);
                adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,liste);
                wordList.setAdapter(adapter);

                Toast.makeText(this, "Woerter hinzugefuegt", Toast.LENGTH_LONG).show();
                break;
            case R.id.delBtn:
               // if (getListAdapter().getCount() > 0) {
                //    word = (Word) getListAdapter().getItem(0);
               //     dataSource.deleteWord(word);
              //      adapter.remove();
              //  }
                break;
        }
    }

    public void openDeleteWordAlert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ListeActivity.this);
        builder1.setMessage("Wollen Sie die Variable: " + choosenWord.toString() + " wirklich loeschen");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dataSource.deleteWord(choosenWord);
                        aktualisiereTabelle();
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("Nein",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder1.create();
        alert.show();
    }

    private void aktualisiereTabelle() {
        List<Word> values= dataSource.getAllWords();
        liste= (ArrayList) values;
        ArrayAdapter<Word> adapter= new ArrayAdapter<Word>(this,android.R.layout.simple_list_item_1,liste);
        wordList.setAdapter(adapter);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ListeActivity.this, MenuActivity.class));
        finish();

    }
}
