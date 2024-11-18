package com.example;

import java.util.ArrayList;

public class GestoreGruppi {
    private ArrayList<Gruppo> gruppi = new ArrayList<>(); // Lista di tutti i gruppi

    // Metodo per creare un gruppo
    public boolean creaGruppo(String nomeGruppo, String creatore) {
        // Controlla se un gruppo con lo stesso nome esiste già
        for (Gruppo gruppo : gruppi) {
            if (gruppo.getNome().equals(nomeGruppo)) {
                return false; // Il gruppo esiste già
            }
        }
        // Crea un nuovo gruppo e lo aggiunge alla lista
        Gruppo nuovoGruppo = new Gruppo(nomeGruppo, creatore);
        gruppi.add(nuovoGruppo);
        return true;
    }

    // Metodo per ottenere un gruppo per nome
    public Gruppo getGruppo(String nomeGruppo) {
        for (Gruppo gruppo : gruppi) {
            if (gruppo.getNome().equals(nomeGruppo)) {
                return gruppo; // Restituisce il gruppo trovato
            }
        }
        return null; // Nessun gruppo trovato
    }
}
