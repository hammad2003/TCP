package com.example.consumingrest.TCP.Tasca1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Establecer conexión con el servidor en el puerto 12345
            Socket socket = new Socket("localhost", 12345);

            // Crear flujo de salida para enviar datos al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Crear flujo de entrada para recibir datos del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Interacción con el usuario para adivinar el número
            Scanner scanner = new Scanner(System.in);
            String response;
            do {
                System.out.println("Introduce un número:");
                String guess = scanner.nextLine();
                out.println(guess);
                response = in.readLine();
                System.out.println(response);
            } while (!response.equals("Correcte"));

            // Cerrar flujos y socket
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
