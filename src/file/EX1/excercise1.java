package file.EX1;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class excercise1 {
    public static void main(String[] args) throws IOException {
        File p = new File("./src/file");
        File p2 = new File("./src/file/EX2/excersice2.java");

//          Xác định file/folder có tồn tại hay không?
//        System.out.println(p.exists());
//        System.out.println(p2.exists());
//          Xác định các thuộc tính hệ thống (quyền đọc, ghi, thực thi..)của file/folder

//        System.out.println("W: "+ p.canWrite() + ", E: " + p.canExecute() + ", R: " + p.canRead());
//        System.out.println("W: "+ p2.canWrite() + ", E: " + p2.canExecute() + ", R: " + p2.canRead());

//          Tạo/xóa file/folder bất kỳ
//        File p3 = new File("./src/file/EX2");
//        p3.mkdir();
//        p2.createNewFile();
//        p2.delete();

//        p.mkdir();
//        p.delete();

//        Tìm kiếm file/folder trong 1 thư mục bất kỳ theo tên hay phần mở rộng
//        List<File> listFile = Arrays.stream(p.listFiles()).filter(item -> item.getName().contains(".java")).toList();
//        listFile.forEach(item -> System.out.println(item.getName()));

            //Hiển thị cấu trúc cây của thư mục
//        System.out.println(p.getAbsolutePath());
        //Hiển thị 1 loại file cho trước (filtering)
//        Arrays.stream(Objects.requireNonNull(p.listFiles())).filter(item -> item.getName().contains(".txt")).toList().forEach(System.out::println);


    }
}
