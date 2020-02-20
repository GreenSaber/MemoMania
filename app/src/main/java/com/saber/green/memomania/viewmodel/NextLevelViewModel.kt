package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game

class NextLevelViewModel(application: Application) : AndroidViewModel(application) {

    private val lifeCount = MutableLiveData<String>()
    private val levelCount = MutableLiveData<String>()
    private val soundStatus = MutableLiveData<Boolean>()

    init {
        lifeCount.value = Game.getLifesCount().toString()
        levelCount.value = Game.getLevel().toString()
        soundStatus.value = Game.getSoundStatus()
    }

    fun getSoundStatus(): LiveData<Boolean> = soundStatus

    fun getLifeCount(): LiveData<String> = lifeCount

    fun getLevelCount(): LiveData<String> = levelCount

    fun addLife() {
        val totalLifes = Game.getLifesCount() + Game.getLivesCountToIncrease()
        Game.setLifesCount(totalLifes)
        lifeCount.value = Game.getLifesCount().toString()
    }

    fun getLivesCountToIncrease(): String {
        return Game.getLivesCountToIncrease().toString()
    }
}