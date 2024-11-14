package ImportAndExport;

public class Student {
    int id;
    String name;
    int bYear;
    double grade;

    public Student(int id, String name, int bYear, double grade) {
        this.id = id;
        this.name = name;
        this.bYear = bYear;
        this.grade = grade;
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + bYear + "\t" + grade + "\n";
    }
}
