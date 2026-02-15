package com.example.threadexample;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class Task extends AsyncTask<Void, Integer, String> {

    private ProgressBar progressBar;

    public Task(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(50);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Completato!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        progressBar.setVisibility(View.INVISIBLE);
    }
}