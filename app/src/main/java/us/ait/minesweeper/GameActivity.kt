package us.ait.minesweeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    var flagChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnFlag.setOnCheckedChangeListener { _, isChecked ->
            flagChecked = isChecked
        }

        btnReset.setOnClickListener {
            minesweeper_view.clearGame()
        }
    }

    fun displayResults(message: String) {
        var intent = Intent(this@GameActivity, ResultsActivity::class.java)
        intent.putExtra("text", message)
        startActivity(intent)
    }
}
