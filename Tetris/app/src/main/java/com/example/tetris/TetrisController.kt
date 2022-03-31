package com.example.tetris

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.example.tetris.Blocks.*


// TODO: 7/20/2021 get end of game reset
class TetrisController {
    var boardLiveData =  MutableLiveData(Array(10) { Array(20) { Cell(0, false) } })
    private var board = Array(10) { Array(20) { Cell(0, false)}}
    private lateinit var currentBlock: Block
    private var score = 0
    private var highScore = 0
    private var period = 1000L
    init {
        reset()
    }
    private fun updateBoard() {
        boardLiveData.postValue(board)
        getCords()
    }
    private fun downTimer() {
        val handler = Handler(Looper.getMainLooper())
        val runnableCode = object: Runnable {
            override fun run() {
                moveDown()
                if(period > 50)
                    period -= 1L
                handler.postDelayed(this, period)
            }
        }
        handler.postDelayed(runnableCode,period)
    }
    private fun reset() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j].remove()
            }
        }
        score = 0
        generateBlock()
        drawBlock(currentBlock)
        updateBoard()
        downTimer()
    }
    private fun generateBlock() {
        val shapes = listOf(IShape(),JShape(),LShape(),OShape(),SShape(),TShape(),ZShape())
        currentBlock = shapes.random()
        if(!drawBlock(currentBlock)) {
            println("GAME OVER")
            reset()
        }
        updateBoard()
    }
    private fun drawBlock(block: Block) : Boolean {
        val loc = block.cords
        loc.forEach {
            if(it[1] > 19 || it[1] < 0 || it[0] > 9 || it[0] < 0) return false
        }
        for(i in board.indices) {
            for(j in board[i].indices) {
                if(board[i][j].isEmpty() && loc.contains(mutableListOf(i,j)))
                    return false
            }
        }
        for(i in board.indices) {
            for(j in board[i].indices) {
                if(loc.contains(mutableListOf(i,j)))
                    board[i][j] = Cell(block.color,true)
            }
        }
        return true
    }
    private fun removeBlock(block: Block) {
        val loc = block.cords
        for (i in board.indices) {
            for (j in board[i].indices) {
                if(loc.contains(mutableListOf(i,j))) {
                    board[i][j].remove()
                }
            }
        }
    }
    private fun getCords() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if(board[i][j].isEmpty()) {
                    println("($i,$j)")
                }
            }
        }
    }
    fun moveDown() {
        removeBlock(currentBlock)
        currentBlock.moveDown()
        if(!drawBlock(currentBlock)) {
            currentBlock.moveUp()
            drawBlock(currentBlock)
            checkBottom()
            generateBlock()
        }
        updateBoard()
    }
    fun moveLeft() {
        removeBlock(currentBlock)
        currentBlock.moveLeft()
        if(!drawBlock(currentBlock)) {
            currentBlock.moveRight()
            drawBlock(currentBlock)
        }
        updateBoard()
    }

    fun moveRight() {
        removeBlock(currentBlock)
        currentBlock.moveRight()
        if(!drawBlock(currentBlock)) {
            currentBlock.moveLeft()
            drawBlock(currentBlock)
        }
        updateBoard()
    }
    fun rotate() {
        removeBlock(currentBlock)
        currentBlock.rotate()
        if(!drawBlock(currentBlock)) {
            currentBlock.rotateBack()
            drawBlock(currentBlock)
        }
        updateBoard()
    }
    private fun checkBottom() {
        var full = 0
        for(i in board.indices) {
            if(board[i][19].isEmpty()) {
                full++
            }
        }
        if(full == 10) {
            score++
            if(score > highScore) highScore = score
            clearBottom()
            checkBottom()
            updateBoard()
        }
    }
    private fun clearBottom() {
        for(i in board.indices) {
            for (j in board[i].size-1 downTo 0) {
                if(j == 0) {
                    board[i][j].remove()
                } else {
                    board[i][j] = board[i][j - 1]
                }
            }
        }
        updateBoard()
    }
    fun getScore() : String {
        return score.toString()
    }
    fun getHighScore() : String {
        return highScore.toString()
    }
}