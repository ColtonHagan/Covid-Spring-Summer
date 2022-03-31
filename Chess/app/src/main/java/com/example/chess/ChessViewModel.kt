package com.example.chess

import androidx.lifecycle.ViewModel

open class ChessViewModel() : ViewModel() {
    val chess = ChessController()
}