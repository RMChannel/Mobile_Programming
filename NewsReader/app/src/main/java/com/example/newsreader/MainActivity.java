package com.example.newsreader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<News> notizie;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if(savedInstanceState!=null) {
            notizie=savedInstanceState.getParcelableArrayList("notizie", News.class);
        }
        else {
            notizie=new ArrayList<>();
            notizie.add(new News("Lancio di Artemis III", "La NASA annuncia i dettagli della prossima missione lunare."));
            notizie.add(new News("Nuova AI Open Source", "Rilasciato un nuovo modello di linguaggio estremamente efficiente."));
            notizie.add(new News("Record di Velocità", "Nuovo prototipo di treno a levitazione magnetica testato con successo."));
            notizie.add(new News("Lancio Smartphone X", "Presentate le caratteristiche della fotocamera con zoom ottico 100x."));
        }
        RecyclerView rc=findViewById(R.id.recyclerView);
        rc.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter=new MyAdapter(notizie);
        rc.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("notizie",notizie);
    }
}