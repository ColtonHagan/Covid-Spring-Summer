package com.example.dotai

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.concurrent.fixedRateTimer
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

//Left
////Try to get it running faster
////Add maze elements
class dotOverView (private val size: Int) {
    var dotsLiveData = MutableLiveData<ArrayList<dot>>()
    private var dots = arrayListOf<dot>()
    private var goalCent = Pair(720.0,200.0)
    private var goalRad = 30
    private var minSteps = Integer.MAX_VALUE
    private var gen = 0
    init {
        createDots()
        moveTimer()
    }
    private fun createDots() {
        for(i in 0 until size) {
            dots.add(dot())
        }
        updateLiveData(dots)
    }
    /*private fun moveTimer() {
        fixedRateTimer("timer", daemon = true, 0L, 25) {
            moveDots()
            if(!aliveCheck()) {
                this.cancel()
                nextGen()
            }
        }

    }
    private fun moveTimer(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                moveDots()
                if(!aliveCheck()) {
                    this.cancel()
                    nextGen()
                } else {
                    delay(10)
                }
            }
        }
    }*/
    private fun moveTimer() {
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                moveDots()
                if(!aliveCheck())
                    nextGen()
                else
                    mainHandler.postDelayed(this, 1)
            }
        })
    }
    private fun nextGen() {
        gen++
        naturalSelection()
        updateLiveData(dots)
        moveTimer()
    }
    private fun moveDots() {
        for(i in 0 until dots.size) {
            if(!dots[i].dead) {
                if((dots[i].brain.step+2) > minSteps) {
                    dots[i].dead = true
                } else {
                    dots[i].move()
                    updateLiveData(dots)
                    if ((dots[i].pos[0] < 0 || dots[i].pos[1] < 10 || dots[i].pos[0] > 1440 || dots[i].pos[1] > 2500)
                        || (dots[i].pos[0] >= 620 && dots[i].pos[1] >= 250 && dots[i].pos[0] <= 820 && dots[i].pos[1] <= 300)
                        || (dots[i].pos[0] >= 0 && dots[i].pos[1] >= 1800 && dots[i].pos[0] <= 1200 && dots[i].pos[1] <= 1850)
                        || (dots[i].pos[0] >= 500 && dots[i].pos[1] >= 1000 && dots[i].pos[0] <= 1440 && dots[i].pos[1] <= 1050)
                        || (dots[i].pos[0] >= 525 && dots[i].pos[1] >= 0 && dots[i].pos[0] <= 575 && dots[i].pos[1] <= 500)
                    ) {
                        dots[i].dead = true
                        //dots[i].calcFitness(goalCent)
                    } else if ((goalRad.toDouble() - distanceToGoal(dots[i].pos[0], dots[i].pos[1])) >= 0) {
                        dots[i].dead = true
                        dots[i].reachedGoal = true
                        //dots[i].calcFitness(goalCent)
                    }
                }
            }
        }
    }
    private fun distanceToGoal(x: Double, y: Double) : Double {
        return sqrt(abs(goalCent.first - x).pow(2.0) + abs(goalCent.second - y).pow(2.0))
    }
    private fun updateLiveData(dots:ArrayList<dot>) {
        dotsLiveData.postValue(dots)
    }
    private fun aliveCheck() : Boolean {
        dots.forEach {
            if(!it.dead)
                return true
        }
        return false
    }
    private fun calcFitnessSum() : Double {
        var sum = 0.0
        dots.forEach {
            sum += it.fitness
        }
        return sum
    }
    private fun selectSingleParent() : dot {
        val fitnessSum = calcFitnessSum()
        val randomParent = Random.nextDouble(0.0,fitnessSum)
        var runningSum = 0.0
        for(i in 0 until size) {
            runningSum += dots[i].fitness
            if(runningSum > randomParent)
                return dots[i]
        }
        return dots[0]
    }

    private fun naturalSelection(){
        for(i in 0 until size) {
            dots[i].calcFitness(goalCent)
        }
        //gets stats
        var highestFitness = Double.MIN_VALUE
        var lowestFitness = Double.MAX_VALUE
        var sumFitness = 0.0
        dots.forEach {
            sumFitness += it.fitness
            if(highestFitness < it.fitness)
                highestFitness = it.fitness
            else if(lowestFitness > it.fitness)
                lowestFitness = it.fitness
        }
        val newDots = arrayListOf<dot>()

        for(i in 1 until size) {
            newDots.add(selectSingleParent().singleParent())
            newDots[i-1].mutate()
        }
        //Prints info and keeps best of old gen
        val bestieParent = getBestDot()
        val bestieBrain = bestieParent.brain.clone()
        if(bestieParent.reachedGoal) {
            println("After $gen generation there is:")
            println("The highest fitness was $highestFitness")
            println("The lowest fitness was $lowestFitness")
            println("The average fitness was ${sumFitness/size}")
            println("Best dot reached goal in ${bestieParent.brain.step} steps w/ fitness of ${bestieParent.fitness}")
            minSteps = bestieParent.brain.step
        } else {
            println("After $gen generation there is:")
            println("The highest fitness was $highestFitness")
            println("The lowest fitness was $lowestFitness")
            println("The average fitness was ${sumFitness/size}")
            println("Best dot was ${distanceToGoal(bestieParent.pos[0],bestieParent.pos[1])} from goal w/ fitness of ${bestieParent.fitness}")
        }
        dots.clear()
        dots = newDots.clone() as ArrayList<dot>
        val bestie = dot()
        bestie.brain = bestieBrain
        bestie.isBest = true
        dots.add(bestie)
        //updates
        updateLiveData(dots)
    }

    // TODO: 8/17/2021 always picking 0.0
    private fun getBestDot() : dot {
        var max = 0.0
        var maxI = 0
        for(i in 0 until size) {
            if (max < dots[i].fitness) {
                max = dots[i].fitness
                maxI = i
            }
        }
        return dots[maxI]
    }
}