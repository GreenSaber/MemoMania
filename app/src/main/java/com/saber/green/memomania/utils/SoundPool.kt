package com.saber.green.memomania.utils

import android.media.AudioAttributes
import android.media.SoundPool

object SoundPool {

    private var instance: SoundPool? = null

    fun getInstance(): SoundPool? {
        if (instance == null) {
            val audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
            instance = SoundPool.Builder()
                .setMaxStreams(50).setAudioAttributes(audioAttributes).build()
        }
        return instance
    }

}