package com.example;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket; // Il socket per comunicare con il client
    private GestoreGruppi gestoreGruppi; // Gestore condiviso per i gruppi
    private String username; // Nome utente del client connesso

    // Costruttore
    public ServerThread(Socket s, GestoreGruppi gg) {
        this.socket = s;
        this.gestoreGruppi = gg;
    }

    @Override
    public void run() {
        try {
            // Stream per leggere e scrivere dati dal/al client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String messaggioRicevuto;

            // Legge i comandi dal client fino alla chiusura della connessione
            while ((messaggioRicevuto = in.readLine()) != null) {
                // Divide il comando in azione e parametri
                String[] comando = messaggioRicevuto.split(" ", 2);
                String azione = comando[0].toUpperCase(); // Azione del comando

                switch (azione) {
                    case "LOGIN":
                        // Gestione del comando LOGIN
                        handleLogin(comando.length > 1 ? comando[1] : "", out);
                        break;

                    case "CREATE":
                        // Gestione della creazione di un gruppo
                        if (comando.length > 1 && comando[1].startsWith("GROUP ")) {
                            createGroup(comando[1].substring(6), out);
                        }
                        break;

                    case "CLOSE":
                        // Gestione della chiusura della connessione
                        if ("CONNECTION".equalsIgnoreCase(comando.length > 1 ? comando[1] : "")) {
                            closeConnection(out);
                            return; // Termina il thread
                        }
                        break;

                    default:
                        // Comando non riconosciuto
                        out.writeBytes("ERROR unrecognized command\n");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il client.");
        } finally {
            // Chiude silenziosamente la connessione
            closeConnectionSilently();
        }
    }

    // Metodo per gestire il comando LOGIN
    private void handleLogin(String username, DataOutputStream out) throws IOException {
        this.username = username; // Salva il nome utente
        out.writeBytes("login success\n"); // Conferma il login al client
    }

    // Metodo per creare un gruppo
    private void createGroup(String groupName, DataOutputStream out) throws IOException {
        if (gestoreGruppi.creaGruppo(groupName, username)) {
            // Se il gruppo è stato creato con successo
            out.writeBytes("OK\n");
        } else {
            // Se il gruppo già esiste
            out.writeBytes("ERROR group already exists\n");
        }
    }

    // Metodo per chiudere la connessione
    private void closeConnection(DataOutputStream out) throws IOException {
        socket.close(); // Chiude il socket
        out.writeBytes("OK\n"); // Conferma la chiusura al client
    }

    // Metodo per chiudere la connessione silenziosamente
    private void closeConnectionSilently() {
        try {
            if (!socket.isClosed()) socket.close();
        } catch (IOException ignored) {
        }
    }
}
