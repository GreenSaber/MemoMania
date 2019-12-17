package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.Life

class NextLevelViewModel(application: Application) : AndroidViewModel(application) {

    private val lifeCount = MutableLiveData<String>()
    private val levelCount = MutableLiveData<String>()

    init {
        lifeCount.value = Life.getLifesCount().toString()
        levelCount.value = Game.getLevel().toString()
    }

    fun getLifeCount(): LiveData<String> = lifeCount

    fun getLevelCount(): LiveData<String> = levelCount

    fun onGetLifeButtonClick() {
        val lifes = Life.getLifesCount() + 1
        Life.setLifesCount(lifes)
        lifeCount.value = Life.getLifesCount().toString()
    }
}