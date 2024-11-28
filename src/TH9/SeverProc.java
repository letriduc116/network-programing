package TH9;

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
        boolean res;
        String lastUserName = null;
        try {
            netOut.println("Ready...");
            //login
            while (!isLogin) {
                response = "";
                line = netIn.readLine();
                if ("QUIT".equalsIgnoreCase(line)) break;
                analyzeRequest(line);
                switch (com) {
                    case "USER":
                        res = dao.checkUserName(param);
                        if (res) {
                            response = "Ok user";
                            lastUserName = param;
                        } else  {
                            response = "Error user";
                            lastUserName = null;
                        }
                        break;
                    case "PASS":
                        if (lastUserName ==null) {
                            response = "Error user";
                        } else {
                            res = dao.login (lastUserName, param);
                            if (res) {
                                isLogin = true;
                                response = "Ok login";
                            } else {
                                response = "Error login";
                            }
                        }
                        break;
                        default: response = "lenh khong hop le";
                }
                netOut.println(response);
            }

            //search
            while (isLogin) {
                response= "";
                line = netIn.readLine();
                if ("QUIT".equalsIgnoreCase(line)) break;
                analyzeRequest(line);
                switch (com) {
                    case "FBID":
                        int id = Integer.parseInt(param);
                        List<Student> list = dao.findById(id);
                        response = makeResponse (list);
                        break;
                    case "FBN":

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
            return "kh tim thay";
        }
        for (Student student : list) {
            res += student.toString() + "\n";
        }
        return res;
    }

    private void analyzeRequest(String line) {
        StringTokenizer stk = new  StringTokenizer(line);
        com = stk.nextToken().toUpperCase();
        param = line.substring(com.length()).trim();

    }
}
