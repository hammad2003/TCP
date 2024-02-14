package com.example.consumingrest.TCP.Tasca2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1"; // IP del servidor
        final int SERVER_PORT = 12345; // Port del servidor

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // Crear una llista per enviar al servidor
            List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 2, 5, 8, 10));
            Llista llistaToSend = new Llista("ExampleList", numbers);

            // Enviar la llista al servidor
            oos.writeObject(llistaToSend);
            System.out.println("Sent list to server: " + llistaToSend.getNom() + " " + llistaToSend.getNumberList());

            // Rebem la llista processada del servidor
            Llista processedList = (Llista) ois.readObject();
            System.out.println("Received processed list from server: " + processedList.getNom() + " " + processedList.getNumberList());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}