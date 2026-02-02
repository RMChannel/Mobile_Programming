package com.example.calcolatrice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private double n=0;
    private double result=0;
    private char operationSelected='+';
    private boolean firstOperation=true;
    private double memory=0;
    private boolean virgola=false;
    private int countCifre=0;

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
        if(savedInstanceState!=null) {
            n=savedInstanceState.getDouble("n");
            result=savedInstanceState.getDouble("result");
            operationSelected=savedInstanceState.getChar("operationSelected");
            firstOperation=savedInstanceState.getBoolean("firstOperation");
            memory=savedInstanceState.getDouble("memory");
            virgola=savedInstanceState.getBoolean("virgola");
            countCifre=savedInstanceState.getInt("countCifre");
        }
        updateDisplays();
        updateMemoryDisplay();
    }

    public void addNumber(View view) {
        Button b=(Button) view;
        if(!virgola) {
            n= (n*10)+Integer.parseInt(b.getText().toString());
            updateDisplays();
        }
        else {
            countCifre++;
            n=n+Integer.parseInt(b.getText().toString())/Math.pow(10,countCifre);
            n=Math.round(n*Math.pow(10,countCifre))/Math.pow(10,countCifre);
            updateDisplays();
        }
    }

    public void operation(View view) {
        Button b=(Button) view;
        operationSelected=b.getText().charAt(0);
        if(firstOperation) {
            firstOperation=false;
            result=n;
        }
        else {
            switch (operationSelected) {
                case '+':
                    result+=n;
                    break;
                case '-':
                    result-=n;
                    break;
                case '*':
                    result*=n;
                    break;
                case '/':
                    result/=n;
                    break;
            }
        };
        n=0;
        virgola=false;
        countCifre=0;
        updateDisplays();
    }

    public void equals(View view) {
        switch (operationSelected) {
            case '+':
                result+=n;
                break;
            case '-':
                result-=n;
                break;
            case '*':
                result*=n;
                break;
            case '/':
                result/=n;
                break;
        }
        n=0;
        virgola=false;
        countCifre=0;
        updateDisplays();
    }

    public void updateDisplays() {
        TextView display1=(TextView) findViewById(R.id.display1);
        TextView display2=(TextView) findViewById(R.id.display2);
        display1.setText(String.valueOf(n));
        display2.setText(String.valueOf(result));
    }

    public void reset(View view) {
        n=0;
        result=0;
        firstOperation=true;
        virgola=false;
        countCifre=0;
        operationSelected='+';
        updateDisplays();
    }

    public void updateMemoryDisplay() {
        TextView memoryText=(TextView) findViewById(R.id.memoryText);
        memoryText.setText("Memory: "+String.valueOf(memory));

    }
    public void memoryClear(View view) {
        memory=0;
        updateMemoryDisplay();
    }

    public void memorySave(View view) {
        memory=result;
        updateMemoryDisplay();
    }

    public void memoryRecall(View view) {
        n=memory;
        updateDisplays();
    }

    public void activateVirgola(View view) {
        virgola=true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("n",n);
        outState.putDouble("result",result);
        outState.putChar("operationSelected",operationSelected);
        outState.putBoolean("firstOperation",firstOperation);
        outState.putDouble("memory",memory);
        outState.putBoolean("virgola",virgola);
        outState.putInt("countCifre",countCifre);
    }
}