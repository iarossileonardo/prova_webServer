package prova;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MioThread extends Thread {
    Socket s0;

    public MioThread(Socket s0) {
        this.s0 = s0;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s0.getInputStream()));
            DataOutputStream out = new DataOutputStream(s0.getOutputStream());
            String firstLine = "";

            firstLine = in.readLine();
            String op = firstLine.split(" ")[0];
            String resource = firstLine.split(" ")[1];
            String version = firstLine.split(" ")[2];

            String header;
            do {
                header = in.readLine();
                System.out.println(header);
            } while (!header.isEmpty());

            if ((resource.equals("/")) || (resource.equals("/index.html"))) {
                String bodyRisposta = "<html><body>Pagina trovata <br><br><b>EVVIVA</b></body></html>";
                out.writeBytes("HTTP/1.1 200 OK\r\n");
                out.writeBytes("Content-Length: " + bodyRisposta.length() + "\r\n");
                out.writeBytes("Content-Type: text/html\r\n");
                out.writeBytes("\r\n");
                out.writeBytes(bodyRisposta);
            } else {
                String bodyRisposta = "<html><body>Sei <b>fuori strada</b></body></html>";
                out.writeBytes("HTTP/1.1 404 Not found\n");
                out.writeBytes("Content-Lenght: " + bodyRisposta.length() + "\n");
                out.writeBytes("Content-Type: text/html\n");
                out.writeBytes("\n");
                out.writeBytes(bodyRisposta);
            }
        } catch (Exception e) {
            System.out.println("ERRORE");
            e.printStackTrace();
        }
    }
}
