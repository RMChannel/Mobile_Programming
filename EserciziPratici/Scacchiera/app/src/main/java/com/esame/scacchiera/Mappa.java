package com.esame.scacchiera;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class Mappa {
    private final ArrayList<ArrayList<Integer>> mappa;

    @RequiresApi(api = Build.VERSION_CODES.VANILLA_ICE_CREAM)
    public Mappa() {
        this.mappa=new ArrayList<>();
        for(int i=0;i<3;i++) {
            ArrayList<Integer> temp=new ArrayList<>();
            for(int j=0;j<3;j++) temp.add(0);
            mappa.add(temp);
        }
        randomGenerate();
    }

    public void set(int x, int y, int value) {
        mappa.get(x).set(y,value);
    }

    public int get(int x, int y) {
        return mappa.get(x).get(y);
    }

    @RequiresApi(api = Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private void randomGenerate() {
        String base="123456789";
        Random random=new Random();
        int i=0;
        int j=0;
        while(!base.isEmpty()) {
            int r=random.nextInt(0,base.length());
            set(i,j, (int) base.charAt(r));
            base = base.substring(0, r) + base.substring(r + 1);
            j++;
            if(j==3) {
                i+=1;
                j=0;
            }
        }
    }

    public void moveX(int x, int direction) {
        ArrayList<Integer> temp=mappa.get(x);
        ArrayList<Integer> temp2=new ArrayList<>();
        if(direction==1) { //right
            temp2.add(temp.get(2));
            temp2.add(temp.get(0));
            temp2.add(temp.get(1));
        }
        else { //left
            temp2.add(temp.get(1));
            temp2.add(temp.get(2));
            temp2.add(temp.get(0));
        }
        mappa.set(x,temp2);
    }

    public void moveY(int y, int direction) {
        int y0=mappa.get(0).get(y);
        int y1=mappa.get(1).get(y);
        int y2=mappa.get(2).get(y);
        if(direction==1) {
            mappa.get(0).set(y,y2);
            mappa.get(1).set(y,y0);
            mappa.get(2).set(y,y1);
        }
        else {
            mappa.get(0).set(y,y1);
            mappa.get(1).set(y,y2);
            mappa.get(2).set(y,y0);
        }
    }

    public boolean hasWon() {
        int old=0;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(old>mappa.get(i).get(j)) return false;
                else old=mappa.get(i).get(j);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                sb.append(i).append(",").append(j).append("=").append(get(i, j));
            }
        }
        return sb.toString();
    }
}
