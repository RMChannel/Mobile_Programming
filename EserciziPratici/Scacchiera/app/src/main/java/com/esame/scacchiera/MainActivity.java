package com.esame.scacchiera;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Mappa mappa;
    private ArrayList<Integer> mappaID;
    private int count;
    private boolean victory;

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

        mappa=new Mappa();
        mappaID=new ArrayList<>();
        count=0;
        victory=false;

        android.widget.TableLayout grid = findViewById(R.id.mainGrid);
        for(int i=0; i<grid.getChildCount(); i++) {
            android.view.View child = grid.getChildAt(i);
            if(child instanceof android.widget.TableRow) {
                android.widget.TableRow row = (android.widget.TableRow) child;
                for(int j=0; j<row.getChildCount(); j++) {
                    android.view.View cell = row.getChildAt(j);
                    if(cell instanceof android.widget.TextView) {
                        if (cell.getId() == android.view.View.NO_ID) {
                            cell.setId(android.view.View.generateViewId());
                        }
                        cell.setTag(i + "," + j);
                        mappaID.add(cell.getId());
                    }
                }
            }
        }

        stampaMappa();
    }

    public void stampaMappa() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                TextView text=findViewById(mappaID.get(i*3+j));
                text.setText(String.valueOf((char)mappa.get(i,j)));
            }
        }
        TextView text=findViewById(R.id.displayValue);
        text.setText(String.valueOf(count));
        Log.d("MAPPA",mappa.toString());
    }

    public void moveX(View view) {
        if(victory) {
            Toast.makeText(this,"Hai vinto!!!",LENGTH_SHORT).show();
            return;
        }
        String direction=view.getTag().toString();
        int x=0;
        if(direction.contains("2")) x=1;
        else if(direction.contains("3")) x=2;
        if(direction.contains("left")) {
            mappa.moveX(x,0);
        }
        else if(direction.contains("right")) {
            mappa.moveX(x,1);
        }
        count++;
        stampaMappa();
        if(mappa.hasWon()) mostraVittoria();
    }

    public void moveY(View view) {
        if(victory) {
            Toast.makeText(this,"Hai vinto!!!",LENGTH_SHORT).show();
            return;
        }
        String direction=view.getTag().toString();
        int y=0;
        if(direction.contains("2")) y=1;
        else if(direction.contains("3")) y=2;
        if(direction.contains("up")) {
            mappa.moveY(y,0);
        }
        else if(direction.contains("down")) {
            mappa.moveY(y,1);
        }
        count++;
        stampaMappa();
        if(mappa.hasWon()) mostraVittoria();
    }

    private void mostraVittoria() {
        victory=true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Complimenti! 🎉");
        builder.setMessage("Hai vinto la partita! Ottimo lavoro.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void reset(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Conferma reset");
        builder.setMessage("Sei sicuro di voler riavviare la partita??");

        builder.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mappa=new Mappa();
                count=0;
                victory=false;
                stampaMappa();
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