package com.bbb.quickfingers

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class BackgroundSound: Service() {

    var mediaPlayer:MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this,R.raw.background)
        mediaPlayer!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        stopSelf()
        super.onDestroy()
    }

}