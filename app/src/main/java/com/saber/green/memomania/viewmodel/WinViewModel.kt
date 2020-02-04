package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game

class WinViewModel(application: Application) : AndroidViewModel(application) {

    private val soundStatus = MutableLiveData<Boolean>()

    init {
        soundStatus.value = Game.getSoundStatus()
    }

    fun resetGame() = Game.resetGame()

    fun getSoundStatus(): LiveData<Boolean> = soundStatus

}