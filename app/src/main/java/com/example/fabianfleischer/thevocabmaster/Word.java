package com.example.fabianfleischer.thevocabmaster;

/**
 * Created by fabian.fleischer on 14.05.2015.
 */
public class Word {
    private long id;
    private String deutsch;
    private String englisch;

    public long getID(){
        return id;
    }

    public void setID(long id){
        this.id=id;
    }

    public String getGerman(){
        return deutsch;
    }

    public String gerEnglisch() {
        return englisch;
    }

    public void setDeutsch(String deutsch){
        this.deutsch=deutsch;
    }

    public void setEnglisch(String englisch){
        this.englisch=englisch;
    }

}
