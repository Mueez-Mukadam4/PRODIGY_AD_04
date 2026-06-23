package com.example.tictactoeapp

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvTurn: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var btnReset: Button

    private var board = Array(3) { Array(3) { "" } }
    private var currentPlayer = "X"
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTurn = findViewById(R.id.tvTurn)
        gridLayout = findViewById(R.id.gridLayout)
        btnReset = findViewById(R.id.btnReset)

        btnReset.setOnClickListener {
            resetGame()
        }

        for (row in 0..2) {
            for (col in 0..2) {
                val buttonId = resources.getIdentifier("btn$row$col", "id", packageName)
                val button = findViewById<Button>(buttonId)
                button.setOnClickListener {
                    onCellClick(row, col, button)
                }
            }
        }

        resetGame()
    }

    private fun onCellClick(row: Int, col: Int, button: Button) {
        if (gameOver) return
        if (board[row][col].isNotEmpty()) return

        board[row][col] = currentPlayer
        button.text = currentPlayer

        if (checkWin(row, col, currentPlayer)) {
            val message = if (currentPlayer == "X") {
                getString(R.string.winner_x)
            } else {
                getString(R.string.winner_o)
            }
            tvTurn.text = message
            gameOver = true
            return
        }

        if (isBoardFull()) {
            tvTurn.text = getString(R.string.draw)
            gameOver = true
            return
        }

        currentPlayer = if (currentPlayer == "X") "O" else "X"
        tvTurn.text = "Player $currentPlayer's Turn"
    }

    private fun checkWin(row: Int, col: Int, player: String): Boolean {
        if (board[row][0] == player && board[row][1] == player && board[row][2] == player) return true
        if (board[0][col] == player && board[1][col] == player && board[2][col] == player) return true
        if (row == col && board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
        if (row + col == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col].isEmpty()) return false
            }
        }
        return true
    }

    private fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameOver = false
        tvTurn.text = getString(R.string.turn_x)

        for (row in 0..2) {
            for (col in 0..2) {
                val buttonId = resources.getIdentifier("btn$row$col", "id", packageName)
                val button = findViewById<Button>(buttonId)
                button.text = ""
            }
        }
    }
}