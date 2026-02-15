package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class PlayerService extends Service {

    // 1. Definiamo le azioni
    public static final String ACTION_PLAY = "com.example.musicplayer.PLAY";
    public static final String ACTION_PAUSE = "com.example.musicplayer.PAUSE";
    public static final String ACTION_STOP = "com.example.musicplayer.STOP";
    private static final String CHANNEL_ID = "MUSIC_CHANNEL";

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel(); // Meglio crearlo una volta sola qui
        mediaPlayer = MediaPlayer.create(this, R.raw.siamounasquadrafortissimi);
        mediaPlayer.setLooping(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 2. Controlliamo se l'intent contiene un'azione (Play/Pause/Stop)
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case ACTION_PLAY:
                    resume();
                    break;
                case ACTION_PAUSE:
                    pause();
                    break;
                case ACTION_STOP:
                    stopSelf(); // Ferma il servizio completamente
                    return START_NOT_STICKY;
            }
        }
        else {
            mediaPlayer.start();
        }

        // 3. Creiamo la notifica con i tasti
        showNotification();
        return START_STICKY;
    }

    @SuppressLint("ForegroundServiceType")
    private void showNotification() {
        // Intent per aprire l'app
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Intent per il tasto PAUSE
        Intent pauseIntent = new Intent(this, PlayerService.class).setAction(ACTION_PAUSE);
        PendingIntent pausePendingIntent = PendingIntent.getService(this, 1, pauseIntent, PendingIntent.FLAG_IMMUTABLE);

        // Intent per il tasto PLAY
        Intent playIntent = new Intent(this, PlayerService.class).setAction(ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getService(this, 2, playIntent, PendingIntent.FLAG_IMMUTABLE);

        // Costruiamo la notifica
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Player")
                .setContentText(mediaPlayer.isPlaying() ? "In riproduzione..." : "In pausa")
                .setSmallIcon(R.drawable.baseline_music_note_24)
                .setContentIntent(contentIntent)
                // Aggiungiamo i tasti (Action)
                .addAction(android.R.drawable.ic_media_pause, "Pause", pausePendingIntent)
                .addAction(android.R.drawable.ic_media_play, "Play", playPendingIntent)
                // Applica lo stile "Media" (opzionale, richiede androidx.media)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1)).build();

        startForeground(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Player Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(serviceChannel);
        }
    }

    // --- Metodi di controllo ---
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            showNotification(); // Aggiorna la notifica per mostrare lo stato "In pausa"
        }
    }

    public void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            showNotification(); // Aggiorna la notifica
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}