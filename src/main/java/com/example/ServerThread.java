package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ServerThread extends Thread {
    private Socket socket;
    private Lista_ServerThread listaThread;

    public ServerThread(Socket socket, Lista_ServerThread listaThread) {
        this.socket = socket;
        this.listaThread = listaThread;
    }

    public String inviaMessaggio(String messaggio) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.writeBytes(messaggio + "\n");
            String messaggioRicevuto=in.readLine();
            if(messaggioRicevuto=="OK MSG"){
                return "OK MSG";

            }else{
                return "ERROR MSG server error";

            }

        } catch (Exception e) {
            return "ERROR MSG server error";
        }
    }
    

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String messaggioRicevuto;
            Boolean login=false;
            String nomeUtente="";
            do {
                messaggioRicevuto=in.readLine();
                
                if(login){
                    if(messaggioRicevuto.startsWith("USERS")){
                        try {
                            String listaUtenti = listaThread.listaUtenti();
                            out.writeBytes("OK USERS "+listaUtenti+"\n");

                        } catch (Exception e) {
                            out.writeBytes("ERROR USERS\n");
                        }
                        
                    }else if(messaggioRicevuto.startsWith("MSG")){
                        String[] parti = messaggioRicevuto.split(" ", 3);
                        String destinatario = parti[1];
                        String messaggioDaInviare = parti[2];

                        if (destinatario.equals("ALL")) {
                            try {
                                List<ServerThread> serverThreads = listaThread.ottieniServerThreads();
                                for(int i=0; i<serverThreads.size(); i++){
                                    if(serverThreads.get(i)!=this){
                                        serverThreads.get(i).inviaMessaggio("MSG " + nomeUtente + " ALL " + messaggioDaInviare);

                                    }
            
                                }

                                out.writeBytes("OK MSG ALL\n");
                            } catch (Exception e) {
                                out.writeBytes("ERROR MSG ALL server error\n");
                            }

                        } else {
                            if(listaThread.nomeUtenteEsiste(destinatario)){
                                try {
                                    ServerThread serverThread = listaThread.ottioniServerThread(destinatario);
                                    out.writeBytes(serverThread.inviaMessaggio("MSG " + nomeUtente + " PRIVATE " + messaggioDaInviare)+"\n");
                                } catch (Exception e) {
                                    out.writeBytes("ERROR MSG server error\n");
                                }
                            }else{
                                out.writeBytes("ERROR MSG user not found\n");

                            }

                        }


                    }else if(messaggioRicevuto.startsWith("LOGOUT")){
                            out.writeBytes("OK LOGOUT\n");
                    }else{
                        out.writeBytes("ERROR command not recognized\n");
                    }
                }else{
                    if (messaggioRicevuto.startsWith("LOGIN")) {
                        nomeUtente = (messaggioRicevuto.substring(6)).trim();
                        if(!listaThread.nomeUtenteEsiste(nomeUtente)){
                            if(listaThread.nomeUtenteValido(nomeUtente)){
                                listaThread.aggiungiServerThread(this, nomeUtente);
                                login=true;
                                out.writeBytes("OK LOGIN "+ nomeUtente + "\n");
                            }else{
                                out.writeBytes("ERROR LOGIN username not valid\n");

                            }

                            }else{
                            out.writeBytes("ERROR LOGIN username already used\n");

                        }
                        
                    }else{
                        out.writeBytes("ERROR you must login first\n" );
                    }
            
                }
                
                
            } while (messaggioRicevuto != "LOGOUT" );
            listaThread.rimuoviServerThread( nomeUtente);
            socket.close();
            System.out.println("Qualcuno si è scollegato");
        
        } catch (Exception e) {
            System.out.println("errore ServerThread");
        }
            
    }

}