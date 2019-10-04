package us.ait.minesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import us.ait.minesweeper.Field
import us.ait.minesweeper.GameActivity
import us.ait.minesweeper.R
import us.ait.minesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet) : View(context, attrs) {
    var paintBackground: Paint = Paint()
    var paintLine: Paint = Paint()
    var paintText: Paint = Paint()
    var flagImage : Bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.flag)
    var mineImage : Bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.mine)

    init {
        paintBackground.color = Color.GRAY
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 8f

        paintText.color = Color.WHITE
        paintText.textAlign = Paint.Align.CENTER

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paintText.textSize = height / 5f
        flagImage = Bitmap.createScaledBitmap(flagImage, width/6, height/6, false)
        mineImage = Bitmap.createScaledBitmap(mineImage, width/6, height/6, false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paintBackground.color = Color.GRAY
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawFields(canvas)
        drawBoard(canvas)
    }

    private fun drawBoard(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        for (i in 1 until 5) {
            canvas?.drawLine(
                0f, (i * height / 5).toFloat(), width.toFloat(), (i * height / 5).toFloat(),
                paintLine
            )
            canvas?.drawLine(
                (i * width / 5).toFloat(), 0f, (i * width / 5).toFloat(), height.toFloat(),
                paintLine
            )
        }

    }

    private fun drawFields(canvas: Canvas?) {
        var field : Field
        var tX  : Float
        var tY : Float
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                tX = (i * width / 5).toFloat()
                tY = (j * height / 5).toFloat()
                field = MinesweeperModel.getFieldContent(i, j)
                if (field.isFlagged) {
                    paintBackground.color = Color.LTGRAY
                    canvas?.drawRect(tX, tY ,tX + width.toFloat()/5,tY + height.toFloat()/5 , paintBackground)
                    canvas?.drawBitmap(flagImage, tX, tY, null)

                } else if (!field.isMine && field.wasClicked) {
                    paintBackground.color = Color.parseColor(context.getString(R.string.green_color))
                    canvas?.drawRect(tX, tY ,tX + width.toFloat()/5,tY + height.toFloat()/5 , paintBackground)
                    canvas?.drawText(field.minesAround.toString(), tX + height.toFloat()/10, tY + height.toFloat()/6, paintText)

                } else if (field.isMine && field.wasClicked && !field.isFlagged) {
                    paintBackground.color = Color.parseColor(context.getString(R.string.color_pink))
                    canvas?.drawRect(tX, tY ,tX + width.toFloat()/5,tY + height.toFloat()/5 , paintBackground)
                    canvas?.drawBitmap(mineImage, tX, tY, null)

                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var flagStatus = (context as GameActivity).flagChecked

        if (event?.action == MotionEvent.ACTION_DOWN && !MinesweeperModel.checkGameOver()) {
            val tX: Int = event.x.toInt() / (width / 5)
            val tY: Int = event.y.toInt() / (height / 5)
            if (tX < 5 && tY < 5 && !MinesweeperModel.getFieldContent(tX, tY).wasClicked) {
                MinesweeperModel.updateGameStatus(tX, tY, flagStatus)
                if (MinesweeperModel.checkGameOver() && MinesweeperModel.checkWinGame()) {
                    (context as GameActivity).displayResults(context.getString(R.string.won_text))
                } else if (MinesweeperModel.checkGameOver() && !MinesweeperModel.checkWinGame()) {
                    (context as GameActivity).displayResults(context.getString(R.string.lost_text))
                }
            }
            invalidate()
        }
        return true
    }

    fun clearGame() {
        MinesweeperModel.resetBoard()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }
}