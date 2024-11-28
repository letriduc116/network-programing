package CommandResponse;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    //    test : telnet 127.0.0.1 12345
    //  C2:      telnet localhost 12345
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new OneConnect(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
