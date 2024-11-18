package com.example;

import java.util.ArrayList;

public class Gruppo {
    private String nome; // Nome del gruppo
    private String creatore; // Creatore del gruppo
    private ArrayList<String> membri; // Lista dei membri del gruppo

    // Costruttore
    public Gruppo(String nome, String creatore) {
        this.nome = nome;
        this.creatore = creatore;
        this.membri = new ArrayList<>();
        this.membri.add(creatore); // Aggiunge il creatore come primo membro
    }

    // Restituisce il nome del gruppo
    public String getNome() {
        return nome;
    }

    // Restituisce il creatore del gruppo
    public String getCreatore() {
        return creatore;
    }

    // Restituisce i membri del gruppo
    public ArrayList<String> getMembri() {
        return membri;
    }

    // Aggiunge un membro al gruppo
    public boolean aggiungiMembro(String username) {
        if (!membri.contains(username)) {
            membri.add(username);
            return true;
        }
        return false;
    }

    // Rimuove un membro dal gruppo
    public boolean rimuoviMembro(String username) {
        return membri.remove(username);
    }
}
