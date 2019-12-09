package com.saber.green.memomania.model

class Life {

    companion object {

        private var lifesCount: Int = 5

        fun getLifesCount(): Int {
            return lifesCount
        }

        fun setLifesCount(count: Int) {
            lifesCount = count
        }
    }
}