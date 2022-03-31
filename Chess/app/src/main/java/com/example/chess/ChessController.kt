package com.example.chess
import androidx.lifecycle.MutableLiveData

class ChessController {
    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    private var selectedRow = -1
    private var selectedCol = -1
    private val board: Board

    init {
        val cells = List(9 * 9) {i -> Cell(i / 9, i % 9)}
        board = Board(9, cells)
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
    }
    fun handleInput() {
        if (selectedRow == -1 || selectedCol == -1) return
        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        selectedCellLiveData.postValue(Pair(row, col))
    }
}