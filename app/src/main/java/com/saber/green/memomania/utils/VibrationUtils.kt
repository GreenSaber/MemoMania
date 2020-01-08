package com.saber.green.memomania.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity

class VibrationUtils(private val appCompatActivity: AppCompatActivity) {

    private val PATTERN_CORRECT_VALUE = longArrayOf(0, 60)
    private val PATTERN_INCORRECT_VALUE = longArrayOf(0, 60, 100, 60)

    private fun vibrate(pattern : LongArray) {
        val vibrator = appCompatActivity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(pattern, -1)
        }
    }

    fun correctValueVibration(){
        vibrate(PATTERN_CORRECT_VALUE)
    }

    fun inCorrectValueVibration(){
        vibrate(PATTERN_INCORRECT_VALUE)
    }
}
