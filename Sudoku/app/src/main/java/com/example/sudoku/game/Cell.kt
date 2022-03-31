package com.example.sudoku.game

class Cell (val row: Int, val col: Int, var isStartingCell: Boolean = false) {
    var value: Int = 0
    var invalid: Boolean = false
    fun softReset() {
        value = 0
        invalid = false
    }
    fun hardReset() {
        isStartingCell = false
        softReset()
    }
    fun setStarting(starting: Boolean) {
        isStartingCell = starting
    }
}