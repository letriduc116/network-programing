package FileUpload;
import java.io.IOException;
import java.net.*;

public class UploadServer {
    /* chạy Server trước, chạy client sau
    Test : upload G:\\temp\\BBB\\abc.pdf G:\\temp\\BBB\\abcCopy.pdf
    thoát  (client) : Exit
    Server duy trì liên tục nên không thoát được (đa luồng)
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        while (true) {
            Socket socket = serverSocket.accept();
            new OneConnect(socket).start();
        }

    }
}
