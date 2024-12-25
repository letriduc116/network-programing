package RMI;

import RMI.Student;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

public class Client {
    ISearch dao; // remote object
    BufferedReader userIn; // doc tu ban phim
    String com, param;
    boolean isLogin = false;
    int sessionId = - 1; // luu lai session id

    public Client() throws NotBoundException, RemoteException {
        Registry reg = LocateRegistry.getRegistry("127.0.0.1", 2000); // lay registry tren port 2000 va dia chi IP
        dao = (ISearch) reg.lookup("SEARCH"); // lay remote object co ten "SEARCH" da dang ki tren registry (server)
        userIn = new BufferedReader(new InputStreamReader(System.in));
    }

    private void run() {
        String  line, response;
        String lastUserName = ""; // luu lai de kiem tra pass
        boolean res;
        try {
            System.out.println("Ready...");
            //login (chua login)
            while (!isLogin) {
                response = "";
                line = userIn.readLine();
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
                            sessionId = dao.login (lastUserName, param);
                            if (sessionId > -1) {
                                isLogin = true;
                                response = " Ok login";
                            } else {
                                response = " Error login";
                            }
                        }
                        break;
                    default: response = "lenh khong hop le";
                }
                System.out.println(response);
            }

            //search (da login)
            while (isLogin) {
                response= "";
                line = userIn.readLine();

                if ("QUIT".equalsIgnoreCase(line)) break;

                analyzeRequest(line); //tach phan tu: com (lenh) va param (tham so)

                switch (com) {
                    case "FBID":
                        int id = Integer.parseInt(param);
                        List<Student> list = dao.findById(sessionId, id);
                        response = makeResponse (list);
                        break;
                    case "FBN": // tuong tu
                        List<Student> listByName = dao.findByName( sessionId, param);
                        response = makeResponse(listByName);
                        break;
                    default: response = "lenh khong hop le";
                }
                System.out.println(response);
                dao.logout(sessionId); // dang xuat khoi session da login
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeResponse(List<Student> list) {
        String res = "";
        if (list.isEmpty()) {
            return " khong tim thay";
        }
        if (list == null) {
            return " ban chua login";
        }
        for (Student student : list) {
            res += student.toString() + "\r\n"; // nen dung StringBuider
        }
        return res;
    }

    private void analyzeRequest(String line) {
        StringTokenizer stk = new  StringTokenizer(line);
        com = stk.nextToken().toUpperCase();
        param = line.substring(com.length()).trim(); // cat khoang trang dau cuoi

    }
    public static void main(String[] args) throws RemoteException, NotBoundException {
            new Client().run();
    }



}
