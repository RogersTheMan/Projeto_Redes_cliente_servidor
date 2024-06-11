package p2_redes;

import java.io.*;
import java.net.*;

public class Cliente {
    private static final String HOST = "127.0.0.1";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORTA);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String informacaoDoServidor;
            while ((informacaoDoServidor = in.readLine()) != null) {
                System.out.println(informacaoDoServidor);
            }

            in.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao conectar com o servidor: " + e.getMessage());
        }
    }
}