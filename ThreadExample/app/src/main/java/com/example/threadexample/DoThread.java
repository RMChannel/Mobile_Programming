package com.example.threadexample;

import android.view.View;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class DoThread extends Thread {
    private View v;

    public DoThread(View v) {
        this.v=v;
    }
    @Override
    public void run() {
        for(int i=0;i<=10;i++) {
            int finalI = i;
            v.post(new Runnable() {
                @Override
                public void run() {
                    onProgressIndicate(finalI);
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void onProgressIndicate(int value) {
        if(value==0) v.setVisibility(View.VISIBLE);
        else if(value==10) v.setVisibility(View.INVISIBLE);
        LinearProgressIndicator l=(LinearProgressIndicator) v;
        l.setProgress(value*10,true);
    }
}
