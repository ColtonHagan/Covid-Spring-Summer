package com.example.tetris.Blocks

class TShape : Block() {
    override var cords = listOf(mutableListOf(4,0),mutableListOf(3,1),mutableListOf(4,1),mutableListOf(5,1))
    override var rotations = listOf(
        listOf(listOf(1,1),listOf(-1,1),listOf(0,0),listOf(1,-1))
        ,listOf(listOf(1,-1),listOf(1,-1),listOf(0,0),listOf(-1,1))
        ,listOf(listOf(-1,-1),listOf(-1,1),listOf(0,0),listOf(1,-1))
        ,listOf(listOf(-1,1),listOf(1,-1),listOf(0,0),listOf(-1,1)))
}