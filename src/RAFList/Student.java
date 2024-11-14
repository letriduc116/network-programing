package RAFList;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
    int id; // 4 byte
    String name; // 2 byte
    int bYear; // 4 byte
    double grade; // 8 byte

    public Student(int id, String name, int bYear, double grade) {
        this.id = id;
        this.name = name;
        this.bYear = bYear;
        this.grade = grade;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + bYear + "\t" + grade + "\n";
    }

    public void save(RandomAccessFile raf, int nLen) throws IOException {
        raf.writeInt(id);
        char c;
        for (int i = 0; i < nLen; i++) {
            if (i < name.length()) c = name.charAt(i);
            else c = 0;
            raf.writeChar(c);
        }
        raf.writeInt(bYear);
        raf.writeDouble(grade);

    }

    public void load(RandomAccessFile raf, int nLen) throws IOException {
        id = raf.readInt();
        name = "";
        char c;
        for (int i = 0; i < nLen; i++) {
            c = raf.readChar();
            if (c != 0) name += c;
        }
        bYear = raf.readInt();
        grade = raf.readDouble();
    }
}
