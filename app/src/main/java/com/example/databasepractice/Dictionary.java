package com.example.databasepractice;

public class Dictionary {
    private String word, def;
    private int id;

    public Dictionary(String word, String def, int id) {
        this.word = word;
        this.def = def;
        this.id= id;
    }

    public Dictionary() {

    }

    public String getWord() {
        return word;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def= def;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getId() { return id; }
}
