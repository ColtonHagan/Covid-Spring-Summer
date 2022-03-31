package com.example.dotai

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View

class backgroundSpace(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var dots: ArrayList<dot> = arrayListOf()
    private var width: Double = 0.0
    private var height: Double = 0.0
    private var goalCent = Pair(720.0,200.0)
    private var goalRad = 30

    private val circleColor = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        strokeWidth = 8F
    }
    private val bestcircleColor = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.RED
        strokeWidth = 8F
    }

    private val goalColor = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLUE
    }

    private val obsColor  = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.GRAY
    }
    override fun onDraw(canvas: Canvas) {
        //get height
        val metrics: DisplayMetrics = context.resources.displayMetrics
        width = metrics.widthPixels.toDouble()
        height = metrics.heightPixels.toDouble()
        //drawBorders(canvas)
        drawGoal(canvas)
        drawObs(canvas)
        drawDotes(canvas)
        //println("Dots alive = ${dots.size}")
        //dots.forEach { println("Dot at ${it.pos[0]},${it.pos[1]}") }
        invalidate()
    }
    private fun drawObs(canvas: Canvas) {
        //just before goal
        canvas.drawRect(620.toFloat(),250.toFloat(),820.toFloat(),300.toFloat(),obsColor)
        //to left of goal and down
        canvas.drawRect(525.toFloat(),0.toFloat(),575.toFloat(),500.toFloat(),obsColor)
        //low gap on right
        canvas.drawRect(0.toFloat(),1800.toFloat(),1200.toFloat(),1850.toFloat(),obsColor)
        //middle gap on left
        canvas.drawRect(500.toFloat(),1000.toFloat(),1500.toFloat(),1050.toFloat(),obsColor)
    }
    private fun drawGoal(canvas: Canvas) {
        canvas.drawCircle(goalCent.first.toFloat(),goalCent.second.toFloat(),goalRad.toFloat(),goalColor)
    }

    private fun drawDotes(canvas: Canvas) {
        dots.forEach {
            if(!it.isBest)
                drawDote(canvas, it.pos[0],it.pos[1])
            else
                drawBestDote(canvas, it.pos[0],it.pos[1])
        }
    }
    private fun drawDote(canvas: Canvas, x: Double, y: Double) {
        canvas.drawCircle(x.toFloat(),y.toFloat(),10.toFloat(),circleColor)
    }
    private fun drawBestDote(canvas: Canvas, x: Double, y: Double) {
        canvas.drawCircle(x.toFloat(),y.toFloat(),10.toFloat(),bestcircleColor)
    }
    fun update(dots: ArrayList<dot>) {
        this.dots = dots
        invalidate()
    }

}