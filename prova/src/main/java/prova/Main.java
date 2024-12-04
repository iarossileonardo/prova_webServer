package prova;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception{
        ServerSocket sS0 = new ServerSocket(3000);
        int i = 0;
        
        do {
            Socket s0 = sS0.accept();
            i++;
            System.out.println("Connessione numero: " + i);
            MioThread t1 = new MioThread(s0);
            t1.start();
        } while (true);
        
    }
}