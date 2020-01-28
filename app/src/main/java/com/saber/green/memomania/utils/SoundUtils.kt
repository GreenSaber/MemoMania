package com.saber.green.memomania.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.saber.green.memomania.R

class SoundUtils {

    fun getSoundPool(): SoundPool {
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        return SoundPool.Builder()
            .setMaxStreams(50).setAudioAttributes(audioAttributes).build()
    }

    fun getSound(context: Context, soundPool: SoundPool): Int {
        return soundPool.load(context, R.raw.laser, 1)
    }

    fun playCorrectTileSound(soundPool: SoundPool, sound : Int) {
        soundPool.play(sound, 1F, 1F, 0, 0, 1F)
    }
}
