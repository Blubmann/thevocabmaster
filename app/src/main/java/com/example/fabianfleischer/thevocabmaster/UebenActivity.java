package com.example.fabianfleischer.thevocabmaster;

/**
 * Created by fabian.fleischer on 20.10.2014.
 */
import android.app.Activity;
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

public class UebenActivity extends Activity implements View.OnClickListener {
    UserFunctions userFunctions;
    Button btnLogout;

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

    public int vocnum;                  // Nummer der momentan gewählte Variable
    public int anzahlVoc = 10;          // Anzahl der Vocabeln
    public String[][] vocabs = new String[2][anzahlVoc];   // Alle Vocabeln
    public String[] ausgewVoc = new String[2];      // Die momentan Gewählte Variable

    // Überürpüfungsfelder
    public int[] anzahlueberprueft = new int[anzahlVoc];
    public int[] anzahlRichtig = new int[anzahlVoc];
    public int[] anzahlFalsch = new int[anzahlVoc];

    public int[] alt_anzahlueberprueft = new int[anzahlVoc];
    public int[] alt_anzahlRichtig = new int[anzahlVoc];
    public int[] alt_anzahlFalsch = new int[anzahlVoc];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ueben);
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
            Intent i= new Intent(this, HilfeActivity.class);
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }                                                            // Zeug fürs Login Funktioniert noch nicht deshalb viel auskommentiert

    //Alles was mit initialisierung zu tun hat
    public void initiate() {
        initResetStatistikArrays();
        bindeOberflaecheEin();
        createvocabs();
        waehleNeueVariable();
        elementeAusblenden();
    }
    public void createvocabs(){
        vocabs[0][0] = "Hallo";
        vocabs[1][0] = "hello";
        vocabs[0][1] = "gehen";
        vocabs[1][1] = "go";
        vocabs[0][2] = "schlafen";
        vocabs[1][2] = "sleep";
        vocabs[0][3] = "Haus";
        vocabs[1][3] = "house";
        vocabs[0][4] = "essen";
        vocabs[1][4] = "eat";
        vocabs[0][5] = "rennen";
        vocabs[1][5] = "run";
        vocabs[0][6] = "trinken";
        vocabs[1][6] = "drink";
        vocabs[0][7] = "spielen";
        vocabs[1][7] = "play";
        vocabs[0][8] = "Tier";
        vocabs[1][8] = "animal";
        vocabs[0][9] = "Tür";
        vocabs[1][9] = "door";
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
    public void initResetStatistikArrays(){
        for (int i=0;i<anzahlVoc;i++){
            anzahlueberprueft[i]= 0;
            anzahlRichtig[i]= 0;
            anzahlFalsch[i]= 0;
            alt_anzahlFalsch[i]= 0;
            alt_anzahlRichtig[i]= 0;
            alt_anzahlueberprueft[i]= 0;
        }
    }// Reset aller Statistike
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
        } else ;
    }     // Initialisierungstext im Eingabefeld (optional)
    public void buttonSprachwechselclicked(){
        vertauscheVariablen();
        switchStatistik();
        reset();
    }


    //Alles was mit Variablen zu tun hat
    public void waehleNeueVariable() {
        vocnum = randomfunktion();
        ausgewVoc[0] = vocabs[0][vocnum];
        ausgewVoc[1] = vocabs[1][vocnum];
        gesucht.setText(vocabs[0][vocnum]);
    }         // Neue zufällige Variable wird in die aktuelle Eingespeichert
    public boolean checkVocab() {
        String eingegeben = (String) eingabe.getText().toString();

        if (vocabs[1][vocnum].equals(eingegeben)) {
            return true;
        } else {
            return false;
        }
    }              // Überprüfung der Richtigkeit der Eingabe
    public void vertauscheVariablen(){
        String hilfsvar= "";
        for(int i=0; i<anzahlVoc;i++) {
            hilfsvar = vocabs[0][i];
            vocabs[0][i] = vocabs[1][i];
            vocabs[1][i] = hilfsvar;
        }
    }
    public void entferneRichtigeVariablen(boolean check){
        if (check== false){
            return;
        }
        else {
            if(anzahlVoc >1) {
                if (anzahlVoc - 1 == vocnum) {
                    anzahlVoc--;
                }
                else{
                    vocabs[1][vocnum] = vocabs[1][(anzahlVoc-1)];
                    vocabs[0][vocnum] = vocabs[0][(anzahlVoc-1)];
                    anzahlVoc--;
                }
            }
            else{
                ausgabe.setText("Das war die letzte Variable die noch nicht richtig beantwortet wurde !");
            }

        }

    }
    //Alles was mit Statistik zu tun hat
    public void switchStatistik(){
        int hilfsvar= 0;
        for(int i =0; i < anzahlVoc; i++) {
            hilfsvar = alt_anzahlueberprueft[i];
            alt_anzahlueberprueft[i] = anzahlueberprueft[i];
            anzahlueberprueft[i] = hilfsvar;

            hilfsvar = alt_anzahlRichtig[i];
            alt_anzahlRichtig[i] = anzahlRichtig[i];
            anzahlRichtig[i] = hilfsvar;

            hilfsvar = alt_anzahlFalsch[i];
            alt_anzahlFalsch[i] = anzahlFalsch[i];
            anzahlFalsch[i] = hilfsvar;
        }
    }
    public void aktualisiereStatistik(boolean check){
        anzahlueberprueft[vocnum]++;
        if (check == false)
        {
            anzahlFalsch[vocnum]++;
        }
        else{
            anzahlRichtig[vocnum]++;
        }
    }       // Auktualisiert die Statistic mit dem Eingegebenen Wert


    //Alle Ausgaben
    public void ausgabeErgebnis(boolean check){
        if (check == false) {
            ausgabe.setText("Falsch! \n Die Richtige Antwort wäre: \"" + vocabs[1][vocnum] + "\" gewesen");
            ausgabe.setTextColor(Color.parseColor("#FF0000"));
        } else {
            ausgabe.setText("Richtig");
            ausgabe.setTextColor(Color.parseColor("#00FF00"));
        }
    }// Ausgabe ob Richtig oder Falsch
    public void zeigeStatistic(){
        ausgabeStatistik.setVisibility(View.VISIBLE);
        ausgabeStatistik.setText("Ihre Statistik zur Vokabel " + vocabs[0][vocnum] + "/" + vocabs[1][vocnum]+ " sieht follgendermaßen aus: " +
                "\n Anzahl der Überprüfungen: " + anzahlueberprueft[vocnum] +
                "\n Anzahl richtiger Antworten: " + anzahlRichtig[vocnum] +
                "\n Anzahl falscher Antworten: " + anzahlFalsch[vocnum]);
    }              // Anzeigen von Statistic

    //Andere Funktionen
    public int randomfunktion(){
        int zufallszahl;
        zufallszahl = (int) (Math.random() * anzahlVoc);
        return zufallszahl;
    }
    public void ueberpruefungBeendet(boolean check){
        ausgabeErgebnis(check);
        buttonPruefen.setVisibility(View.INVISIBLE);
        buttonWeiter.setVisibility(View.VISIBLE);
        buttonStatistik.setVisibility(View.VISIBLE);
    }       // Was wird nach der Beantwortung gemacht (WeiterButton/Statistic)

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(UebenActivity.this, MenuActivity.class));
        finish();

    }

}
