package com.example.filesegreto;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private final static String NOME_FILE="file.txt";

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
        try(FileInputStream fis=openFileInput(NOME_FILE)) {
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String text;
            while((text=br.readLine())!=null) {
                sb.append(text);
            }
            EditText editText=findViewById(R.id.textEditor);
            editText.setText(sb.toString());
            Toast.makeText(this,"Ciao, il file esiste",LENGTH_SHORT).show();
        } catch (FileNotFoundException ignored) {
            Toast.makeText(this,"Ciao, il file non esiste",LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonPressed(View v) {
        EditText editText=findViewById(R.id.textEditor);
        try(FileOutputStream fos=openFileOutput(NOME_FILE, Context.MODE_PRIVATE)) {
            fos.write(editText.getText().toString().getBytes());
            Toast.makeText(this,"Messaggio scritto",LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}