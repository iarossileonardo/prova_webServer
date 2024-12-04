package prova;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MioThread extends Thread{
    Socket s0;

    public MioThread(Socket s0){
        this.s0 = s0;
    }

    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s0.getInputStream()));
            DataOutputStream out = new DataOutputStream(s0.getOutputStream());
            int conta = 0;
            String op = "";
            
            do {
                String s1 = in.readLine();
                if (conta == 0) {
                    op = s1.split(" ")[0];
                }
                if ((s1.isEmpty()) && (op.equals("GET"))) {
                    String bodyRisposta = "<html><body>Pagina trovata <br><br> EVVIVA</body></html>";
                    out.writeBytes("HTTP/1.1 200 OK\r\n");
                    out.writeBytes("Content-Length: " + bodyRisposta.length() + "\r\n");
                    out.writeBytes("Content-Type: text/html\r\n");
                    out.writeBytes("\r\n");
                    out.writeBytes(bodyRisposta);
                    break;
                }
                conta++;
            } while (true);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
