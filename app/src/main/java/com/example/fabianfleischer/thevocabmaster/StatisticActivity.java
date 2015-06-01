package com.example.fabianfleischer.thevocabmaster;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

import java.util.ArrayList;
import java.util.List;


public class StatisticActivity extends ListActivity {

    private DatabaseDataSource dataSource;
    public ListView vocList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
          dataSource= new DatabaseDataSource(this);
          dataSource.open();
        List<Word> allWords= dataSource.getAllWords();

        vocList = (ListView) findViewById(android.R.id.list);
        fillListStatisticList(allWords);
    }
    public void fillListStatisticList(List<Word> allWords){

        ArrayList meineListe = (ArrayList) allWords;
        ArrayAdapter listenAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meineListe);
        vocList.setAdapter(listenAdapter);
        vocList.setOnItemClickListener( new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Word choosenWord = (Word) parent.getAdapter().getItem(position);
                showAlertBox(choosenWord);


            }
        });

    }

    public void showAlertBox(Word choosenWord){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(StatisticActivity.this);
        String msg = "Deine Statistik fuer das Wort " + choosenWord.getGerman() + " / " + choosenWord.gerEnglisch() + " :" + "\n Anzahl korrekter Eingaben: " + choosenWord.getRichtig() + "\n Anzahl falscher Eingaben: " + choosenWord.getFalsch();
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setNeutralButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder1.create();
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(StatisticActivity.this, MenuActivity.class));
        finish();

    }
}
