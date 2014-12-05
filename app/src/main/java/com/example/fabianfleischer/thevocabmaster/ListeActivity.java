package com.example.fabianfleischer.thevocabmaster;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.R;
import com.example.fabianfleischer.thevocabmaster.library.Word;
import com.example.fabianfleischer.thevocabmaster.library.WordListOperation;

import java.sql.SQLException;
import java.util.List;

/**
 * BlubberBlubber
 * Created by fabian.fleischer on 22.11.2014.
 */
public class ListeActivity extends ListActivity implements View.OnClickListener {
    private WordListOperation wDBop;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);

        Button addBtn;
        Button delBtn;
        addBtn = (Button) findViewById(R.id.addBtn);
        delBtn = (Button) findViewById(R.id.delBtn);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        wDBop = new WordListOperation(this);
        try {
            wDBop.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List values =wDBop.getAllWords();
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2,values);
        setListAdapter(adapter);
    }

    public void addWord(){
        ArrayAdapter adapter = (ArrayAdapter)getListAdapter();
        EditText text =(EditText)findViewById(R.id.deutsch);
        EditText text2 =(EditText)findViewById(R.id.englisch);
        Word word = wDBop.addWord(text.getText().toString(),text2.getText().toString());
        adapter.add(word);
    }

    public void deleteFirstWord() {
        ArrayAdapter adapter = (ArrayAdapter)getListAdapter();
        Word word =null;

        if(getListAdapter().getCount() >0) {
            word = (Word)getListAdapter().getItem(0);
            wDBop.deleteWord(word);
            adapter.remove(word);
        }
    }

    protected void onResume() {
        try {
            wDBop.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    protected void onPause() {
        try {
            wDBop.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) { //Checkt welches Element geklickt wurde.
            case R.id.addBtn:
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
