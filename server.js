const express = require("express");
const http = require("http");
const {Server} = require("socket.io");
const cors = require("cors");

const app = express();
app.use(cors());

const server = http.createServer(app);
const io = new Server(server, { cors: { origin: "*" } });

app.get("/status", (req, res) => {
    res.json({ msg: "Servidor TicTacToe Corriendo" });
});

io.on("connection", (socket) => {
    console.log("Jugador Conectado: ", socket.id);
    socket.emit("welcome", { msg: "Bienvenido a TicTacToe" });
});

server.listen(3000, () => {
    console.log("Servidor escuchando en http://localhost:3000");
});

socket.on("move", (data) => {
  console.log("Movimiento recibido:", data);
  io.emit("move", data); // enviar la jugada a todos los clientes conectados
});
