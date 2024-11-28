package StudentInfomation;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
    private List<Student> students;

    public StudentDatabase() {
        students = new ArrayList<>();
        students.add(new Student("SV001", "Nguyen Van A", 7.5));
        students.add(new Student("SV002", "Tran Thi B", 8.0));
        students.add(new Student("SV003", "Pham Thi C", 9.0));
        students.add(new Student("SV004", "Nguyen Thi Hoa Hong", 6.5));
    }

    public Student findById(String id) {
        return students.stream()
                .filter(student -> student.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Student> findByName(String name) {
        List<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                foundStudents.add(student);
            }
        }
        return foundStudents;
    }
}
