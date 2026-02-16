package com.esame.doppotris;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MappaTris {
    private final ArrayList<ArrayList<Integer>> mappa;

    public MappaTris() {
        mappa = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> temp=new ArrayList<>();
            for(int j=0;j<3;j++) temp.add(0);
            mappa.add(temp);
        }
    }

    public boolean isOccupied(int x, int y) {
        return mappa.get(x).get(y) != 0;
    }

    public void setOccupied(int x, int y, int player) {
        mappa.get(x).set(y, player);
    }

    public int getOccupied(int x, int y) {
        return mappa.get(x).get(y);
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mappa.get(i).set(j, 0);
            }
        }
    }

    public int hasWon() {
        if(this.getOccupied(0,0)==this.getOccupied(1,1) && this.getOccupied(1,1)==this.getOccupied(2,2)) return this.getOccupied(0,0);
        for(int i = 0;i < 3; i++) {
            boolean control=true;
            for(int j = 0; j < 3; j++) {
                if (this.getOccupied(i, j) != this.getOccupied(i, 0)) control = false;
            }
            if(control) return this.getOccupied(i,0);
        }
        for(int j = 0; j < 3; j++) {
            boolean control=true;
            for(int i = 0; i < 3; i++) {
                if (this.getOccupied(i, j) != this.getOccupied(0, j)) control = false;
            }
            if(control) return this.getOccupied(0, j);
        }
        return 0;
    }
}
