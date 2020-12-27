package com.example.game2.Music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.game2.R

@Suppress("DEPRECATION")
class SoundPlayer : Service() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        // TODO Auto-generated method stub
        return null
    }

    override fun onStart(intent: Intent, startId: Int) {
        super.onStart(intent, startId)
        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer.create(this, R.raw.game)
            mediaPlayer?.setLooping(true)
            mediaPlayer?.start()
            mediaPlayer?.prepare()
        }
    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy()
        mediaPlayer!!.stop()
    }
}