package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game

class GameOverViewModel(application: Application) : AndroidViewModel(application) {

    private val levelCount = MutableLiveData<String>()
    private val soundStatus = MutableLiveData<Boolean>()

    init {
        levelCount.value = Game.getLevel().toString()
        soundStatus.value = Game.getSoundStatus()
    }

    fun getSoundStatus(): LiveData<Boolean> = soundStatus

    fun getLevelCount(): LiveData<String> = levelCount

    fun resetGame() {
        Game.resetGame()
    }
}