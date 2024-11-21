package com.example;

import java.util.HashMap;
import java.util.Map;

public class Lista_ServerThread {
    private Map<String, ServerThread> serverThreads = new HashMap<>();

    public Lista_ServerThread() {
    }

    public void aggiungiServerThread(ServerThread serverThread, String nomeUtente){
        this.serverThreads.put(nomeUtente, serverThread);
    }

    public Map<String, ServerThread> getserverThreads() {
        return serverThreads;
    }

    public void setserverThreads(Map<String, ServerThread> serverThreads) {
        this.serverThreads = serverThreads;
    }

    public Boolean nomeUtenteEsiste(String nomeUtente){
        return serverThreads.containsKey(nomeUtente);
 
    }

    public String listaUtenti(){
        return String.join(", ", serverThreads.keySet());

    }

    

    

}

