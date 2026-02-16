package com.esame.doppotris;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MappaTris mappa;
    private int turno;
    private ArrayList<Integer> mappa1;
    private ArrayList<Integer> mappa2;
    private boolean victory;

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
        mappa=new MappaTris();
        turno=1;
        mappa1=new ArrayList<>();
        mappa2=new ArrayList<>();
        for(int i=0;i<9;i++){
            mappa1.add(getResources().getIdentifier("btn_p1_"+i, "id", getPackageName()));
            mappa2.add(getResources().getIdentifier("btn_p2_"+i, "id", getPackageName()));
        }
        victory=false;
    }

    public char getCharOfVictory() {
        if(mappa.hasWon()==1) return 'X';
        else return 'O';
    }

    public void buttonClicked(View view) {
        if(victory) {
            Toast.makeText(this, "Ha vinto il giocatore "+getCharOfVictory()+"!!", Toast.LENGTH_SHORT).show();
            return;
        }
        int x, y;
        x=view.getTag().toString().charAt(0)-'0';
        y=view.getTag().toString().charAt(2)-'0';
        if(x!=turno) Toast.makeText(this, "Non è il tuo turno", Toast.LENGTH_SHORT).show();
        else {
            x=y%3;
            y=y/3;
            if(mappa.isOccupied(x, y)) Toast.makeText(this, "Posizione occupata", Toast.LENGTH_SHORT).show();
            else {
                mappa.setOccupied(x, y, turno);
                aggiornaMappa();
                if(turno==1) turno=2;
                else turno=1;
                int vinctore=mappa.hasWon();
                if(vinctore!=0) {
                    victory = true;
                    Toast.makeText(this, "Ha vinto il giocatore "+getCharOfVictory()+"!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void aggiornaMappa() {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(mappa.getOccupied(j, i)==1) {
                    findViewById(mappa1.get(i*3+j)).setBackgroundResource(R.drawable.x);
                    findViewById(mappa2.get(i*3+j)).setBackgroundResource(R.drawable.x);
                }
                else if(mappa.getOccupied(j, i)==2) {
                    findViewById(mappa1.get(i*3+j)).setBackgroundResource(R.drawable.o);
                    findViewById(mappa2.get(i*3+j)).setBackgroundResource(R.drawable.o);
                }
                else {
                    findViewById(mappa1.get(i*3+j)).setBackgroundResource(R.drawable.button_border);
                    findViewById(mappa2.get(i*3+j)).setBackgroundResource(R.drawable.button_border);
                }
            }
        }
    }

    public void reset(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Conferma reset");
        builder.setMessage("Sei sicuro di voler riavviare la partita??");

        builder.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                victory=false;
                turno=1;
                mappa=new MappaTris();
                aggiornaMappa();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}