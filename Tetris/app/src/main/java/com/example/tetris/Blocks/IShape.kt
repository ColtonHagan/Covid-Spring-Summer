package com.example.tetris.Blocks

class IShape : Block() {
    override var cords = listOf(mutableListOf(3,0),mutableListOf(4,0),mutableListOf(5,0),mutableListOf(6,0))
    override var rotations = listOf(
        listOf(listOf(0,2),listOf(1,1),listOf(2,0),listOf(3,-1))
        ,listOf(listOf(1,-2),listOf(0,-1),listOf(-1,0),listOf(-2,1))
        ,listOf(listOf(-1,1),listOf(0,0),listOf(1,-1),listOf(2,-2))
        ,listOf(listOf(0,-1),listOf(-1,0),listOf(-2,1),listOf(-3,2)))
}