package com.example.tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TetrisBoard(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var heightSize = 20
    private var widthSize = 10
    private var cellPixelH = 0F
    private var cellPixelW = 0F
    private lateinit var board: Array<Array<Cell>>

    private val outerLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 20F
    }
    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val base = kotlin.math.min(widthMeasureSpec,heightMeasureSpec)
        val width = base/2
        val height = base
        setMeasuredDimension(width, height)
    }
    override fun onDraw(canvas: Canvas) {
        cellPixelH = (height / heightSize).toFloat()
        cellPixelW = (width / widthSize).toFloat()
        fillCells(canvas)
        drawLines(canvas)
        invalidate()
    }
    private fun fillCells(canvas: Canvas) {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if(board[i][j].isEmpty()) {
                    val paint = Paint().apply {
                        style = Paint.Style.FILL_AND_STROKE
                        color = board[i][j].getColor()
                    }
                    canvas.drawRect(
                        i * cellPixelW,
                        j * cellPixelH,
                        (i + 1) * cellPixelW,
                        (j + 1) * cellPixelW,
                        paint
                    )
                }
            }
        }
    }
    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), outerLinePaint)
        for(i in 1 until heightSize) {
            canvas.drawLine(
                0F,
                i * cellPixelH,
                width.toFloat(),
                i * cellPixelH,
                linePaint
            )
        }
        for(i in 1 until widthSize) {
            canvas.drawLine(
                i * cellPixelW,
                0F,
                i * cellPixelW,
                height.toFloat(),
                linePaint
            )
        }
    }

    fun updateBoard(board: Array<Array<Cell>>) {
        this.board = board
    }
}