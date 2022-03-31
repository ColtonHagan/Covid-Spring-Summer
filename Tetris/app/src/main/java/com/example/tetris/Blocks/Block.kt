package com.example.tetris.Blocks

import android.graphics.Color

open class Block {
    open var cords: List<MutableList<Int>> = listOf()
    open var rotNum = 0
    open var rotations: List<List<List<Int>>> = listOf()
    private val colorsList = listOf("#0341AE","#72CB3B","#FFD500","#FF971C","#FF3213")
    var color = 0
    init {
        color = Color.parseColor(colorsList.random())
    }
    fun moveUp() {
        cords.forEach {
            it[1] -= 1
        }
    }
    fun moveDown() {
        cords.forEach {
            it[1] += 1
        }
    }
    fun moveLeft() {
        cords.forEach {
            it[0] -= 1
        }
    }

    fun moveRight() {
        cords.forEach {
            it[0] += 1
        }
    }
    fun rotate() {
        for(i in cords.indices) {
            cords[i][0] += rotations[rotNum][i][1]
            cords[i][1] += rotations[rotNum][i][0]
        }
        rotNum++
        if(rotNum > 3) rotNum = 0
    }
    fun rotateBack() {
        rotNum--
        if(rotNum < 0) rotNum = 3
        for(i in cords.indices) {
            cords[i][0] -= rotations[rotNum][i][1]
            cords[i][1] -= rotations[rotNum][i][0]
        }
    }
}