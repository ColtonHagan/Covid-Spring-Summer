package com.example.chess
class Board(private val size: Int, val cells: List<Cell>) {
    fun getCell(row: Int, col: Int) = cells[row*size + col]
}