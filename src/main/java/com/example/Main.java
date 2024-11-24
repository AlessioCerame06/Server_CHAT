package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int porta = 10100;
        Lista_ServerThread listaServerThread = new Lista_ServerThread();

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Server in ascolto sulla porta " + porta);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + socket.getInetAddress());

                ServerThread thread = new ServerThread(socket, listaServerThread);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Errore durante l'avvio del server: " + e.getMessage());
        }
    }
}

