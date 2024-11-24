package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread {
    private Socket socket;
    private Lista_ServerThread listaThread;
    private String nomeUtente = null;

    public ServerThread(Socket socket, Lista_ServerThread listaThread) {
        this.socket = socket;
        this.listaThread = listaThread;
    }

    private void inviaMessaggio(String messaggio) throws Exception {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeBytes(messaggio + "\n");
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String messaggioRicevuto;
            boolean login = false;

            while ((messaggioRicevuto = in.readLine()) != null) {
                System.out.println("Comando ricevuto: " + messaggioRicevuto);

                if (!login) {
                    if (messaggioRicevuto.startsWith("LOGIN")) {
                        nomeUtente = messaggioRicevuto.substring(6).trim();
                        if (listaThread.nomeUtenteValido(nomeUtente) && !listaThread.nomeUtenteEsiste(nomeUtente)) {
                            listaThread.aggiungiServerThread(this, nomeUtente);
                            login = true;
                            out.writeBytes("OK LOGIN " + nomeUtente + "\n");
                        } else {
                            out.writeBytes("ERROR LOGIN username not valid or already used\n");
                        }
                    } else {
                        out.writeBytes("ERROR you must login first\n");
                    }
                } else {
                    if (messaggioRicevuto.startsWith("USERS")) {
                        out.writeBytes("OK USERS " + listaThread.listaUtenti() + "\n");
                    } else if (messaggioRicevuto.startsWith("MSG")) {
                        String[] parti = messaggioRicevuto.split(" ", 3);
                        if (parti.length < 3) {
                            out.writeBytes("ERROR MSG invalid format\n");
                            continue;
                        }
                        String destinatario = parti[1];
                        String messaggio = parti[2];

                        if (destinatario.equalsIgnoreCase("ALL")) {
                            List<ServerThread> threads = listaThread.ottieniServerThreads();
                            for (ServerThread thread : threads) {
                                if (thread != this) {
                                    thread.inviaMessaggio("MSG " + nomeUtente + " ALL " + messaggio);
                                }
                            }
                            out.writeBytes("OK MSG ALL\n");
                        } else if (listaThread.nomeUtenteEsiste(destinatario)) {
                            ServerThread destinatarioThread = listaThread.ottieniServerThread(destinatario);
                            destinatarioThread.inviaMessaggio("MSG " + nomeUtente + " PRIVATE " + messaggio);
                            out.writeBytes("OK MSG PRIVATE\n");
                        } else {
                            out.writeBytes("ERROR MSG user not found\n");
                        }
                    } else if (messaggioRicevuto.equalsIgnoreCase("LOGOUT")) {
                        out.writeBytes("OK LOGOUT\n");
                        break;
                    } else {
                        out.writeBytes("ERROR command not recognized\n");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore nel thread utente: " + e.getMessage());
        } finally {
            if (nomeUtente != null) {
                listaThread.rimuoviServerThread(nomeUtente);
                System.out.println("Utente " + nomeUtente + " disconnesso.");
            }
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Errore nella chiusura del socket: " + e.getMessage());
            }
        }
    }
}