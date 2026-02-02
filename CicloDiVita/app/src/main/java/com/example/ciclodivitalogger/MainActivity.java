package com.example.ciclodivitalogger;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        Log.d("debug","Applicazione creata");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debug","Applicazione avviata");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("debug","Applicazione riavviata");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debug","Applicazione ripresa");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug","Applicazione in pausa");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug","Applicazione fermata");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug","Applicazione distrutta");
    }
}