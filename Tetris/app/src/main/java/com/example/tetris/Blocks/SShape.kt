package com.example.tetris.Blocks

class SShape : Block() {
    override var cords = listOf(mutableListOf(3,1),mutableListOf(4,1),mutableListOf(4,0),mutableListOf(5,0))
    override var rotations = listOf(
        listOf(listOf(-1,1),listOf(0,0),listOf(1,1),listOf(2,0))
        ,listOf(listOf(1,1),listOf(0,0),listOf(1,-1),listOf(0,-2))
        ,listOf(listOf(1,-1),listOf(0,0),listOf(-1,-1),listOf(-2,0))
        ,listOf(listOf(-1,-1),listOf(0,0),listOf(-1,1),listOf(0,2)))
}