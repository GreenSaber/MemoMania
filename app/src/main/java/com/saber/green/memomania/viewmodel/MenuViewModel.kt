package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.GameDifficulty

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val difficulty = MutableLiveData<GameDifficulty>()
    private val soundStatus = MutableLiveData<Boolean>()

    init {
        difficulty.value = Game.getDifficulty()
        soundStatus.value = Game.getSoundStatus()
    }

    fun getDifficulty(): LiveData<GameDifficulty> {
        return difficulty
    }

    fun getSoundStatus(): LiveData<Boolean> {
        return soundStatus
    }

    private fun setDifficulty(gameDifficulty: GameDifficulty) {
        difficulty.value = gameDifficulty
    }

    fun onSoundButtonClick() {
        val statusToSet: Boolean = !Game.getSoundStatus()
        Game.setSoundStatus(statusToSet)
        soundStatus.value = statusToSet
    }

    fun onRightArrowButtonClick(): GameDifficulty {
        when (difficulty.value!!) {
            GameDifficulty.EASY -> {
                Game.setDifficulty(GameDifficulty.CLASSIC)
                Game.resetGame()
                setDifficulty(GameDifficulty.CLASSIC)
                return GameDifficulty.CLASSIC
            }
            GameDifficulty.CLASSIC -> {
                Game.setDifficulty(GameDifficulty.HARD)
                Game.resetGame()
                setDifficulty(GameDifficulty.HARD)
                return GameDifficulty.HARD
            }
            GameDifficulty.HARD -> {
                Game.setDifficulty(GameDifficulty.EASY)
                Game.resetGame()
                setDifficulty(GameDifficulty.EASY)
                return GameDifficulty.EASY
            }
        }
    }

    fun onLeftArrowButtonClick(): GameDifficulty {
        when (difficulty.value!!) {
            GameDifficulty.EASY -> {
                Game.setDifficulty(GameDifficulty.HARD)
                setDifficulty(GameDifficulty.HARD)
                return GameDifficulty.HARD
            }
            GameDifficulty.CLASSIC -> {
                Game.setDifficulty(GameDifficulty.EASY)
                setDifficulty(GameDifficulty.EASY)
                return GameDifficulty.EASY
            }
            GameDifficulty.HARD -> {
                Game.setDifficulty(GameDifficulty.CLASSIC)
                setDifficulty(GameDifficulty.CLASSIC)
                return GameDifficulty.CLASSIC
            }
        }
    }

    fun refreshData() {
        Game.refreshData()
    }
}