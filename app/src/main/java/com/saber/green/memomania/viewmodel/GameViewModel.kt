package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val lifeCount = MutableLiveData<String>()
    private val levelCount = MutableLiveData<String>()

    init {
        lifeCount.value = Game.getLifesCount().toString()
        levelCount.value = Game.getLevel().toString()
    }

    fun getLifeCount(): LiveData<String> = lifeCount

    fun getLevelCount(): LiveData<String> = levelCount

    fun resetGame(){
        Game.resetGame()
    }

}