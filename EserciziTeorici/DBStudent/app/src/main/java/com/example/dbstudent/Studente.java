package com.example.dbstudent;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Studente implements Parcelable {
    private String nome;
    private String cognome;
    private String matricola;

    public Studente() {}

    public Studente(String nome, String cognome, String matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    @NonNull
    @Override
    public String toString() {
        return nome+" "+cognome+" "+matricola;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(cognome);
        dest.writeString(matricola);
    }

    public static final Creator<Studente> CREATOR=new Creator<Studente>() {
        @Override
        public Studente createFromParcel(Parcel source) {
            return new Studente(source);
        }

        @Override
        public Studente[] newArray(int size) {
            return new Studente[size];
        }
    };

    public Studente(Parcel parcel) {
        this.nome=parcel.readString();
        this.cognome=parcel.readString();
        this.matricola=parcel.readString();
    }
}
