package com.example.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private PlayerService player;

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

    public void play(View v) {
        Intent intent=new Intent(this, PlayerService.class);
        if(player==null) {
            startService(intent);
            bindService(intent,connection, Context.BIND_AUTO_CREATE);
        }
        else player.resume();
    }

    public void pause(View v) {
        player.pause();
    }

    public void stop(View v) {
        if(player!=null) {
            unbindService(connection);
            player=null;
        }
        Intent intent=new Intent(this, PlayerService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent(this, PlayerService.class);
        stopService(intent);
    }

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.LocalBinder binder=(PlayerService.LocalBinder) service;
            player=binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            player=null;
        }
    };
}