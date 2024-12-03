package StudentInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

public class SeverProc extends Thread {
    Socket socket;
    BufferedReader netIn;
    PrintWriter netOut; // test bằng command -> text -> printWriter
    boolean isLogin = false;
    Dao dao;
    String com, param;

    public SeverProc(Socket socket) throws IOException {
        this.socket = socket;
        netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        netOut = new PrintWriter(socket.getOutputStream(), true);
        this.dao = new Dao();
    }

    public void run() {
        String  line, response;
        String lastUserName = ""; // luu lai de kiem tra pass
        boolean res;
        try {
            netOut.println("Ready...");
            //login (chua login)
            while (!isLogin) {
                response = "";
                line = netIn.readLine();
                if ("QUIT".equalsIgnoreCase(line)) break;
                analyzeRequest(line); //tach phan tu: com (lenh/tu khoa) va param (tham so)
                switch (com) {
                    case "USER":
                        res = dao.checkUserName(param);
                        if (res) {
                            response = " Ok USER";
                            lastUserName = param;
                        } else  {
                            response = " Error USER";
                            lastUserName = null;
                        }
                        break;
                    case "PASS":
                        if (lastUserName ==null) {
                            response = "Error user must be login";
                        } else {
                            res = dao.login (lastUserName, param);
                            if (res) {
                                isLogin = true;
                                response = " Ok login";
                            } else {
                                response = " Error login";
                            }
                        }
                        break;
                    default: response = "lenh khong hop le";
                }
                netOut.println(response);
            }

            //search (da login)
            while (isLogin) {
                response= "";
                line = netIn.readLine();
                if ("QUIT".equalsIgnoreCase(line)) break;
                analyzeRequest(line); //tach phan tu: com (lenh) va param (tham so)
                switch (com) {
                    case "FBID":
                        int id = Integer.parseInt(param);
                        List<Student> list = dao.findById(id);
                        response = makeResponse (list);
                        break;
                    case "FBN": // tuong tu
                        List<Student> listByName = dao.findByName(param);
                        response = makeResponse(listByName);
                        break;
                    default: response = "lenh khong hop le";
                }
                netOut.println(response);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeResponse(List<Student> list) {
        String res = "";
        if (list.isEmpty()) {
            return " khong tim thay";
        }
        for (Student student : list) {
            res += student.toString() + "\n"; // nen dung StringBuider
        }
        return res;
    }

    private void analyzeRequest(String line) {
        StringTokenizer stk = new  StringTokenizer(line);
        com = stk.nextToken().toUpperCase();
        param = line.substring(com.length()).trim(); // cat khoang trang dau cuoi

    }
}
