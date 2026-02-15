package com.example.dbstudent;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServiceProvider extends ContentProvider {
    private static final String AUTHORITY="com.example.dbstudent";
    private static final String PATH_STUDENTI="studenti";
    private static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/"+PATH_STUDENTI);

    private static final UriMatcher urimMatcher =new UriMatcher(UriMatcher.NO_MATCH);
    static {
        urimMatcher.addURI(AUTHORITY,PATH_STUDENTI,1); //Restituisce tutti gli studenti
        urimMatcher.addURI(AUTHORITY,PATH_STUDENTI+"/#",2); //Restituisce lo studente singolo
    }

    private MyDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper =new MyDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        return db.query("STUDENTI",projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "Student";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }
}
