package com.example.tetris

import android.graphics.Color

class Cell(private var color: Int, private var empty: Boolean) {
    fun isEmpty() : Boolean {
        return empty
    }
    fun getColor() : Int {
        return color
    }
    fun remove() {
        color = 0
        empty = false
    }
}