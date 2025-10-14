const express = require("express");
const http = require("http");
const { Server } = require("socket.io");
const cors = require("cors");

const app = express();
app.use(cors());

const server = http.createServer(app);
const io = new Server(server, { cors: { origin: "*" } });

// Ruta simple para comprobar el estado del servidor
app.get("/status", (req, res) => {
    res.json({ msg: "Servidor TicTacToe Corriendo" });
});

// Evento de conexión de Socket.IO
io.on("connection", (socket) => {
    console.log("Jugador Conectado: ", socket.id);

    // Enviar mensaje de bienvenida al cliente
    socket.emit("welcome", { msg: "Bienvenido a TicTacToe" });

    // Escuchar jugadas del cliente
    socket.on("move", (data) => {
        console.log("Movimiento recibido:", data);
        io.emit("move", data); // reenviar jugada a todos los clientes
    });
});

// Escuchar en el puerto 4001
server.listen(4001, () => {
    console.log("Servidor escuchando en http://localhost:4001");
});
