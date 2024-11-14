package FileUpload;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class UploadClient {
    public static void main(String[] args) throws IOException {
        DataInputStream netIn ; // read --> byte, readUTF -- chuoi
        DataOutputStream netOut ;
        BufferedReader userIn  = new BufferedReader(new InputStreamReader(System.in));
        String line, com, source, dest;
        Socket socket = new Socket("127.0.0.1", 2000);
        netIn = new DataInputStream(socket.getInputStream());
        netOut = new DataOutputStream(socket.getOutputStream());

        while (true) {
            line = userIn.readLine();
            if ("EXIT".equalsIgnoreCase(line)) {
                netOut.writeUTF("EXIT");
                netOut.flush();
                break;
            }
            StringTokenizer stk = new StringTokenizer(line);
            com = stk.nextToken();
            source = stk.nextToken();
            dest = stk.nextToken();
            long size = new File(source).length(); // cho biet kich thuoc cua file de ket thuc read
            netOut.writeUTF("UP " +dest);
            netOut.flush();
            netOut.writeLong(size);
            netOut.flush();
            FileInputStream fis = new FileInputStream(source);
            byte[] buff = new byte[102400];
            int count;
            while ((count = fis.read(buff)) != -1) netOut.write(buff, 0, count);
            netOut.flush();
            fis.close();
            System.out.println(netIn.readUTF());
        }
        socket.close();
    }
}
