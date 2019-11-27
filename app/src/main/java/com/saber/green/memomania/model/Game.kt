package com.saber.green.memomania.model

import com.saber.green.memomania.utils.RandomUtils

class Game(private val levelNumber: Int) {

    private val tilesList = ArrayList<Tile>()
    private val randomUtils = RandomUtils()

    fun getLevel(): Int {
        return levelNumber
    }

    private fun getUniqueTilesArray(arraySize: Int, valuesMaxCount: Int, numbersMaxCount: Int = 12): ArrayList<Tile> {
        val numbers = randomUtils.getUniqueRandomElementsArray(arraySize, numbersMaxCount)
        val values = randomUtils.getUniqueRandomElementsArray(arraySize, valuesMaxCount)
        for (i in 0 until arraySize) {
            tilesList.add(Tile(numbers[i], values[i]))
        }
        return tilesList
    }

    fun getActiveTiles(): ArrayList<Tile>? {
        val result = when (levelNumber) {
            1 -> getUniqueTilesArray(3, 9)
            2 -> getUniqueTilesArray(4, 9)
            3 -> getUniqueTilesArray(5, 9)
            4 -> getUniqueTilesArray(6, 9)
            5 -> getUniqueTilesArray(7, 10)
            6 -> getUniqueTilesArray(8, 10)
            7 -> getUniqueTilesArray(9, 10)
            8 -> getUniqueTilesArray(10, 12)
            9 -> getUniqueTilesArray(11, 12)
            10 -> getUniqueTilesArray(12, 12)
            else -> null
        }
        return result
    }
}