package TH9;

import StudentInfomation.OneConnect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StInfoServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2000)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new SeverProc(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


