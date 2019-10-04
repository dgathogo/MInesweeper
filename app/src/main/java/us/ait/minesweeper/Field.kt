package us.ait.minesweeper

data class Field(var isMine: Boolean, var minesAround: Int, var isFlagged: Boolean, var wasClicked: Boolean)