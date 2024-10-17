package FileIO;

import java.io.File;

public class DeleteFile {
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) return true;

        File[] listFile = file.listFiles();
        if (listFile != null)
            for (File f : listFile) { //xoá bên trong thư mục
                deleteFile(f.getAbsolutePath());
            }
        return file.delete(); //xoá thư mục đã xoá bên trong
    }


    public static void main(String[] args) {
//        String path = "G:\\temp";
//        System.out.println(deleteFile(path));
    }

}
