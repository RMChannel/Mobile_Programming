package com.example.votesharing;

import static android.widget.Toast.LENGTH_SHORT;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        caricaStudenti();
    }

    public void caricaStudenti() {
        Uri uri = Uri.parse("content://com.example.dbstudent/studenti");

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        List<String> studenti=new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String cognome = cursor.getString(cursor.getColumnIndexOrThrow("cognome"));
                String matricola = cursor.getString(cursor.getColumnIndexOrThrow("matricola"));
                studenti.add(nome+" "+cognome+" "+matricola);
            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayAdapter<String> list=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,studenti);
        ListView l=findViewById(R.id.lista);
        l.setAdapter(list);
    }
}