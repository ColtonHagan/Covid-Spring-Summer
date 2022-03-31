package com.example.sudoku.model

import androidx.lifecycle.ViewModel
import com.example.sudoku.game.SudokuController

class SudokuViewModel : ViewModel() {
    val sudoku = SudokuController()
}