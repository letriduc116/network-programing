package ImportAndExport;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ImportData {
    public  static List<Student> importData(String stFile, String gradeFile, String charSet) throws IOException {
        List<Student> list = new ArrayList<Student>();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(stFile), charSet));
        //load stFile
        br.read(); // đọc trước (bỏ qua) 1 byte của bảng mã UTF-16BE
        while ((line = br.readLine()) != null) {
            StringTokenizer stk = new StringTokenizer(line, "\t");
            int id = Integer.parseInt(stk.nextToken());
            String name = stk.nextToken();
            list.add(new Student(id, name));
        }
        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream(gradeFile), charSet));
        //load gradeFile
        br.read();// đọc trước (bỏ qua) 1 byte của bảng mã UTF-16BE
        while ((line = br.readLine()) != null) {
            StringTokenizer stk = new StringTokenizer(line, "\t");
            int id = Integer.parseInt(stk.nextToken());
            int count =0;
            double total = 0;
            while (stk.hasMoreTokens()) {
                count ++;
                total += Double.parseDouble(stk.nextToken());
            }
            double grade = total/count;
            //update grade
            for (Student st : list) {
                if (st.id == id) { // found grade
                    st.grade = grade; //update
                    break;
                }
            }

        }

        br.close();
        return list;
    }

    public static void main(String[] args) throws IOException {
        String stFile = "G:\\temp\\DUC\\sv.txt";
        String gradeFile = "G:\\temp\\DUC\\diem.txt";
        String charSet = "UTF-16BE";

        List<Student> list =  importData(stFile,gradeFile,charSet);
        for (Student st : list) {
            System.out.println(st);
        }

    }
}
