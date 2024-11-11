package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;
    private GestoreGruppi gg;
    
    public ServerThread(Socket s, GestoreGruppi gg) {
        this.socket = s;
        this.gg = gg;
    }

    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // ascolto                                                                              // (ricevere)
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // parla (invia)
            String messaggioRicevuto="";
            do {
                

            } while ( !messaggioRicevuto.equals("CLOSE CONNECTION"));
            socket.close();
            System.out.println("Qualcuno si è scollegato");


        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    
}
