package com.example.dotai

import kotlin.math.*
import kotlin.random.Random

class dot {
    var pos: ArrayList<Double> = arrayListOf(720.0,2400.0)
    private var vel: ArrayList<Double> = arrayListOf(0.0,0.0)
    private var acc: ArrayList<Double> = arrayListOf(0.0,0.0)
    var reachedGoal = false
    var isBest = false
    var dead = false
    var brain = dotBrain(2000)
    var fitness: Double = 0.0

    fun move() {
        if(brain.step < brain.directions.size) {
            acc.clear()
            acc.add(brain.directions[brain.step].x)
            acc.add(brain.directions[brain.step].y)
            brain.step++
        }
        vel[0] += acc[0]
        vel[1] += acc[1]
        pos[0] += vel[0]
        pos[1] += vel[1]
    }
    fun calcFitness(goalCent: Pair<Double,Double>) {
        val dist = sqrt(abs(goalCent.first - pos[0]).pow(2.0) + abs(goalCent.second - pos[1])
            .pow(2.0)
        )
        //dist seems fine
        if(reachedGoal)
            this.fitness = (1.0/16.0 + 10000.0/ brain.step.toDouble().pow(2.0))
        else
            this.fitness = 1.0/(dist.pow(2.0)) //(always equal to negative infinity?)
    }
    fun singleParent(): dot {
        val kid = dot()
        kid.brain = brain.clone()
        return kid
    }
    fun mutate() {
        if(isBest) {
            println("ERROR")
        }
        val mutatedBrain = dotBrain(2000)
        val rate = 1
        for(i in 0 until mutatedBrain.directions.size) {
            val rand = Random.nextInt(0,1000)
            if(rand <= rate) {
                val randomAngle: Double = Math.toDegrees(Random.nextDouble(0.0,2* Math.PI))
                mutatedBrain.directions[i].x = cos(randomAngle)
                mutatedBrain.directions[i].y = sin(randomAngle)
            } else {
                mutatedBrain.directions[i].x = brain.directions[i].x
                mutatedBrain.directions[i].y = brain.directions[i].y
            }
        }
        brain = mutatedBrain
    }
}