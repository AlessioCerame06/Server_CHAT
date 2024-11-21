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
                    switch (messaggio) {
                        case 1=1:
                            
                            break;
                    
                        default:
                            break;
                    }
                }else{
                    if (messaggio.startsWith("LOGIN")) {
                        nomeUtente = messaggio.substring(6);
                    }else{
                        out.writeBytes("ERROR you must login first\n" );
                    }
            
                }
                
                
            } while (messaggio != "LOGOUT" );
            socket.close();
            System.out.println("Qualcuno si è scollegato");
        
        } catch (Exception e) {
            // TODO: handle exception
        }
            
    }

}