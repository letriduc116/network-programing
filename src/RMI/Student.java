package RMI;

import java.io.Serializable;

public class Student implements Serializable { // bat buoc phai implement Serializable
    int id;
    String name;
    double gpa;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return
                " id: " + id + "\t name: " + name  + "\t gpa: " + gpa + "\n";
    }
}
