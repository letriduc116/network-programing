package FileUpload;

import java.io.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.StringTokenizer;

public class OneConnect extends Thread {
    // Sever phải đọc lệnh từ client, sau đó thực hiện lệnh đó
    // Luồng trên Server để xử lý lệnh upload file từ Client.
    Socket socket;
    DataInputStream netIn;
    DataOutputStream netOut;

    public OneConnect(Socket socket) throws IOException {
        this.socket = socket;
        netIn = new DataInputStream(socket.getInputStream());
        netOut = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {
        String line;
        String com, dest;
        try {
            while (true) {
                line = netIn.readUTF(); // lúc này:  line = "UP G:\\temp\\BBB\\abcCopy.pdf"
                if ("EXIT".equalsIgnoreCase(line)) break;
                StringTokenizer stk = new StringTokenizer(line);
                com = stk.nextToken(); // com = "UP"
                dest = stk.nextToken(); // dest = "G:\\temp\\BBB\\abcCopy.pdf"
                long size = netIn.readLong();// Server nhận kích thước tệp size mà client đã gửi trước đó.
                FileOutputStream fos = new FileOutputStream(dest); // mở file abcCopy.pdf
                dataCopy(netIn, fos, size);
                fos.close();
                netOut.writeUTF("DONE");
                netOut.flush();
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean dataCopy(InputStream fis, FileOutputStream fos, long pSize) throws IOException {
        byte[] buffer = new byte[102400];
        long remain = pSize; //so luong byte can doc
        int byteMustRead;
        while (remain > 0) {
            byteMustRead = (int) (remain < buffer.length ? remain : buffer.length);
            int byteRead = fis.read(buffer, 0, byteMustRead);
            if (byteRead == -1) return false;
            fos.write(buffer, 0, byteRead);
            remain -= byteRead;
        }
        return true;
    }

}
