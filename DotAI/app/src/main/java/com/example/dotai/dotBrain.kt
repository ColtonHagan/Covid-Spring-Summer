package com.example.dotai

import java.lang.Math.*
import kotlin.random.Random

class dotBrain (size: Int) {
    private val size: Int
    var directions: ArrayList<cords> = arrayListOf()
    var step = 0
    init {
        this.size = size
        for(i in 0 until size)
            directions.add(cords(0.0,0.0))
        randomDir()
    }

    fun randomDir() {
        directions.forEach {
            val randomAngle: Double = Math.toDegrees(Random.nextDouble(0.0,2*PI))
            it.x = cos(randomAngle)
            it.y = sin(randomAngle)
        }
    }

    fun clone(): dotBrain {
        val clone = dotBrain(size)
        for(i in 0 until size) {
            clone.directions[i] = directions[i]
        }
        return clone
    }

    fun mutate() {
        /*val rate = .01
        for(i in 0 until size) {
            val rand = Random.nextDouble(0.00,1.00)
            if(rand <= rate) {
                val randomAngle: Double = Math.toDegrees(Random.nextDouble(0.0,2*PI))
                directions[i].x = cos(randomAngle)
                directions[i].y = sin(randomAngle)
            }
        }*/
    }
}