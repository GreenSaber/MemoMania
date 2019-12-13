package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saber.green.memomania.model.Game

class NextLevelViewModel(application: Application) : AndroidViewModel(application) {

    fun getGameLevel(): Int {
        return Game.getLevel()
    }
}