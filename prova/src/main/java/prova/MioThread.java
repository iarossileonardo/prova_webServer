package prova;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

            File file;

            System.out.println("RISORSA: "+resource);

            if (resource.endsWith("/")) {
                resource = resource + "index.html";
            } else if ((!resource.endsWith("/")) && (resource.split("\\.").length == 1)) {
                System.out.println("BOMBA");
                out.writeBytes("HTTP/1.1 301 Moved Permanently\n");
                out.writeBytes("Content-Length: 0\n");
                out.writeBytes("Location: "+resource+"/\n");
                out.writeBytes("\r\n");
            }
            System.out.println("NUOVARIS: "+resource);
            file = new File("htdocs/" + resource);
            
            if (file.exists()) {
                out.writeBytes("HTTP/1.1 200 OK\r\n");
                out.writeBytes("Content-Length: " + file.length() + "\r\n");
                out.writeBytes("Content-Type: " + risolviTipo(file) + "\r\n");
                out.writeBytes("\r\n");
                InputStream input = new FileInputStream(file);
                byte[] buf = new byte[8192];
                int n;
                while ((n = input.read(buf)) != -1) { //legge i dati e li mette nel buffer, quando i dati sono finiti, rende -1
                    out.write(buf, 0, n);
                }
                input.close();   
            }
            else {
                String bodyRisposta = "<html><body>Sei <b>fuori strada</b></body></html>";
                out.writeBytes("HTTP/1.1 404 Not found\n");
                out.writeBytes("Content-Lenght: " + bodyRisposta.length() + "\n");
                out.writeBytes("Content-Type: text/html\n");
                out.writeBytes("\n");
                out.writeBytes(bodyRisposta);
            }
            s0.close();
        } catch (Exception e) {
            System.out.println("ERRORE");
            e.printStackTrace();
        }
    }

    private String risolviTipo(File file){
        String ext = file.getName().split("\\.")[1];
        switch (ext) {
            case "png":
                return "image/png";
            case "html":
            case "htm":
                return "text/html";
            case "css":
                return "text/css";
            case "js":
                return "application/javascript";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "mp4":
                return "video/mp4";
            default:
                return "";
        }
    }
}
