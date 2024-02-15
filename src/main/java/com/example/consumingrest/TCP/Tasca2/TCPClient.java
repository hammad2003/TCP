package com.example.consumingrest.TCP.Tasca2;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Establecer conexión con el servidor en el puerto 12345
            Socket socket = new Socket("localhost", 12345);

            // Crear flujo de salida para enviar objetos al servidor
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // Crear la lista con los datos deseados
            Llista llista = new Llista("Lista de números", List.of(3, 1, 2, 3, 4, 2, 5, 6, 4));

            // Enviar la lista al servidor
            objectOutputStream.writeObject(llista);
            objectOutputStream.flush();

            // Crear flujo de entrada para recibir objetos del servidor
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Recibir la lista modificada del servidor
            Llista listaModificada = (Llista) objectInputStream.readObject();

            // Imprimir la lista modificada
            System.out.println("Lista modificada recibida del servidor:");
            System.out.println("Nombre: " + listaModificada.getNom());
            System.out.println("Números ordenados y sin repetir: " + listaModificada.getNumberList());

            // Cerrar los flujos y el socket
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
