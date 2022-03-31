package com.example.sudoku.game
import androidx.lifecycle.MutableLiveData
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList


// TODO: 7/8/2021 Animate solve
class SudokuController {
    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<CopyOnWriteArrayList<Cell>>()

    private var selectedRow = -1
    private var selectedCol = -1
    private val board: Board
    private val cells = CopyOnWriteArrayList<Cell>()

    init {
        for(i in 0 until 81) {
            val newCell = Cell(i/9,i%9,(0..100).random() < 75)
            cells.add(newCell)
        }
        generateStartingCells()
        board = Board(9,cells)
        selectedCellLiveData.postValue(Pair(selectedRow,selectedCol))
        cellsLiveData.postValue(board.cells)
    }
    private fun generateStartingCells() {
        println("GENERATING STARTING CELLS")
        cells.forEach {
            if(it.isStartingCell) {
                while(true) {
                    it.value = (1..9).random()
                    if (validNumber(it, it.value)) {
                        break
                    }
                    println("Looping")
                }
                println("Is Starting Cell")
            }
            println("${it.col},${it.row}")
        }
        println("ATTEMPTING TO SOLVE")
        if(!solve(0,false)) {
            println("Starting gen")
            generateStartingCells()
            println("Next gen")
        }
        cells.forEach {
            if(!it.isStartingCell)
                it.softReset()
        }
    }
    
    fun victoryCheck() : Boolean {
        cells.forEach { if(it.invalid || it.value < 1) return false }
        return true
    }
    fun solveHelper(index: Int,animate: Boolean) : Boolean {
        if(index>80)
            return true
        var solve = false
        if(animate) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    selectedCellLiveData.postValue(Pair(cells[index].row,cells[index].col))
                    solve = solve(index,animate)
                }
            }, 10)
        } else {
            solve = solve(index, animate)
        }
        return solve
    }

    fun solve(index: Int,animate: Boolean) : Boolean {
        if(index>80) return true
        if(cells[index].value > 0)
            return solveHelper(index+1,animate)
        for(i in 1..9) {
            if(validNumber(cells[index], i)) {
                if(animate) {
                    cells[index].value = i
                    cellsLiveData.postValue(board.cells)
                } else {
                    cells[index].value = i
                }
                //moves to next number
                if(solveHelper(index+1,animate)) {
                    return true
                } else {
                    //removes number
                    if(animate) {
                        cells[index].value = 0
                    } else {
                        cells[index].value = 0
                    }
                }
            }
        }
        return false
    }
    fun reset() {
        cells.forEach {
            if(!it.isStartingCell)
                it.softReset()
        }
        cleanData()
    }

    fun restart() {
        cells.forEach {
            it.hardReset()
            it.setStarting((0..2).random() == 0)
        }
        generateStartingCells()
        cleanData()
    }

    fun cleanData() {
        selectedRow = -1
        selectedCol = -1
        selectedCellLiveData.postValue(Pair(selectedRow,selectedCol))
        cellsLiveData.postValue(board.cells)
    }
    private fun validNumber(curCell: Cell, cellValue: Int) : Boolean {
        cells.forEach {
            val r = it.row
            val c = it.col
            if ((r == curCell.row || c == curCell.col || (r / 3 == curCell.row / 3 && c / 3 == curCell.col / 3)) && it != curCell) {
                if (cellValue == it.value)
                    return false
            }
        }
        return true
    }
    fun handleInput(num: Int) {
        if (selectedCol < 0 && selectedRow < 0) return
        val cell = board.getCell(selectedRow, selectedCol)
        cell.invalid = !validNumber(cell, num)
        board.getCell(selectedRow, selectedCol).value = num
        cellsLiveData.postValue(board.cells)
    }

    fun deleteInput() {
        if (selectedCol < 0 && selectedRow < 0) return
        val cell = board.getCell(selectedRow, selectedCol)
        cell.softReset()
        cellsLiveData.postValue(board.cells)
    }

    fun update(row: Int, col: Int) {
        if(board.getCell(row,col).isStartingCell) return
        selectedRow = row
        selectedCol = col
        selectedCellLiveData.postValue(Pair(row,col))
    }
}