package com.example.consumingrest.TCP.Tasca2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

                // Llegir la llista enviada pel client
                Llista llista = (Llista) ois.readObject();
                System.out.println("Received list from client: " + llista.getNom() + " " + llista.getNumberList());

                // Processar la llista (eliminar repetits i ordenar)
                List<Integer> uniqueSortedNumbers = new ArrayList<>(new HashSet<>(llista.getNumberList()));
                Collections.sort(uniqueSortedNumbers);

                // Crear una nova llista amb els resultats i enviar-la al client
                Llista result = new Llista(llista.getNom(), uniqueSortedNumbers);
                oos.writeObject(result);
                System.out.println("Sent processed list to client: " + result.getNom() + " " + result.getNumberList());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
