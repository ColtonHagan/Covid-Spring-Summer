package com.example.chess
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chess.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ChessBoardView.OnTouchListener {
    private lateinit var boardView: ChessBoardView
    private lateinit var chessController: ChessController
    private lateinit var viewModel: ChessViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardView.also {boardView = it}
        boardView.registerListener(this)

        //viewModel = ViewModelProvider(this).get(ChessViewModel::class.java)
        //chessController.selectedCellLiveData.observe(this, Observer { updateSelectedCell(it) })
        //chessController.cellsLiveData.observe(this, Observer { updateCells(it) })
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        boardView.updateCells(cells)
    }

    private fun updateSelectedCell(cell: Pair<Int, Int>?) = cell?.let {
        boardView.updateSelectedCell(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, col: Int) {
        chessController.updateSelectedCell(row, col)
    }
}
