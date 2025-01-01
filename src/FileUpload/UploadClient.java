package FileUpload;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class UploadClient {
    //Client thực hiện việc gửi file đến Server
    public static void main(String[] args) throws IOException {
        DataInputStream netIn ; // read --> byte, readUTF -- chuoi
        DataOutputStream netOut ;
        BufferedReader userIn  = new BufferedReader(new InputStreamReader(System.in));
        String line, com, source, dest;
        Socket socket = new Socket("127.0.0.1", 2000);
        netIn = new DataInputStream(socket.getInputStream());
        netOut = new DataOutputStream(socket.getOutputStream());

        // upload G:\\temp\\BBB\\abc.pdf G:\\temp\\BBB\\abcCopy.pdf = line
        while (true) {
            line = userIn.readLine(); // đọc 1 dòng từ bàn phím
            if ("EXIT".equalsIgnoreCase(line)) {
                netOut.writeUTF("EXIT");
                netOut.flush();
                break;
            }
            StringTokenizer stk = new StringTokenizer(line);
            com = stk.nextToken(); // upload
            source = stk.nextToken(); // G:\\temp\\BBB\\abc.pdf
            dest = stk.nextToken(); // G:\\temp\\BBB\\abcCopy.pdf
            long size = new File(source).length(); // cho biet kich thuoc cua file de ket thuc read
            netOut.writeUTF("UP " +dest); // gửi lệnh UP và dest đến server
            netOut.flush();
            netOut.writeLong(size); // gửi kích thước file đến server
            netOut.flush();
            // ghi dữ liệu từ file source đến netOut
            FileInputStream fis = new FileInputStream(source); // mở file abc.pdf

            // đọc nội dung của tệp abc.pdf và gửi từng khối dữ liệu qua socket bằng netOut
            byte[] buff = new byte[102400];
            int count;
            while ((count = fis.read(buff)) != -1) netOut.write(buff, 0, count);
            netOut.flush();
            fis.close();
            System.out.println(netIn.readUTF()); // chờ phản hồi từ server, nếu Thành công thì in ra DONE
        }
        socket.close();
    }
}
