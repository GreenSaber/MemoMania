package com.saber.green.memomania.model

import com.saber.green.memomania.data.GameDataProvider

class Game {

    companion object {
        private var lifesCount: Int = 4
        private var levelNumber: Int = 1
        private var difficulty: GameDifficulty = GameDifficulty.CLASSIC
        private var soundStatus: Boolean = true
        private var vibrationStatus: Boolean = true

        fun getDifficulty(): GameDifficulty {
            return difficulty
        }

        fun setDifficulty(difficulty: GameDifficulty) {
            this.difficulty = difficulty
            lifesCount = when (difficulty) {
                GameDifficulty.EASY -> 3
                GameDifficulty.CLASSIC -> 4
                GameDifficulty.HARD -> 5
            }
        }

        fun getSoundStatus(): Boolean {
            return soundStatus
        }

        fun setSoundStatus(status: Boolean) {
            soundStatus = status
        }

        fun getVibrationStatus(): Boolean {
            return vibrationStatus
        }

        fun setVibrationStatus(status: Boolean) {
            vibrationStatus = status
        }

        fun getLevel(): Int {
            return levelNumber
        }

        fun setLevel(number: Int) {
            levelNumber = number
        }

        fun getLifesCount(): Int {
            return lifesCount
        }

        fun getLevelsCount(): Int {
            return when (difficulty) {
                GameDifficulty.EASY -> {
                    GameDataProvider.LEVELS_COUNT_EASY
                }
                GameDifficulty.CLASSIC -> {
                    GameDataProvider.LEVELS_COUNT_CLASSIC
                }
                GameDifficulty.HARD -> {
                    GameDataProvider.LEVELS_COUNT_HARD
                }
            }
        }

        fun setLifesCount(count: Int) {
            lifesCount = count
        }

        fun getActiveTiles(): ArrayList<Tile>? {
            return GameDataProvider.getActiveTilesForLevel(difficulty)?.get(levelNumber-1)
        }

        fun getShowTiming(): Long? {
            val tilesSize = GameDataProvider.getActiveTilesForLevel(difficulty)?.get(levelNumber-1)?.size!!
            return (tilesSize * 700).toLong()
        }

        fun refreshData() {
            GameDataProvider.refreshData()
        }

        fun resetGame() {
            GameDataProvider.refreshData()
            lifesCount = when (difficulty) {
                GameDifficulty.EASY -> 3
                GameDifficulty.CLASSIC -> 4
                GameDifficulty.HARD -> 5
            }
            levelNumber = 1
        }
    }

}