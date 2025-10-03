package com.example.tictacapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {

    lateinit var b1: Button
    lateinit var b2: Button
    lateinit var b3: Button
    lateinit var b4: Button
    lateinit var b5: Button
    lateinit var b6: Button
    lateinit var b7: Button
    lateinit var b8: Button
    lateinit var b9: Button
    lateinit var tvPlayer1: TextView
    lateinit var tvPlayer2: TextView
    lateinit var tvScorePlayer1: TextView
    lateinit var tvScorePlayer2: TextView

    var currentPlayer: Int = 1
    var scorePlayer1: Int = 0
    var scorePlayer2: Int = 0
    var gameFinished: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        initUI()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initUI() {
        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)
        b4 = findViewById(R.id.b4)
        b5 = findViewById(R.id.b5)
        b6 = findViewById(R.id.b6)
        b7 = findViewById(R.id.b7)
        b8 = findViewById(R.id.b8)
        b9 = findViewById(R.id.b9)
        tvPlayer1 = findViewById(R.id.tvPlayer1)
        tvPlayer2 = findViewById(R.id.tvPlayer2)
        tvScorePlayer1 = findViewById(R.id.tvScorePlayer1)
        tvScorePlayer2 = findViewById(R.id.tvScorePlayer2)

        tvPlayer1.text = intent?.extras?.getString("player1").toString()
        tvPlayer2.text = intent?.extras?.getString("player2").toString()
        nuevaPartida(tvPlayer1)
    }

    fun play(btn: View) {
        val myBtn: Button = btn as Button
        if (!gameFinished && myBtn.text.toString().isEmpty()) {
            if (currentPlayer == 1) {
                myBtn.text = "X"
                validateWinner(btn)
                currentPlayer = 2
                tvPlayer1.setTextColor(Color.GRAY)
                tvPlayer2.setTextColor(Color.BLACK)
            } else {
                myBtn.text = "O"
                validateWinner(btn)
                currentPlayer = 1
                tvPlayer1.setTextColor(Color.BLACK)
                tvPlayer2.setTextColor(Color.GRAY)
            }
        }
    }

    fun validateWinner(btn: View) {
        if (validateCards(btn)) {
            if (currentPlayer == 1) {
                scorePlayer1++
                tvScorePlayer1.text = "$scorePlayer1"
                Toast.makeText(applicationContext, "${tvPlayer1.text} Ganaste!!!", Toast.LENGTH_LONG).show()
            } else {
                scorePlayer2++
                tvScorePlayer2.text = "$scorePlayer2"
                Toast.makeText(applicationContext, "${tvPlayer2.text} Ganaste!!!", Toast.LENGTH_LONG).show()
            }
            gameFinished = true
        }
    }

    private fun validateCards(btn: View): Boolean {
        val b1val = b1.text.toString().trim()
        val b2val = b2.text.toString().trim()
        val b3val = b3.text.toString().trim()
        val b4val = b4.text.toString().trim()
        val b5val = b5.text.toString().trim()
        val b6val = b6.text.toString().trim()
        val b7val = b7.text.toString().trim()
        val b8val = b8.text.toString().trim()
        val b9val = b9.text.toString().trim()
        var winner = false

        when (btn.id) {
            b1.id -> {
                if (b1val.isNotEmpty() &&
                    ((b1val == b2val && b1val == b3val) ||
                            (b1val == b4val && b1val == b7val) ||
                            (b1val == b5val && b1val == b9val))
                ) {
                    winner = true
                }
            }
            b2.id -> {
                if (b2val.isNotEmpty() &&
                    ((b2val == b1val && b2val == b3val) ||
                            (b2val == b5val && b2val == b8val))
                ) {
                    winner = true
                }
            }
            b3.id -> {
                if (b3val.isNotEmpty() &&
                    ((b3val == b1val && b3val == b2val) ||
                            (b3val == b6val && b3val == b9val) ||
                            (b3val == b5val && b3val == b7val))
                ) {
                    winner = true
                }
            }
            b4.id -> {
                if (b4val.isNotEmpty() &&
                    ((b4val == b1val && b4val == b7val) ||
                            (b4val == b5val && b4val == b6val))
                ) {
                    winner = true
                }
            }
            b5.id -> {
                if (b5val.isNotEmpty() &&
                    ((b5val == b2val && b5val == b8val) ||
                            (b5val == b4val && b5val == b6val) ||
                            (b5val == b3val && b5val == b7val) ||
                            (b5val == b1val && b5val == b9val))
                ) {
                    winner = true
                }
            }
            b6.id -> {
                if (b6val.isNotEmpty() &&
                    ((b6val == b3val && b6val == b9val) ||
                            (b6val == b4val && b6val == b5val))
                ) {
                    winner = true
                }
            }
            b7.id -> {
                if (b7val.isNotEmpty() &&
                    ((b7val == b1val && b7val == b4val) ||
                            (b7val == b5val && b7val == b3val) ||
                            (b7val == b8val && b7val == b9val))
                ) {
                    winner = true
                }
            }
            b8.id -> {
                if (b8val.isNotEmpty() &&
                    ((b8val == b2val && b8val == b5val) ||
                            (b8val == b7val && b8val == b9val))
                ) {
                    winner = true
                }
            }
            b9.id -> {
                if (b9val.isNotEmpty() &&
                    ((b9val == b3val && b9val == b6val) ||
                            (b9val == b1val && b9val == b5val) ||
                            (b9val == b7val && b9val == b8val))
                ) {
                    winner = true
                }
            }
        }

        return winner
    }

    fun nuevaPartida(v: View) {
        b1.text = ""
        b2.text = ""
        b3.text = ""
        b4.text = ""
        b5.text = ""
        b6.text = ""
        b7.text = ""
        b8.text = ""
        b9.text = ""
        gameFinished = false
        currentPlayer = if (currentPlayer == 1) 2 else 1
        tvPlayer1.setTextColor(if (currentPlayer == 1) Color.BLACK else Color.GRAY)
        tvPlayer2.setTextColor(if (currentPlayer == 2) Color.BLACK else Color.GRAY)
    }
}
