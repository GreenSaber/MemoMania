package com.saber.green.memomania.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity

object VibrationUtils {

    val PATTERN_CORRECT_VALUE = longArrayOf(0, 60)
    val PATTERN_INCORRECT_VALUE = longArrayOf(0, 60, 100, 60)

    fun vibrate(appCompatActivity: AppCompatActivity, pattern : LongArray) {
        val vibrator = appCompatActivity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(pattern, -1)
        }
    }
}
