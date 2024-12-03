package StudentInfomationTest;
import java.io.*;
import java.net.*;
import java.util.List;

public class OneConnect extends Thread{
    private Socket clientSocket;
    private boolean isAuthenticated = false;
    private String currentUser;
    private UserAuth auth;
    private StudentDatabase database;

    public OneConnect(Socket socket, UserAuth auth, StudentDatabase database) {
        this.clientSocket = socket;
        this.auth = auth;
        this.database = database;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("Welcome to Student Information System. Please log in.");

            String command;
            while ((command = in.readLine()) != null) {
                if (command.equalsIgnoreCase("QUIT")) {
                    out.println("Goodbye!");
                    break;
                } else if (command.startsWith("USER")) {
                    handleUserCommand(out, command);
                } else if (command.startsWith("PASS")) {
                    handlePassCommand(out, command);
                } else if (isAuthenticated) {
                    if (command.startsWith("FindById")) {
                        handleFindByIdCommand(out, command);
                    } else if (command.startsWith("FindByName")) {
                        handleFindByNameCommand(out, command);
                    } else {
                        out.println("Invalid command.");
                    }
                } else {
                    out.println("Please log in first.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUserCommand(PrintWriter out, String command) {
        String[] parts = command.split(" ");
        if (parts.length == 2) {
            currentUser = parts[1];
            out.println("User accepted. Please enter your password.");
        } else {
            out.println("Invalid USER command.");
        }
    }

    private void handlePassCommand(PrintWriter out, String command) {
        String[] parts = command.split(" ");
        if (parts.length == 2 && currentUser != null) {
            String password = parts[1];
            if (auth.validate(currentUser, password)) {
                isAuthenticated = true;
                out.println("Login successful.");
            } else {
                out.println("Invalid username or password.");
            }
        } else {
            out.println("Invalid PASS command.");
        }
    }

    private void handleFindByIdCommand(PrintWriter out, String command) {
        String[] parts = command.split(" ");
        if (parts.length == 2) {
            String id = parts[1];
            Student student = database.findById(id);
            if (student != null) {
                out.println(student);
            } else {
                out.println("Student not found.");
            }
        } else {
            out.println("Invalid FindById command.");
        }
    }

    private void handleFindByNameCommand(PrintWriter out, String command) {
        String[] parts = command.split(" ", 2);
        if (parts.length == 2) {
            String name = parts[1];
            List<Student> foundStudents = database.findByName(name);
            if (!foundStudents.isEmpty()) {
                for (Student student : foundStudents) {
                    out.println(student);
                }
            } else {
                out.println("Student not found.");
            }
        } else {
            out.println("Invalid FindByName command.");
        }
    }

}
