package CommandResponeTest;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Đã kết nối tới cổng 12345");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new OneConnect(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
