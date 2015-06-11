package com.example.fabianfleischer.thevocabmaster;

/**
 * Created by fabian.fleischer on 20.10.2014.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.R;

import java.util.List;

public class UebenActivity extends Activity implements View.OnClickListener {
    UserFunctions userFunctions;
    Button btnLogout;

    private DatabaseDataSource dataSource;

    //Schnittstellenbezogene Variablen.
    public Button buttonWeiter;
    public Button buttonStatistik;
    public Button buttonPruefen;
    public Button buttonSprachwechsel;
    public TextView gesucht;
    public TextView ausgabe;
    public EditText eingabe;
    public String eingegeben;
    public TextView ausgabeStatistik;

    public boolean next =false;         // Zur Änderung des Weiter-Buttons
    public boolean firstpress = false;  // Für Initialisierungs-Text
    public boolean lang = true;

    private List<Word> allWords;
    private Word choosenWord;

    // Überürpüfungsfelder

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ueben);
        dataSource= new DatabaseDataSource(this);
        dataSource.open();
        initiate();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i= new Intent(this, HilfeActivity.class); //Hier wird die Hilfe aufgerufen
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }                                                            // Zeug fürs Login Funktioniert noch nicht deshalb viel auskommentiert

    //Alles was mit initialisierung zu tun hat
    public void initiate() {
        bindeOberflaecheEin();
        boolean wordexists = checkifVoclistempty();
        if (wordexists== true){
            getAllVocabs();
            waehleNeueVariable();
            elementeAusblenden();
        }
        else {
            keineVokabelnInVokabelliste();
        }

    }
    public void getAllVocabs(){
        allWords= dataSource.getAllWords();
    }            // Hier wird das Array mit Daten gelanden

    public void bindeOberflaecheEin(){

        eingabe = (EditText) findViewById(R.id.eingabeKasten);
        buttonPruefen = (Button) findViewById(R.id.buttonPruefen);
        buttonStatistik = (Button) findViewById(R.id.buttonStatistik);
        buttonWeiter = (Button) findViewById(R.id.buttonWeiter);
        buttonSprachwechsel = (Button) findViewById(R.id.buttonSprachWechsel);
        ausgabe = (TextView) findViewById(R.id.ausgabe);
        gesucht = (TextView) findViewById(R.id.gesucht);
        ausgabeStatistik = (TextView) findViewById(R.id.ausgabeStatistik);

        // In Klick-Listener Laden
        buttonWeiter.setOnClickListener(this);
        buttonStatistik.setOnClickListener(this);
        buttonPruefen.setOnClickListener(this);
        eingabe.setOnClickListener(this);
        buttonSprachwechsel.setOnClickListener(this);
    }      // Hier werden die Id's der XML-Element mit den Variablen des Java-Quelltextes verlinkt
    public void elementeAusblenden(){
        buttonWeiter.setVisibility(View.INVISIBLE);
        buttonStatistik.setVisibility(View.INVISIBLE);
        ausgabeStatistik.setVisibility(View.INVISIBLE);
    }      // Ausblenden der Elemente
    public void reset(){
        waehleNeueVariable();
        elementeAusblenden();
        buttonPruefen.setVisibility(View.VISIBLE);
        buttonWeiter.setVisibility(View.INVISIBLE);
        buttonStatistik.setVisibility(View.INVISIBLE);
        ausgabeStatistik.setVisibility(View.INVISIBLE);
        ausgabe.setText("");
        eingabe.setText("");
    }

    //Klick-Interaktion
    public void onClick(View view) {
        switch (view.getId()) {                                                 //Checkt welches Element geklickt wurde.
            case R.id.buttonPruefen:
                buttonPruefenclicked();
                break;
            case R.id.buttonStatistik:
                buttonStatistikclicked();
                break;
            case R.id.buttonWeiter:
                buttonWeiterclicked();
            case R.id.eingabeKasten:
                eingabefeldclicked();
                break;
            case R.id.buttonSprachWechsel:
                buttonSprachwechselclicked();
                break;
        }
    }           // Switch-Case welcher Button gewählt wurde
    public void buttonPruefenclicked() {
        boolean check = false;
        check = checkVocab();
        aktualisiereStatistik(check);
        ueberpruefungBeendet(check);
        entferneRichtigeVariablen(check);          // Inteligente Wahl der Variablen.

    }         // Unterscheidung welcher Zustand (Überprüfen/Weiter)
    public void buttonStatistikclicked() {
        zeigeStatistic();
    }         // Button zur Auswertung von Statistik
    public void buttonWeiterclicked(){
        waehleNeueVariable();
        buttonPruefen.setVisibility(View.VISIBLE);
        buttonWeiter.setVisibility(View.INVISIBLE);
        buttonStatistik.setVisibility(View.INVISIBLE);
        ausgabeStatistik.setVisibility(View.INVISIBLE);
        ausgabe.setText("");
        eingabe.setText("");

    }
    public void eingabefeldclicked() {

        if (firstpress == false) {
            eingabe.setText("");
            firstpress = true;
        }
    }     // Initialisierungstext im Eingabefeld (optional)
    public void buttonSprachwechselclicked(){
        //vertauscheVariablen();
        //switchStatistik();
        reset();
    }


    //Alles was mit Variablen zu tun hat
    public void waehleNeueVariable() {

        choosenWord = allWords.get(randomfunktion());
        //TODO: Wortumschaltung
        gesucht.setText(choosenWord.getGerman());
    }         // Neue zufällige Variable wird in die aktuelle Eingespeichert
    public boolean checkVocab() {
        String eingegeben = (String) eingabe.getText().toString();
        //TODO: Wortumschaltung beachten
        return (choosenWord.gerEnglisch().equals(eingegeben));
    }              // Überprüfung der Richtigkeit der Eingabe
    /**
    public void vertauscheVariablen(){
        String hilfsvar= "";
        for(int i=0; i<anzahlVoc;i++) {
            hilfsvar = vocabs[0][i];
            vocabs[0][i] = vocabs[1][i];
            vocabs[1][i] = hilfsvar;
        }
    }**/
    public void entferneRichtigeVariablen(boolean check){
        if (check== false){
            return;
        }
        else {
            int i=0;
            boolean richtigesgefunden=false;
            while(richtigesgefunden == false){

                Word equalWord = allWords.get(i);
                if (equalWord.getID()== choosenWord.getID()) {
                    allWords.remove(i);
                    richtigesgefunden=true;
                }
                i++;
            }
            if(allWords.isEmpty()) {
              keineVokabelnZuÜberprüfen();
            }

        }

    }
    //Alles was mit Statistik zu tun hat
    public void aktualisiereStatistik(boolean check){
        if (check == false)
        {
            choosenWord.incFalsch();
        }
        else{
            choosenWord.incRichtig();
        }
        dataSource.updateWord(choosenWord);

    }       // Auktualisiert die Statistic mit dem Eingegebenen Wert


    //Alle Ausgaben
    public void ausgabeErgebnis(boolean check){
        if (check == false) {
            //TODO: Umschaltung auf Englisch beachten
            ausgabe.setText("Falsch! \n Die Richtige Antwort wäre: \"" + choosenWord.gerEnglisch() + "\" gewesen");
            ausgabe.setTextColor(Color.parseColor("#FF0000"));
        } else {
            ausgabe.setText("Richtig");
            ausgabe.setTextColor(Color.parseColor("#00FF00"));
        }
    }// Ausgabe ob Richtig oder Falsch
    public void zeigeStatistic(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UebenActivity.this);
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
        }              // Anzeigen von Statistic

    //Andere Funktionen
    public int randomfunktion(){
        int zufallszahl;
        zufallszahl = (int) (Math.random() * allWords.size());
        return zufallszahl;
    }
    public void ueberpruefungBeendet(boolean check){
        ausgabeErgebnis(check);
        buttonPruefen.setVisibility(View.INVISIBLE);
        buttonWeiter.setVisibility(View.VISIBLE);
        buttonStatistik.setVisibility(View.VISIBLE);
    }       // Was wird nach der Beantwortung gemacht (WeiterButton/Statistic)

    public void keineVokabelnZuÜberprüfen(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UebenActivity.this);
        String msg = "Es wurden alle Vokabeln aus der Wortliste richtig beantwortet möchten Sie die Wortliste erneut üben ?";
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Nein",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        onBackPressed();
                    }
                });

        builder1.setPositiveButton("Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        setContentView(R.layout.ueben);
                        initiate();
                    }
                });
        AlertDialog alert = builder1.create();
        alert.show();
    }
    public void keineVokabelnInVokabelliste(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(UebenActivity.this);
        String msg = "Es sind keine Vokabeln in der Wortliste.";
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Zurück",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        onBackPressed();
                    }
                });

        builder1.setPositiveButton("Füge neue Wörter hinzu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(getApplicationContext(),
                                ListeActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        AlertDialog alert = builder1.create();
        alert.show();
    }
    // Anzeigen von Statistic

    public boolean checkifVoclistempty(){
        boolean varDrin;
            List<Word> allWords = dataSource.getAllWords();
            if(allWords.isEmpty()){
                varDrin= false;
            }
            else {
                varDrin= true;
        }
        return varDrin;
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(UebenActivity.this, MenuActivity.class));
        finish();

    }

}
