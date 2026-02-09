package com.example.dbstudent.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dbstudent.MyDatabaseHelper;
import com.example.dbstudent.R;
import com.example.dbstudent.Studente;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String KEY_LIST="LISTA_STUDENTI";

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
        SQLiteOpenHelper helper=new MyDatabaseHelper(this);
        ArrayList<String> studenti=new ArrayList<>();
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM STUDENTI",null);
        if(cursor.moveToFirst()) {
            int nome=cursor.getColumnIndexOrThrow("nome");
            int cognome=cursor.getColumnIndexOrThrow("cognome");
            int matricola=cursor.getColumnIndexOrThrow("matricola");
            do {
                Studente s=new Studente(cursor.getString(nome),cursor.getString(cognome),cursor.getString(matricola));
                studenti.add(s.toString());
            } while (cursor.moveToNext());
        }
        else {
            SQLiteDatabase dbWriter=helper.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("nome","Roberto");
            cv.put("cognome","Cito");
            cv.put("matricola","0512118992");
            dbWriter.insert("STUDENTI",null,cv);
        }
        cursor.close();
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studenti);
        listView.setAdapter(adapter);
    }

    public void createNewStudent(View v) {
        Intent intent=new Intent(MainActivity.this,CreateStudentActivity.class);
        startActivity(intent);
    }

    public void removeStudent(View v) {
        SQLiteOpenHelper helper = new MyDatabaseHelper(this);
        ArrayList<Studente> studenti = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM STUDENTI", null);
        if (cursor.moveToFirst()) {
            int nome = cursor.getColumnIndexOrThrow("nome");
            int cognome = cursor.getColumnIndexOrThrow("cognome");
            int matricola = cursor.getColumnIndexOrThrow("matricola");
            do {
                studenti.add(new Studente(cursor.getString(nome), cursor.getString(cognome), cursor.getString(matricola)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Intent intent = new Intent(MainActivity.this, RemoveStudentActivity.class);
        intent.putParcelableArrayListExtra(KEY_LIST, studenti);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteOpenHelper helper=new MyDatabaseHelper(this);
        ArrayList<String> studenti=new ArrayList<>();
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM STUDENTI",null);
        if(cursor.moveToFirst()) {
            int nome=cursor.getColumnIndexOrThrow("nome");
            int cognome=cursor.getColumnIndexOrThrow("cognome");
            int matricola=cursor.getColumnIndexOrThrow("matricola");
            do {
                Studente s=new Studente(cursor.getString(nome),cursor.getString(cognome),cursor.getString(matricola));
                studenti.add(s.toString());
            } while (cursor.moveToNext());
        }
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studenti);
        listView.setAdapter(adapter);
    }
}