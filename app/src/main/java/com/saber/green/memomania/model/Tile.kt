package com.saber.green.memomania.model

class Tile(private val number: Int, private val value: Int) {

    fun getNumber(): Int {
        return number
    }

    fun getValue(): Int {
        return value
    }
}