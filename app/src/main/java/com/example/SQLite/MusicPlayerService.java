package com.example.SQLite;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicPlayerService extends Service {
    private  String path = "mnt";
    MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void playPause() {
            if (player.isPlaying()){
                // 正在播放
                player.pause();
            }else {
                player.start();
            }
        }

        public boolean isPlaying() {
            return player.isPlaying();
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
