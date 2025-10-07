package com.example.tictacapp
// Nuevos imports
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializa el socket
        try {
            socket = IO.socket("http://10.0.2.2:3000")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            return
        }

        // Conecta al servidor
        socket.connect()

        // Escucha el evento "welcome" desde el servidor
        socket.on("welcome") { args ->
            val data = args[0] as JSONObject
            runOnUiThread {
                Toast.makeText(this, data.getString("msg"), Toast.LENGTH_SHORT).show()
            }
        }

        // Ajuste de padding para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Función para ir a la siguiente pantalla
    fun nextScreem(v: View) {
        val player1 = findViewById<EditText>(R.id.Player1)
        val player2 = findViewById<EditText>(R.id.Player2)

        val intent = Intent(applicationContext, GameActivity::class.java)
        intent.putExtra("player1", player1.text.toString())
        intent.putExtra("player2", player2.text.toString())
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off("welcome")
    }
}
