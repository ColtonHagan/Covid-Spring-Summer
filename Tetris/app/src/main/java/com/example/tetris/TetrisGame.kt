package com.example.tetris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tetris.databinding.ActivityMainBinding


class TetrisGame : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var boardView: TetrisBoard
    private lateinit var viewModel: TetrisViewModel
    private lateinit var controller: TetrisController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardView = binding.boardView
        viewModel = ViewModelProvider(this).get(TetrisViewModel::class.java)
        controller = viewModel.tetris
        controller.boardLiveData.observe(this, {updateBoard(it)})

        binding.downButton.setOnTouchListener(RepeatListener(100, 50) { controller.moveDown() })
        binding.rightButton.setOnTouchListener(RepeatListener(100, 50) { controller.moveRight() })
        binding.leftButton.setOnTouchListener(RepeatListener(100, 50) { controller.moveLeft() })
        binding.rotateButton.setOnClickListener { controller.rotate() }
    }
    private fun updateBoard(board: Array<Array<Cell>>) {
        updateScores()
        boardView.updateBoard(board)
    }
    private fun updateScore() {
        val scoreText = binding.scoreText.text
        val splitPoint = scoreText.indexOf(':')
        if(splitPoint != -1)
            binding.scoreText.text = scoreText.substring(0,splitPoint) + ": ${controller.getScore()}"
    }
    private fun updateHighScore() {
        val highScoreText = binding.highScoreView.text
        val splitPoint = highScoreText.indexOf(':')
        if(splitPoint != -1)
            binding.highScoreView.text = highScoreText.substring(0,splitPoint) + ": ${controller.getHighScore()}"
    }
    private fun updateScores() {
        updateScore()
        updateHighScore()
    }
}