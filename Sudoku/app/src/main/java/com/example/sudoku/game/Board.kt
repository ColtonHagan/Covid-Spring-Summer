package com.example.sudoku.game

import java.util.concurrent.CopyOnWriteArrayList

class Board(private val size: Int, val cells: CopyOnWriteArrayList<Cell>) {
    fun getCell(row: Int, col: Int): Cell = cells[row*size + col]
}