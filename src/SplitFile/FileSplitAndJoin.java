package SplitFile;

import java.io.*;

public class FileSplitAndJoin {
    public static void split(String source, int pSize) throws IOException {
        FileInputStream fis = new FileInputStream(source);
        int fileNumber = 1;

        //loop
        while (true){
            String dest = source + createExt(fileNumber);
            FileOutputStream fos = new FileOutputStream(dest);
            boolean hasMoreData = dataCopy(fis, fos, pSize);

            fos.close();
            fileNumber++;
            if (!hasMoreData) break;
        }

        fis.close();
    }

    public static boolean dataCopy(FileInputStream fis, FileOutputStream fos, int pSize) throws IOException {
        byte[] buffer = new byte[102400];
        long remain = pSize; //so luong byte can doc

        while (remain > 0){
            int byteMustRead = remain > buffer.length ? buffer.length: (int) remain;
            int byteRead =  fis.read(buffer);
            if (byteRead == -1) return false;
            fos.write(buffer,0,byteRead);
            remain  -= byteRead;
        }
        return true;
    }

    // 1->001
    public static String createExt(int fileNumber) {
        if (fileNumber < 10) return ".00" + fileNumber;
        else if (fileNumber < 100) return  ".0" +fileNumber;
        else  return "." +fileNumber;
    }

    public static void join(String partFilename) throws IOException {
        String dest = partFilename.substring(0, partFilename.lastIndexOf("."));
        FileOutputStream fos = new FileOutputStream(dest);
        int fileNumber = 1;
        while (true) {
            String source = dest + createExt(fileNumber);
            File file = new File(source);
            if (!file.exists()) break;
            FileInputStream fis = new FileInputStream(source);
            dataCopy(fis,fos, (int) file.length());
            fis.close();
            fileNumber++;
        }
        fos.close();
    }

    public static void main(String[] args) throws IOException {
        String path = "G:\\temp\\text.txt";
        int size = 102400;
        String partFileName = path + ".001";
        split(path, size);
//        join(partFileName);

    }
}
