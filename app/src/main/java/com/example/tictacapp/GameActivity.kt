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

    var currentPlayer:Int = 1
    var scorePlayer1:Int = 0
    var scorePlayer2:Int = 0
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


    tvPlayer1.text = intent ?.extras?.getString("player1").toString()
    tvPlayer2.text = intent ?.extras?.getString("player2").toString()
    nuevaPartida(tvPlayer1)
    }

    fun play(btn: View){
        val myBtn: Button = btn as Button
        if (gameFinished && myBtn.text.toString().isEmpty()){
            if (currentPlayer == 1){
                myBtn.text = "X"
                validateWinner(btn)
                currentPlayer = 2
                tvPlayer1.setTextColor(Color.GRAY)
                tvPlayer2.setTextColor(Color.BLACK)
            }else{
                myBtn.text = "O"
                validateWinner(btn)
                currentPlayer = 1
                tvPlayer1.setTextColor(Color.BLACK)
                tvPlayer2.setTextColor(Color.GRAY)
            }
        }
    }
    fun validateWinner(btn: View){

        if(validateCards(btn))

        {
            if(currentPlayer==1){
                scorePlayer1++
                tvScorePlayer1.text = "$scorePlayer1"
                Toast.makeText(applicationContext, "${tvPlayer1.text} Ganaste!!!", Toast.LENGTH_LONG).show()
            }else{
                scorePlayer2++
                tvScorePlayer2.text = "$scorePlayer2"
                Toast.makeText(applicationContext, "${tvPlayer2.text} Ganaste!!!", Toast.LENGTH_LONG).show()
            }
            gameFinished = true
        }


    }

    private fun validateCards(btn: View): Boolean {
        var b1val = b1.text.toString().trim()
        var b2val = b2.text.toString().trim()
        var b3val = b3.text.toString().trim()
        var b4val = b4.text.toString().trim()
        var b5val = b5.text.toString().trim()
        var b6val = b6.text.toString().trim()
        var b7val = b7.text.toString().trim()
        var b8val = b8.text.toString().trim()
        var b9val = b9.text.toString().trim()
        var winner = false
        when(btn.id){
            b1.id->{
                if(!b1val.isEmpty()
                    &&
                    b1val.equals(b2val) && b1val.equals(b3val)
                    ||
                    (b1val.equals(b5val) && b1val.equals(b9val))
                    ||
                    (b1val.equals(b4val) && b1val.equals(b7val))
                    ){
                    winner = true
                }
            b2.id->{
                    if (!b2val.isEmpty() &&
                        b2val.equals(b1val) && b2val.equals(b3val)
                        ||
                        (b2val.equals(b5val) && b2val.equals(b8val))
                    ){
                    winner = true
                    }
                }
            b3.id->{
                    if (!b3val.isEmpty() &&
                        b3val.equals(b1val) && b3val.equals(b2val)
                        ||
                        (b3val.equals(b6val) && b3val.equals(b9val)
                        ||
                        (b3val.equals(b5val) && b3val.equals(b7val)))
                    ){
                        winner = true
                    }
                }
            b4.id->{
                    if (!b4val.isEmpty() &&
                        b4val.equals(b1val) && b4val.equals(b7val)
                        ||
                        (b4val.equals(b5val) && b4val.equals(b6val)
                        ||
                        (b3val.equals(b5val) && b3val.equals(b7val)))
                    ) {
                        winner = true
                    }
                }
                b5.id->{
                    if (!b5val.isEmpty() &&
                        b5val.equals(b2val) && b5val.equals(b8val)
                        ||
                        (b5val.equals(b4val) && b5val.equals(b6val)
                        ||
                        (b5val.equals(b3val) && b5val.equals(b7val)
                        ||
                        (b5val.equals(b1val) && b5val.equals(b9val))))
                    ) {
                        winner = true
                    }
                }
                b6.id->{
                    if (!b6val.isEmpty() &&
                        b6val.equals(b4val) && b6val.equals(b5val)
                        ||
                        (b4val.equals(b5val) && b4val.equals(b6val)))
                    ) {
                        winner = true
                    }
                }
                b7.id->{
                    if (!b7val.isEmpty() &&
                        b7val.equals(b1val) && b7val.equals(b4val)
                        ||
                        (b7val.equals(b3val) && b7val.equals(b5val)
                        ||
                        (b7val.equals(b8val) && b7val.equals(b9val)))
                    ) {
                        winner = true
                    }
                }
                b8.id->{
                    if (!b8val.isEmpty() &&
                        b8val.equals(b7val) && b8val.equals(b9val)
                        ||
                        (b8val.equals(b5val) && b8val.equals(b2val)))
                    ) {
                        winner = true
                    }
                }
                b9.id->{
                    if (!b9val.isEmpty() &&
                        b9val.equals(b3val) && b9val.equals(b7val)
                        ||
                        (b9val.equals(b8val) && b9val.equals(b6val)))
                    ) {
                        winner = true
                    }
                }
                    else->{
                        winner = false
                    }
                }

        }

        return winner
    }
    fun nuevaPartida(v: View){
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
        currentPlayer = if (currentPlayer==1) 2 else 1
        tvPlayer1.setTextColor(if(currentPlayer==1) Color.BLACK else Color.GRAY)
        tvPlayer2.setTextColor(if(currentPlayer==2) Color.BLACK else Color.GRAY)
    }

}