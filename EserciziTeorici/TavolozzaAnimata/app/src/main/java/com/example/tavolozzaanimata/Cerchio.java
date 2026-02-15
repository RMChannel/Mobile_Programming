package com.example.tavolozzaanimata;

public class Cerchio {
    private float x;
    private float y;
    private int colore;

    public Cerchio() {}

    public Cerchio(float x, float y, int colore) {
        this.x = x;
        this.y = y;
        this.colore = colore;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getColore() {
        return colore;
    }

    public void setColore(int colore) {
        this.colore = colore;
    }
}
