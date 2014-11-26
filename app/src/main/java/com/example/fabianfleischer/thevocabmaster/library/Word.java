package com.example.fabianfleischer.thevocabmaster.library;

/**
 * Created by fabian.fleischer on 23.11.2014.
 */
public class Word {
    private int id;
    private String deutsch;
    private String englisch;
    private int fehler;
    private int aufrufe;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public int getFehler(){
        return fehler;
    }
    public void setFehler(int fehler){
        this.fehler=fehler;
    }

    public int getAufrufe(){
        return aufrufe;
    }
    public void setAufrufe(int aufrufe){
        this.aufrufe=aufrufe;
    }

    public String getDeutsch(){
        return this.deutsch;
    }

    public void setDeutsch(String deutsch){
        this.deutsch=deutsch;
    }

    public String getEnglisch(){
        return this.englisch;
    }

    public void setEnglisch(String englisch){
        this.englisch=englisch;
    }
}
