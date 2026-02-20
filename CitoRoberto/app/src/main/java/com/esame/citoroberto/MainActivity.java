package com.esame.citoroberto;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

        CursorView cursorView = findViewById(R.id.cursorView);

        Button btnUp = findViewById(R.id.btnUp);
        Button btnDown = findViewById(R.id.btnDown);
        Button btnLeft = findViewById(R.id.btnLeft);
        Button btnRight = findViewById(R.id.btnRight);
        Button btnReset = findViewById(R.id.btnReset);

        btnUp.setOnClickListener(v -> cursorView.move(CursorView.DIR_UP));
        btnDown.setOnClickListener(v -> cursorView.move(CursorView.DIR_DOWN));
        btnLeft.setOnClickListener(v -> cursorView.move(CursorView.DIR_LEFT));
        btnRight.setOnClickListener(v -> cursorView.move(CursorView.DIR_RIGHT));
        btnReset.setOnClickListener(v -> cursorView.resetGame());

        //dialog di vittoria
        cursorView.setWinCallback(() -> new AlertDialog.Builder(this)
                .setTitle("Hai vinto!")
                .setMessage("Sei riuscito ad arrivare in alto!")
                .setPositiveButton("Gioca ancora", (d, w) -> cursorView.resetGame())
                .setNegativeButton("Chiudi", null)
                .setCancelable(false)
                .show());
    }
}