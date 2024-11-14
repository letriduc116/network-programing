package RAFList;

import java.io.*;

public class RAFListStudent {
    static final int NAME_LENGTH = 25;
    int count;
    int recSize; // header size
    int nLength;
    RandomAccessFile raf;

    public RAFListStudent(String filename) throws IOException {
        raf = new RandomAccessFile(filename, "rw");
        raf.seek(0); // đọc từ đầu file
        if (raf.length() > 0) { // mở để ghi tiếp
            count = raf.readInt();
            recSize = raf.readInt();
            nLength = (recSize - 4 - 4 - 8) / 2;
        } else { // tạo mới
            count = 0;
            nLength = NAME_LENGTH;
            recSize = 4 + 4 + 8 + 2 * nLength;
            raf.writeInt(count);
            raf.writeInt(recSize);
        }
    }

    public Student getStudent(int index) throws IOException {
        if ((index < 0) || (index > count - 1)) return null;
        long pos = (4 + 4 + index * recSize); // count , recSize
        raf.seek(pos);
        Student st = new Student();
        st.load(raf,nLength);
        return st;
    }

    public void addStudent(Student st) throws IOException {
        raf.seek(raf.length()); // dua con tro xuong cuoi
        st.save(raf,nLength);
        count++;
        raf.seek(0); // dua con tro len dau
        raf.writeInt(count);
    }

    public void updateStudent(int index,Student newStudent) throws IOException {
        if ((index < 0) || (index > count - 1)) return;
        long pos = (4 + 4 + index * recSize);
        raf.seek(pos);
        newStudent.save(raf,nLength);
    }

    public void close() throws IOException {
        raf.close();
    }

    public static void main(String[] args) throws IOException {
        String file = "G:\\temp\\AAA\\st.dat";
        RAFListStudent list = new RAFListStudent(file);
//        list.addStudent(new Student(1,"Lê Trí Đức", 2004, 9.5));
//        list.addStudent(new Student(2,"Võ Xuân Đông", 2004, 9.8));
        Student st = list.getStudent(0);
        System.out.println(st);
        list.close();
    }
}
