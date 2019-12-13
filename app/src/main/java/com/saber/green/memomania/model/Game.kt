package com.saber.green.memomania.model

import com.saber.green.memomania.data.GameDataProvider

class Game {

    companion object {
        private var levelNumber: Int = 1


        fun getLevel(): Int {
            return levelNumber
        }

        fun setLevel(number: Int) {
            levelNumber = number
        }

        fun getActiveTiles(): ArrayList<Tile>? {
            return GameDataProvider.getInstance()?.getActiveTilesForLevel()?.get(levelNumber-1)
        }
    }

}