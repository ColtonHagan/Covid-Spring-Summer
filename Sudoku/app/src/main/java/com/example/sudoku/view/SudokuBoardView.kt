package com.example.sudoku.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.sudoku.game.Cell
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.min
import kotlin.math.sqrt


class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var size = 9
    private var sqrtSize = sqrt(size.toDouble()).toInt()
    private var cellSizePixels = 0F
    private var selectedRow = -1
    private var selectedCol = -1

    private var listener: OnTouchListener? = null
    private var cells: List<Cell>? = null

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 8F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 5F
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#a552ff")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#dfc2ff")
    }

    private val impossibleCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.RED
    }

    private val startingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#acacac")
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 64F
    }

    private val startingTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 64F
        typeface = Typeface.DEFAULT_BOLD
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas) {
        cellSizePixels = (width / size).toFloat()
        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    private fun fillCells(canvas: Canvas) {
        cells?.forEach {
            val r = it.row
            val c = it.col
            if(it.invalid) {
                fillCell(canvas, r, c, impossibleCellPaint)
            } else if(it.isStartingCell) {
                fillCell(canvas, r, c, startingCellPaint)
            } else if (r == selectedRow && c == selectedCol) {
                fillCell(canvas, r, c, selectedCellPaint)
            } else if ((r == selectedRow || c == selectedCol ||
                (r/sqrtSize == selectedRow/sqrtSize && c/sqrtSize == selectedCol/sqrtSize)) && !(selectedCol < 0 && selectedRow < 0)) {
                fillCell(canvas, r, c, conflictingCellPaint)
            }
        }
    }

    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint) {
        canvas.drawRect(c * cellSizePixels, r * cellSizePixels, (c + 1) * cellSizePixels, (r + 1) * cellSizePixels, paint)
    }

    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)

        for (i in 1 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }
            canvas.drawLine(
                i * cellSizePixels,
                0F,
                i * cellSizePixels,
                height.toFloat(),
                paintToUse
            )

            canvas.drawLine(
                0F,
                i * cellSizePixels,
                width.toFloat(),
                i * cellSizePixels,
                paintToUse
            )
        }
    }

    private fun drawText(canvas: Canvas) {
        cells?.forEach {
            if (it.value > 0) {
                val r = it.row
                val c = it.col
                val value = it.value.toString()
                val currTextPaint = if (it.isStartingCell) startingTextPaint else textPaint
                val textBounds = Rect()
                currTextPaint.getTextBounds(value, 0, value.length, textBounds)
                val textWidth = currTextPaint.measureText(value)
                val textHeight = textBounds.height()
                canvas.drawText(
                    value,
                    (c * cellSizePixels) + cellSizePixels / 2 - textWidth / 2,
                    (r * cellSizePixels) + cellSizePixels / 2 + textHeight / 2,
                    currTextPaint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        val possibleSelectedRow = (y / cellSizePixels).toInt()
        val possibleSelectedCol = (x / cellSizePixels).toInt()
        listener?.onCellTouched(possibleSelectedRow, possibleSelectedCol)
    }

    fun updateCellUI(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        invalidate()
    }

    fun updateCells(cells: CopyOnWriteArrayList<Cell>) {
        this.cells = cells
        invalidate()
    }

    fun registerListener(listener: OnTouchListener) {
        this.listener = listener
    }

    fun victoryMessage() {
        AlertDialog.Builder(context).apply {
            setTitle("VICTORY")
            setMessage("You solved the puzzle!")
            setPositiveButton("Play Again") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    interface OnTouchListener {
        fun onCellTouched(row: Int, col: Int)
    }
}