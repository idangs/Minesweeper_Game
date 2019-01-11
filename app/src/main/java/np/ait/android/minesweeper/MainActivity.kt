package np.ait.android.minesweeper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import np.ait.android.minesweeper.ui.MinesweeperView
import np.ait.android.minesweeper.ui.model.MineModel

import np.ait.android.minesweeper.ui.model.MineModel.resetModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRestart.setOnClickListener {
            GameView.restart()
        }

        simpleToggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            val msg = getString(R.string.status) + if (isChecked) getString(R.string.trymessage) else getString(R.string.flagging)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

    }

    public fun showMessage(msg: String) {
        Snackbar.make(GameView, msg, Snackbar.LENGTH_LONG).show()
                }

    }


