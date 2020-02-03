package com.saber.green.memomania.ui

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    var player : MediaPlayer? = null

    fun startPlayer(context : Context, soundId : Int){
        if (player == null) {
            player = MediaPlayer.create(context, soundId)
            player?.setOnCompletionListener { stopPlayer() }
        }
        player?.start()
    }

    fun stopPlayer() {
        if (player != null) {
            player!!.release()
            player = null
        }
    }
}