package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Lista_ServerThread {
    private ConcurrentMap<String, ServerThread> serverThreads = new ConcurrentHashMap<>();

    public void aggiungiServerThread(ServerThread serverThread, String nomeUtente) {
        serverThreads.put(nomeUtente, serverThread);
    }

    public void rimuoviServerThread(String nomeUtente) {
        serverThreads.remove(nomeUtente);
    }

    public boolean nomeUtenteEsiste(String nomeUtente) {
        return serverThreads.containsKey(nomeUtente);
    }

    public String listaUtenti() {
        return String.join(",", serverThreads.keySet());
    }

    public boolean nomeUtenteValido(String nomeUtente) {
        return nomeUtente != null && !nomeUtente.isEmpty() && !nomeUtente.equalsIgnoreCase("ALL");
    }

    public List<ServerThread> ottieniServerThreads() {
        return new ArrayList<>(serverThreads.values());
    }

    public ServerThread ottieniServerThread(String nomeUtente) {
        return serverThreads.get(nomeUtente);
    }
}


