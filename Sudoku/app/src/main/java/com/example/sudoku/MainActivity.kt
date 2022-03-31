package com.example.sudoku
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.game.Cell
import com.example.sudoku.model.SudokuViewModel
import com.example.sudoku.view.SudokuBoardView
import java.util.concurrent.CopyOnWriteArrayList

class MainActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {
    private lateinit var viewModel: SudokuViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardView.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(SudokuViewModel::class.java)
        viewModel.sudoku.selectedCellLiveData.observe(this, { updateSelectedCellUI(it) })
        viewModel.sudoku.cellsLiveData.observe(this, { updateCells(it as CopyOnWriteArrayList<Cell>?) })
        buttons = listOf(binding.oneButton, binding.twoButton,binding.threeButton,
            binding.fourButton,binding.fiveButton,binding.sixButton,
            binding.sevenButton,binding.eightButton, binding.nineButton)
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.sudoku.handleInput(index+1)
                handleVictory()
            }
        }
        binding.delButton.setOnClickListener {
            toggleButtons(false)
            viewModel.sudoku.deleteInput()
            toggleButtons(true)
        }
        binding.resetButton.setOnClickListener {
            toggleButtons(false)
            viewModel.sudoku.reset()
            toggleButtons(true)

        }
        binding.restartButton.setOnClickListener {
            toggleButtons(false)
            viewModel.sudoku.restart()
            toggleButtons(true)
        }
        binding.solveButton.setOnClickListener {
            toggleButtons(false)
            viewModel.sudoku.reset()
            //viewModel.sudoku.solveHelper(0,true)
            viewModel.sudoku.solve(0,false)
            viewModel.sudoku.cleanData()
            toggleButtons(true)
        }
    }
    //Not in use now but can be used latter
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        val numkey = keyCode - 7
        if (numkey in 1..9) {
            viewModel.sudoku.handleInput(numkey)
            handleVictory()
            return true
        }
        return false
    }
    private fun handleVictory() {
        if(viewModel.sudoku.victoryCheck()) {
            viewModel.sudoku.restart()
            binding.boardView.victoryMessage()
        }
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        binding.boardView.updateCellUI(cell.first, cell.second)
    }

    private fun updateCells(cells: CopyOnWriteArrayList<Cell>?) = cells?.let {
        binding.boardView.updateCells(cells)
    }
    private fun toggleButtons(toggle: Boolean) {
        toggleButton(binding.delButton, toggle)
        toggleButton(binding.resetButton, toggle)
        toggleButton(binding.restartButton, toggle)
        toggleButton(binding.solveButton, toggle)
        buttons.forEach {
            toggleButton(it, toggle)
        }
    }

    private fun toggleButton(button: Button, toggle: Boolean) {
        button.isEnabled = toggle
        button.isClickable = toggle
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudoku.update(row, col)
    }
}