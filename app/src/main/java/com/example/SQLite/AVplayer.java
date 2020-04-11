package com.example.SQLite;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.IOException;

public class AVplayer extends Activity implements View.OnClickListener {
    private MyConnection conn;
    MusicPlayerService.MyBinder musicController;
    Button ib_play;
    Button ib_pause;
    MediaPlayer player;
    String path;

    private ImageView imageView;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(cameraBitmap);
                
            }
        }
    }
    public void onClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avplayer);
        ib_play = findViewById(R.id.av_btn);
        ib_pause = findViewById(R.id.av_pau);
        // 混合方式开启音乐播放
        Intent service = new Intent(this, MusicPlayerService.class);
        startService(service);
        conn = new MyConnection();
        bindService(service, conn, BIND_AUTO_CREATE);

        // 拍照
        Button btnTakePicture = findViewById(R.id.btn_take_picture);
        btnTakePicture.setOnClickListener(this);

        imageView =  findViewById(R.id.image_view);

    }
    public void pause(View v){
        player.pause();
    }
    public void play(View v) {
//        MediaPlayer player = MediaPlayer.create(this, R.raw.test);
//        player.start();
        if (player != null ) {
            player.start();
        } else {
            player = new MediaPlayer();
            try {
                // 设置要播放的路径
                player = MediaPlayer.create(this, R.raw.test);
//            player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        musicController.playPause();
//        if(musicController.isPlaying()){
//            ib_play.setText("暂停");
//        }else {
//            ib_play.setText("播放");
//        }
    }

    class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicController = (MusicPlayerService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
