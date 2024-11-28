package CommandResponse;

import java.net.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class OneConnect extends Thread {
    Socket socket;
    BufferedReader netIn;
    PrintWriter netOut; // test bằng command -> text -> printWriter
    double operand1, operand2;
    char operator; // --> so nguyen

    public OneConnect(Socket socket) throws IOException {
        this.socket = socket;
        netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        netOut = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        String line;

        try {
            netOut.print("Ready...\r\n");
            while (true) {
                // revices request
                line = netIn.readLine();
                if ("Exit".equalsIgnoreCase(line)) break;

                try {
                    //Analize command
                    anylizeCommand(line);
                    // excute => result
                    double res = execComand();
                    //Response

                    netOut.println(line +" = " + res + "\r\n");
                    netOut.flush();
                } catch (MyException e) {
                    netOut.println(e.getMessage());
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double execComand() throws MyException {
        double res = 0;
        switch (operator) {
            case '+':
                res = operand1 + operand2;
                break;
            case '-':
                res = operand1 - operand2;
                break;
            case '*':
                res = operand1 * operand2;
                break;
            case '/':
                res = operand1 / operand2;
                if (Double.isFinite(res)) throw new MyException("Khong chia cho 0");
                break;
        }
        return res;
    }

    public void anylizeCommand(String line) throws MyException {
        StringTokenizer stk = new StringTokenizer(line, "+-*/");
        String str1, str2; //token
        try {
            str1 = stk.nextToken();
            str2 = stk.nextToken();
        } catch (NoSuchElementException e) {
            throw new MyException("Thieu toan hang");
        }
        this.operator = line.charAt(str1.length()); //length chuoi str1 ->  tim duoc operator
        try {
            this.operand1 = Double.parseDouble(str1.trim());
        } catch (NumberFormatException e) {
            throw new MyException("Toan hang 1 khong phai so");
        }
        try {
            this.operand2 = Double.parseDouble(str2.trim());
        } catch (NumberFormatException e) {
            throw new MyException("Toan hang 2 khong phai so");
        }
    }
}
