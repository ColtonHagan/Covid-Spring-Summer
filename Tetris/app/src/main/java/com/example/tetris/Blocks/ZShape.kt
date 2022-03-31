package com.example.tetris.Blocks

class ZShape : Block() {
    override var cords = listOf(mutableListOf(3,0),mutableListOf(4,0),mutableListOf(4,1),mutableListOf(5,1))
    override var rotations = listOf(
        listOf(listOf(0,2),listOf(1,1),listOf(0,0),listOf(1,-1))
        ,listOf(listOf(2,0),listOf(1,-1),listOf(0,0),listOf(-1,-1))
        ,listOf(listOf(0,-2),listOf(-1,-1),listOf(0,0),listOf(-1,1))
        ,listOf(listOf(-2,0),listOf(-1,1),listOf(0,0),listOf(1,1)))
}