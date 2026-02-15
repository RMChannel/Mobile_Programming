package com.example.newsreader;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsListFragment.OnNewsSelectedListener {

    private boolean isDualPane;
    private ArrayList<News> newsList;
    private News selectedNews; // Tracciamo la selezione corrente

    private static final String KEY_NEWS_LIST = "notizie_lista";
    private static final String KEY_SELECTED_NEWS = "notizia_selezionata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDualPane = findViewById(R.id.detail_container) != null;

        if (savedInstanceState == null) {
            // Primo avvio: inizializziamo i dati
            newsList = new ArrayList<>();
            newsList.add(new News("Lancio Spaziale", "La missione Artemis è partita..."));
            newsList.add(new News("Nuova AI", "Presentato un nuovo modello..."));
            newsList.add(new News("Meteo Weekend", "Sole ovunque..."));
        } else {
            // Recuperiamo sia la lista che l'eventuale selezione precedente
            newsList = savedInstanceState.getParcelableArrayList(KEY_NEWS_LIST);
            selectedNews = savedInstanceState.getParcelable(KEY_SELECTED_NEWS);
        }

        // GESTIONE DINAMICA DEI FRAMMENTI (Reflow)
        if (isDualPane) {
            // MODALITÀ TABLET
            // 1. Carichiamo sempre la lista a sinistra
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, NewsListFragment.newInstance(newsList))
                    .commit();

            // 2. Gestiamo la parte destra
            if (selectedNews != null) {
                // Se c'era una notizia selezionata (es. veniamo da smartphone), mostriamola
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, NewsDetailFragment.newInstance(selectedNews))
                        .commit();
            } else {
                // Altrimenti mostriamo il placeholder/benvenuto
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new NewsDetailFragment())
                        .commit();
            }
        } else {
            // MODALITÀ SMARTPHONE
            // Se c'era una notizia selezionata, mostriamo subito il dettaglio
            if (selectedNews != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, NewsDetailFragment.newInstance(selectedNews))
                        .commit();
            } else {
                // Altrimenti mostriamo la lista
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, NewsListFragment.newInstance(newsList))
                        .commit();
            }
        }
    }

    @Override
    public void onNewsSelected(News news) {
        selectedNews = news; // Memorizziamo la scelta

        if (isDualPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, NewsDetailFragment.newInstance(news))
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, NewsDetailFragment.newInstance(news))
                    .addToBackStack(null) // Permette di tornare alla lista con il tasto back
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_NEWS_LIST, newsList);
        outState.putParcelable(KEY_SELECTED_NEWS, selectedNews);
    }

    // Gestione tasto back su smartphone per pulire la selezione
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isDualPane) {
            // Se torniamo indietro dal dettaglio alla lista su smartphone,
            // resettiamo la selezione così al prossimo switch non riapre il dettaglio
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                selectedNews = null;
            }
        }
    }
}
