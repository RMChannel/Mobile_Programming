package com.example.newsreader;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class News implements Parcelable {
    private String titolo;
    private String testo;

    public News() {}

    public News(String titolo, String testo) {
        this.titolo = titolo;
        this.testo = testo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(titolo);
        dest.writeString(testo);
    }

    public static final Creator<News> CREATOR=new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public News(Parcel source) {
        this.titolo = source.readString();
        this.testo = source.readString();
    }
}
