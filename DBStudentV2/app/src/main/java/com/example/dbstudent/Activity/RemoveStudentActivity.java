package com.example.dbstudent.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dbstudent.MyDatabaseHelper;
import com.example.dbstudent.R;
import com.example.dbstudent.Studente;

import java.util.ArrayList;

public class RemoveStudentActivity extends AppCompatActivity {

    private ArrayList<Studente> studenti;
    private Studente studenteSelezionato = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        studenti = getIntent().getParcelableArrayListExtra("LISTA_STUDENTI");
        if (studenti == null) {
            studenti = new ArrayList<>();
        }

        ListView listView = findViewById(R.id.listView);
        Button btnAction = findViewById(R.id.btnAction);

        ArrayAdapter<Studente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studenti);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            studenteSelezionato = studenti.get(position);
            Toast.makeText(RemoveStudentActivity.this, "Selezionato: " + studenteSelezionato.toString(), Toast.LENGTH_SHORT).show();
        });

        btnAction.setOnClickListener(v -> {
            if (studenteSelezionato != null) {
                rimuoviDalDatabase(studenteSelezionato);
            } else {
                Toast.makeText(this, "Seleziona prima uno studente!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rimuoviDalDatabase(Studente s) {
        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("STUDENTI", "matricola = ?", new String[]{s.getMatricola()});
        db.close();

        Toast.makeText(this, "Studente rimosso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
