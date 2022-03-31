package com.example.tetris.Blocks

class OShape : Block() {
    override var cords = listOf(mutableListOf(4,0),mutableListOf(5,0),mutableListOf(4,1),mutableListOf(5,1))
    override var rotations = listOf(
        listOf(listOf(0,0),listOf(0,0),listOf(0,0),listOf(0,0))
        ,listOf(listOf(0,0),listOf(0,0),listOf(0,0),listOf(0,0))
        ,listOf(listOf(0,0),listOf(0,0),listOf(0,0),listOf(0,0))
        ,listOf(listOf(0,0),listOf(0,0),listOf(0,0),listOf(0,0)))
}