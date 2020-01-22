package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saber.green.memomania.model.Game

class WinViewModel(application: Application) : AndroidViewModel(application) {

    fun resetGame() {
        Game.resetGame()
    }
}