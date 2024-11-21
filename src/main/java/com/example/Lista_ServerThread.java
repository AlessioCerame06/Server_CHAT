package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lista_ServerThread {
    private Map<String, ServerThread> serverThreads = new HashMap<>();

    public Lista_ServerThread() {
    }

    public void aggiungiServerThread(ServerThread serverThread, String nomeUtente){
        this.serverThreads.put(nomeUtente, serverThread);
    }

    public void rimuoviServerThread( String nomeUtente){
        this.serverThreads.remove(nomeUtente);
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
        return String.join(",", serverThreads.keySet());

    }

    public Boolean nomeUtenteValido(String nomeUtente){
        Boolean valido=true;
        if( nomeUtente == null || nomeUtente.isEmpty() || nomeUtente=="ALL"){
            valido=false;
        }
        return valido;

    }

    public List<ServerThread> ottieniServerThreads(){
            return  new ArrayList<>(this.serverThreads.values());

    }

    public ServerThread ottioniServerThread(String nomeUtente){
        return serverThreads.get(nomeUtente);
    }

    

}

