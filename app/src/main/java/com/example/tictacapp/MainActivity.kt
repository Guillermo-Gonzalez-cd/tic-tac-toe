package com.example.tictacapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            // Cambia la IP según tu caso:
            socket = IO.socket("http://10.0.2.2:4001")
        } catch (e: Exception) {
            Log.e("Socket", "Error al crear socket", e)
            Toast.makeText(this, "Error al crear socket", Toast.LENGTH_SHORT).show()
            return
        }

        // Logs para depurar la conexión
        socket.on(Socket.EVENT_CONNECT) {
            Log.d("Socket", "✅ Conectado correctamente al servidor")
            runOnUiThread {
                Toast.makeText(this, "Conectado al servidor", Toast.LENGTH_SHORT).show()
            }
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            Log.e("Socket", "❌ Error de conexión: ${args.getOrNull(0)}")
            runOnUiThread {
                Toast.makeText(this, "Error de conexión al servidor", Toast.LENGTH_SHORT).show()
            }
        }

        socket.on("welcome") { args ->
            val data = args[0] as JSONObject
            Log.d("Socket", "Mensaje recibido: ${data.getString("msg")}")
            runOnUiThread {
                Toast.makeText(this, data.getString("msg"), Toast.LENGTH_SHORT).show()
            }
        }

        socket.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off(Socket.EVENT_CONNECT)
        socket.off(Socket.EVENT_CONNECT_ERROR)
        socket.off("welcome")
    }
}
