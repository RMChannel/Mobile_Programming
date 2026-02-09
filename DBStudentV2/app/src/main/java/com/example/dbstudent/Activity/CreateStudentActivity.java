package com.example.dbstudent.Activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dbstudent.MyDatabaseHelper;
import com.example.dbstudent.R;

public class CreateStudentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void createStudent(View v) {
        String nome=((EditText) findViewById(R.id.textNome)).getText().toString();
        String cognome=((EditText) findViewById(R.id.textCognome)).getText().toString();
        String matricola=((EditText) findViewById(R.id.textMatricola)).getText().toString();
        if(nome.isEmpty()) Toast.makeText(this,"Il campo nome è vuoto",LENGTH_SHORT).show();
        if(cognome.isEmpty()) Toast.makeText(this,"Il campo cognome è vuoto",LENGTH_SHORT).show();
        if(matricola.isEmpty()) Toast.makeText(this,"Il campo matricola è vuoto",LENGTH_SHORT).show();
        if(!nome.isEmpty() && !cognome.isEmpty() && !matricola.isEmpty()) {
            ContentValues cv=new ContentValues();
            cv.put("nome",nome);
            cv.put("cognome",cognome);
            cv.put("matricola",matricola);
            SQLiteOpenHelper helper=new MyDatabaseHelper(this);
            SQLiteDatabase dbWriter=helper.getWritableDatabase();
            dbWriter.insert("STUDENTI",null,cv);
            dbWriter.close();
            helper.close();
            Toast.makeText(this,"Studente aggiunto con successo",LENGTH_SHORT).show();
            finish();
        }
    }
}
