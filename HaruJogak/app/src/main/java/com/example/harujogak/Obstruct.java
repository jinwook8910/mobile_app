package com.example.harujogak;

public class Obstruct {
    private String obstruction;
    private int frequency;

    public Obstruct(){

    }
    public Obstruct(String ob, int fre){
        this.obstruction = ob;
        this.frequency = fre;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setObstruction(String obstruction) {
        this.obstruction = obstruction;
    }

    public String getObstruction() {
        return obstruction;
    }
}