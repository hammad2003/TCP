package com.example.consumingrest.TCP.Tasca1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
    public static void main(String[] args) {
        try {
            // Crear el socket del servidor en el puerto 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor TCP esperando conexiones...");

            // Aceptar la conexión entrante
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + socket.getInetAddress());

            // Crear flujo de entrada para recibir datos del cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Crear flujo de salida para enviar datos al cliente
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Generar un número aleatorio para adivinar
            SecretNum secretNum = new SecretNum(100);
            int numberToGuess = secretNum.getNum();

            // Interacción con el cliente para adivinar el número
            String guess;
            do {
                guess = in.readLine();
                String response = secretNum.comprova(guess);
                out.println(response);
            } while (!guess.equals(String.valueOf(numberToGuess)));

            // Cerrar flujos y socket
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
