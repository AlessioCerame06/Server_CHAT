package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        final int PORT = 10100; // Porta del server
        GestoreGruppi gestoreGruppi = new GestoreGruppi(); // Inizializza il gestore dei gruppi

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server avviato sulla porta " + PORT);

            // Ciclo infinito per accettare connessioni dai client
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Attende una connessione
                System.out.println("Un client si è connesso.");

                // Crea un nuovo thread per gestire il client
                ServerThread clientThread = new ServerThread(clientSocket, gestoreGruppi);
                clientThread.start(); // Avvia il thread
            }
        } catch (IOException e) {
            System.err.println("Errore nell'avvio del server: " + e.getMessage());
        }
    }
}
