package com.example.tavolozzaanimata;

import static android.widget.Toast.LENGTH_SHORT;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    }

    public void clearScreen(View v) {
        DrawingView d=findViewById(R.id.drawingView);
        d.clearPoints();
    }

    public void anima(View v) {
        // 1. Rotazione di 3 giri a sinistra (0 -> -1080 gradi) in 1 secondo
        // Usiamo -1080 perché 3 * 360 = 1080. Negativo = sinistra.
        Button button=findViewById(R.id.button2);
        ObjectAnimator rotazioneIniziale = ObjectAnimator.ofFloat(button, "rotation", 0f, -1080f);
        rotazioneIniziale.setDuration(1000);

        // 2. Traslazione di 200px a destra in 1 secondo
        ObjectAnimator traslazioneDestra = ObjectAnimator.ofFloat(button, "translationX", 0f, 200f);
        traslazioneDestra.setDuration(1000);

        // 3. Ritorno: Rotazione inversa (-1080 -> 0) e ritorno a sinistra (200 -> 0) in 1 secondo
        ObjectAnimator rotazioneRitorno = ObjectAnimator.ofFloat(button, "rotation", -1080f, 0f);
        ObjectAnimator traslazioneRitorno = ObjectAnimator.ofFloat(button, "translationX", 200f, 0f);

        // Creiamo un set per far avvenire il ritorno (punto 3) contemporaneamente
        AnimatorSet faseRitorno = new AnimatorSet();
        faseRitorno.playTogether(rotazioneRitorno, traslazioneRitorno);
        faseRitorno.setDuration(1000);

        // Costruiamo la sequenza finale
        AnimatorSet sequenzaCompleta = new AnimatorSet();
        sequenzaCompleta.playSequentially(rotazioneIniziale, traslazioneDestra, faseRitorno);

        // Avvia l'animazione
        sequenzaCompleta.start();
    }
}