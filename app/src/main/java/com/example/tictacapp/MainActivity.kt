package com.example.tictacapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun nextScreem(v: View){
        var player1 = findViewById<EditText>(R.id.Player1)
        var player2 = findViewById<EditText>(R.id.Player2)

        var intent = Intent(applicationContext, GameActivity::class.java)
        intent.putExtra("player1",player1.text.toString())
        intent.putExtra("player2",player2.text.toString())
        startActivity(intent)
    }
}