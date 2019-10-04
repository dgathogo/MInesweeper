package us.ait.minesweeper.model

import us.ait.minesweeper.Field

object MinesweeperModel {

    private var winGame = false
    private var gameOver = false


    private val board: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 2, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false)
        ),
        arrayOf(
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = true, minesAround = 0, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 2, isFlagged = false, wasClicked = false),
            Field(isMine = true, minesAround = 0, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false)
        ),
        arrayOf(
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 2, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 3, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 2, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false)
            ),
        arrayOf(
            Field(isMine = false, minesAround = 0, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = true, minesAround = 0, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 0, isFlagged = false, wasClicked = false)
        ),
        arrayOf(
            Field(isMine = false, minesAround = 0, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 1, isFlagged = false, wasClicked = false),
            Field(isMine = false, minesAround = 0, isFlagged = false, wasClicked = false)
        )
    )

    fun getFieldContent(x: Int, y: Int) = board[x][y]

    fun resetBoard() {
        for (i in board[0].indices) {
            for (j in board[0].indices) {
                board[i][j].isFlagged = false
                board[i][j].wasClicked = false
            }
        }
        gameOver = false
        winGame = false
    }

    fun checkGameOver() = gameOver

    fun checkWinGame() = winGame

    fun updateGameStatus(x: Int, y: Int, flagChecked: Boolean) {
        var field = board[x][y]
        if (!gameOver) {
            field.wasClicked = true
            if (field.isMine && !flagChecked) {
                gameOver = true
                return
            } else if (!field.isMine && flagChecked) {
                field.isFlagged = true
                gameOver = true
                return
            } else if (field.isMine && flagChecked) {
                field.isFlagged = true
            }
            // win game if all mines are flagged
            for (i in 0 until 5) {
                for (j in 0 until 5) {
                    if (board[i][j].isMine && !board[i][j].isFlagged) return
                }
            }
            gameOver = true
            winGame = true
        }
    }


}