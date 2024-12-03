package StudentInfomationTest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        UserAuth auth = new UserAuth();
        StudentDatabase database = new StudentDatabase();
        try (ServerSocket serverSocket = new ServerSocket(2000)) {
            System.out.println("Server da ket noi toi cong:  " + 2000 + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new OneConnect(clientSocket, auth, database).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
