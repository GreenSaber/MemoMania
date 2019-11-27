package com.saber.green.memomania.utils

class RandomUtils {

    fun getUniqueRandomElementsArray(arraySize: Int, maxValue: Int): MutableList<Int> {
        val list = ArrayList<Int>(maxValue)
        for (i in 1..maxValue) {
            list.add(i)
        }
        list.shuffle()
        return list.subList(0, arraySize)
    }
}