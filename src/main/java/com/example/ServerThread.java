package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private Lista_ServerThread listaThread;

    public ServerThread(Socket socket, Lista_ServerThread listaThread) {
        this.socket = socket;
        this.listaThread = listaThread;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String messaggio;
            Boolean login=false;
            String nomeUtente;
            do {
                messaggio=in.readLine();
                
                if(login){
                    if(messaggio.startsWith("USERS")){
                        try {
                            String listaUtenti = listaThread.listaUtenti();
                            out.writeBytes("OK USERS "+listaUtenti+"\n");

                        } catch (Exception e) {
                            out.writeBytes("ERROR USERS\n");
                        }
                        
                    }else if(messaggio.startsWith("MSG")){


                    }else if(messaggio.startsWith("LOGOUT")){
                        try {
                            out.writeBytes("OK LOGOUT\n");
                        } catch (Exception e) {
                            out.writeBytes("ERROR LOGOUT\n");                        }
                    }else{
                        out.writeBytes("ERROR command not recognized\n");
                    }
                }else{
                    if (messaggio.startsWith("LOGIN")) {
                        nomeUtente = messaggio.substring(6);
                        if(!listaThread.nomeUtenteEsiste(nomeUtente)){
                            listaThread.aggiungiServerThread(this, nomeUtente);
                            login=true;
                            out.writeBytes("OK LOGIN "+ nomeUtente + "\n");
                        }else{
                            out.writeBytes("ERROR LOGIN username already used\n");

                        }
                        
                    }else{
                        out.writeBytes("ERROR you must login first\n" );
                    }
            
                }
                
                
            } while (messaggio != "LOGOUT" );
            socket.close();
            System.out.println("Qualcuno si è scollegato");
        
        } catch (Exception e) {
            System.out.println("errore ServerThread");
        }
            
    }

}