package com.example.consumingrest.TCP.Tasca2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TCPServer {
    public static void main(String[] args) {
        try {
            // Crear el socket del servidor en el puerto 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                // Aceptar la conexión entrante
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + socket.getInetAddress());

                // Crear flujo de entrada para recibir objetos del cliente
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                // Recibir la lista enviada por el cliente
                Llista lista = (Llista) objectInputStream.readObject();

                // Procesar la lista: eliminar duplicados y ordenar los números
                List<Integer> numerosSinRepetirYOrdenados = lista.getNumberList().stream()
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());

                // Crear la lista modificada
                Llista listaModificada = new Llista(lista.getNom(), numerosSinRepetirYOrdenados);

                // Crear flujo de salida para enviar objetos al cliente
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                // Enviar la lista modificada al cliente
                objectOutputStream.writeObject(listaModificada);
                objectOutputStream.flush();

                // Cerrar flujos y socket
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
