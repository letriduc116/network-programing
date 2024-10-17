package FileIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {
    public static void fileCopy(String source, String dest) throws IOException {
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] data = new byte[102400];
        int byteread;
        long bt = System.currentTimeMillis();
        while ((byteread = fis.read(data)) != -1) fos.write(data, 0, byteread);
        long et = System.currentTimeMillis();
        fis.close();
        fos.close();
        System.out.println("Done in: " + (et - bt) + " ms");
    }

    public static void main(String[] args) throws IOException {
        String source = "G:\\temp\\TA.pdf";
        String dest = "G:\\temp\\TA_Copy.pdf";
        fileCopy(source, dest);
    }

}
