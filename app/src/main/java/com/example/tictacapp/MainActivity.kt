package com.example.tictacapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Activar el modo edge-to-edge
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            socket = IO.socket("http://10.0.2.2:3000")
        } catch (e: Exception) {
            Log.e("SocketIO", "Error al crear socket: ${e.message}")
            Toast.makeText(this, "Error al crear socket", Toast.LENGTH_SHORT).show()
        }

        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on("welcome", onWelcome)

        socket.connect()

        val move = JSONObject().put("cell", 4).put("player", "X")
        socket.emit("move", move)
    }

    private val onConnect = Emitter.Listener {
        runOnUiThread {
            Toast.makeText(this, "Conectado al servidor", Toast.LENGTH_SHORT).show()
            Log.i("SocketIO", "Conectado al servidor")
        }
    }

    private val onConnectError = Emitter.Listener { args ->
        runOnUiThread {
            Toast.makeText(this, "Error de conexión al servidor", Toast.LENGTH_SHORT).show()
            Log.e("SocketIO", "Error de conexión: ${args.getOrNull(0)}")
        }
    }

    private val onWelcome = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        val mensaje = data.getString("msg")
        runOnUiThread {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            Log.i("SocketIO", "Mensaje welcome: $mensaje")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off(Socket.EVENT_CONNECT, onConnect)
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.off("welcome", onWelcome)
    }

    fun nextScreem(v: View) {
        val player1 = findViewById<EditText>(R.id.Player1)
        val player2 = findViewById<EditText>(R.id.Player2)

        val intent = Intent(applicationContext, GameActivity::class.java)
        intent.putExtra("player1", player1.text.toString())
        intent.putExtra("player2", player2.text.toString())
        startActivity(intent)
    }
}
