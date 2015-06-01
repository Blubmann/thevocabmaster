package com.example.fabianfleischer.thevocabmaster;

/**
 * Created by fabian.fleischer on 14.05.2015.
 */
public class Word {
    private long id;
    private int richtig;
    private int falsch;
    private String deutsch;
    private String englisch;

    public long getID(){
        return id;
    }

    public void setID(long id){
        this.id=id;
    }

    public String getGerman(){
        return this.deutsch;
    }

    public String gerEnglisch() {
        return this.englisch;
    }

    public void setDeutsch(String deutsch){
        this.deutsch=deutsch;
    }

    public void setEnglisch(String englisch){
        this.englisch=englisch;
    }

    public int getRichtig() { return this.richtig;}

    public int getFalsch() { return this.falsch;}

    public void incRichtig() { this.richtig++;}

    public void setRichitg(int val){this.richtig=val;}

    public void incFalsch() { this.falsch++;}

    public void setFalsch(int val){this.falsch=val;}

    public String toString() { return this.deutsch +  " / " + this.englisch; }
}
