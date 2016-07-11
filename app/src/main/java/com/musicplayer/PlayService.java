package com.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;


public class PlayService extends Service {
    private MediaPlayer mMediaPlayer;
    private IBinder mBinder = new LocalBinder();
    private int totalDuration;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) mMediaPlayer.release();
    }

    public void playMusic(String songUri) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }

        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(songUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mMediaPlayer.prepare();
            totalDuration = mMediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void stopMusic() {
        mMediaPlayer.stop();
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public int getCurentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void getSongPosition(int curentPositon) {
        mMediaPlayer.seekTo(curentPositon);
    }
public void onComplet(){
    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

        }
    });
}
    public class LocalBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

}
