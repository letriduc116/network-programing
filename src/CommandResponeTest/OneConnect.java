package CommandResponeTest;
import java.net.*;
import java.io.*;

public class OneConnect extends Thread {
    Socket socket;
    BufferedReader netIn;
    PrintWriter netOut;

    public OneConnect(Socket socket) throws IOException {
        this.socket = socket;
        netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        netOut = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        String line;
        netOut.println("Ready...");
        try {
            while ((line = netIn.readLine()) != null) {
                if (line.equalsIgnoreCase("EXIT")) {
                    break;
                }
                try {
                    String[] parts = line.split(" ");
                    if (parts.length != 3) {
                        netOut.println("Loi khong hop le toan tu 1 va 2");
                        continue;
                    }
                    double operand1 = Double.parseDouble(parts[0]);
                    double operand2 = Double.parseDouble(parts[2]);
                    String operator = parts[1];
                    double result = 0;
                    switch (operator) {
                        case "+":
                            result = operand1 + operand2;
                            break;
                        case "-":
                            result = operand1 - operand2;
                            break;
                        case "*":
                            result = operand1 * operand2;
                            break;
                        case "/":
                            if (operand2 == 0) {
                                netOut.println("Lỗi: không thể chia cho 0");
                                continue;
                            }
                            result = operand1 / operand2;
                            break;
                        default:
                            netOut.println("Loi: toan tu khong hop le");
                            continue;
                    }
                    netOut.println(" = " + result);
                } catch (NumberFormatException e) {
                    netOut.println("Vui long nhap dung dinh dang so");
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
