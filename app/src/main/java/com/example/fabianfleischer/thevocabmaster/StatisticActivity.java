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


public class StatisticActivity extends ListActivity {

    public ListView vocList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        //  dataSource= new DatabaseDataSource(this);
        //  dataSource.open();
        vocList = (ListView) findViewById(android.R.id.list);

        String[] allWords = {"Fleischer", "Chillt", "sein", "Leben", "ziemlich", "hart", "Nesaf", "dsgsdgg", "sdafsdf", "ghjghj", "öoiöiojhkl", "vqweweqer"};
        fillListStatisticList(allWords);
    }
    public void fillListStatisticList(String[] allWords){
        ArrayList meineListe = new ArrayList();
        for (int i=0; i< allWords.length;i++){
            meineListe.add(allWords[i]);
        }
        ArrayAdapter listenAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meineListe);
        vocList.setAdapter(listenAdapter);
        vocList.setOnItemClickListener( new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView) view).getText().toString();
                //hier müssen Daten abhängig von Geklickten namen aus derr Daten bank gelesen werden
                showAlertBox(item, "Wort in Englisch",1,1);


            }
        });

    }

    public void showAlertBox(String wort_de, String wort_en, int anz_falsch, int anz_richtig){
        String hallo[][]= new String[1][1];

        hallo[0][0] = "Hallo";

        AlertDialog.Builder builder1 = new AlertDialog.Builder(StatisticActivity.this);
        String msg = "Deine Statistik fuer das Wort " + wort_de + " / " + wort_en + " :" + "\n Anzahl korrekter Eingaben: " + anz_richtig + "\n Anzahl falscher Eingaben: " + anz_falsch;
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
