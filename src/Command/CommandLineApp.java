package Command;

import java.io.*;
import java.util.StringTokenizer;

public class CommandLineApp {
    File defaultDir;
    boolean quit = false;
    BufferedReader userIn;

    public CommandLineApp(String initDir) {
        this.defaultDir = new File(initDir);
        this.userIn = new BufferedReader(new InputStreamReader(System.in)); // String -> Binary
    }

    public void start() {
        String line, res;
        System.out.println(getPromt());

        while (!quit) {
            line = getUserCommand();
            res = excuteCommand(line);

            System.out.println(getPromt());
            if (!res.isBlank()) {
                System.out.println(res);
            }
        }
    }

    public String excuteCommand(String line) {
        if (line.isBlank()) return "";
        String res = "";
        StringTokenizer st = new StringTokenizer(line); // -> same split
        String com, param = "";
        com = st.nextToken().toUpperCase();
        if (st.hasMoreTokens()) param = st.nextToken();
        switch (com) {
            case "EXIT":
                quit = true;
                break;
            case "CD":
                res = changeDir(param);
                break;
            case "DIR":
                res = listDir();
                break;
            case "DETETE":
//                res = deleteFile();
                break;
        }
        return res;
    }

    public String listDir() {
//        StringBuilder sb = new StringBuilder();
        File[] files = defaultDir.listFiles();

        if (files == null || files.length == 0) {
            return "Thư mục rỗng hoặc không thể truy cập.";
        }
        String s = "";
        for (File file : files) {
            if (file.isDirectory()) {
//                sb.append(file.getName().toUpperCase()).append("\n");

                s += file.getName().toUpperCase() + "\n";
            }
        }

        for (File file : files) {
            if (file.isFile()) {
//                sb.append(file.getName().toLowerCase()).append("\n");
                s += file.getName().toLowerCase() + "\n";
            }
        }

        return s;
    }

    public String changeDir(String param) {
        String res = "";
        if (param.isBlank()) return defaultDir.getAbsolutePath(); // param can null ->
        if ("..".equalsIgnoreCase(param)) { //return paren file
            File parent = defaultDir.getParentFile();
            if (parent != null) defaultDir = parent;
        } else { // try change to child
            File temp = new File(defaultDir, param);
            if (temp.exists()) defaultDir = temp;
            else res = "Folder not exist";
        }
        return res;
    }

    public String getUserCommand() {
        try {
            return userIn.readLine();
        } catch (IOException e) {
            return "";
        }
    }


    public String getPromt() {
        return defaultDir.getAbsolutePath() + ">";
    }

    public static void main(String[] args) {
        String initDir = "G:\\temp";
        new CommandLineApp(initDir).start();
    }
}
