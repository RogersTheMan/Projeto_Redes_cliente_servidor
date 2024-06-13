// Rodrigo Pretel Rosa - 739761
// Antonio Armando Fascio IV - 739885
// Yasmin Tomé - 741501

package p2_redes;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

// Classe Servidor
public class Servidor {
    private static final int PORTA = 12345;
    private static final int N_THREADS = 10;

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(N_THREADS);
        ServerSocket serverSocket = new ServerSocket(PORTA);
        
        System.out.println("Servidor iniciado na porta " + PORTA);

        try {
            while (true) {
                pool.execute(new Handler(serverSocket.accept()));
            }
        } finally {
            pool.shutdown();
            serverSocket.close();
        }
    }

    // Classe interna para tratar cada solicitação de cliente
    private static class Handler implements Runnable {
        private final Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                // Enviar informações do processador
                OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
                out.println("Processador: " + osBean.getArch());
                
                // Enviar informações do SO
                out.println("Sistema Operacional: " + System.getProperty("os.name"));
                
                // Enviar informações do IP
                out.println("Endereço IP: " + InetAddress.getLocalHost().getHostAddress());
                
                // Enviar informações de espaço de armazenamento
                File[] roots = File.listRoots();
                for (File root : roots) {
                    out.println("Unidade de disco: " + root.getAbsolutePath());
                    out.println("Espaço total: " + root.getTotalSpace());
                    out.println("Espaço livre: " + root.getFreeSpace());
                }
                
                socket.close();
            } catch (IOException e) {
                System.out.println("Erro ao enviar informações para o cliente: " + e.getMessage());
            }
        }
    }
}
