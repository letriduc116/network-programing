package packAndUnpack;
import  java.io.*;
import java.util.ArrayList;
import java.util.List;

public class packUnpack {
    // Loc file 1 cap
    public static List<File> getFiles (String folder) {
        List<File> files = new ArrayList<File>();
        File dir = new File(folder);
        for (File f: dir.listFiles()) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    public static void pack (String folder, String packegefile) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(packegefile , "rw");
        List<File> files = getFiles(folder);
        raf.writeInt(files.size()); //so luong file co trong folder
        long pos = 0; // mac dinh vi tri ban dau la dung
        long[] headPos = new long[files.size()] ; // luu vi tri cac pos
        int index = 0; // vi tri cua pos1
        byte[] buff = new byte[102400];
        int byteRead;

        for (File f: files) { //header
            headPos[index++] = raf.getFilePointer();
            raf.writeLong(pos);
            raf.writeLong(f.length());
            raf.writeUTF(f.getName());
        }
        index = 0;
        for (File f: files) { //data
            pos = raf.getFilePointer(); // lay vi tri hien tai
            raf.seek(headPos[index++]); // di chuyen den vi tri header
            raf.writeLong(pos);// ghi lai vi tri data
            raf.seek(pos);
            FileInputStream fis = new FileInputStream(f);
            while ((byteRead = fis.read(buff)) != -1) {
                raf.write(buff, 0, byteRead);
            }
            fis.close();
        }
        raf.close();
    }

    public static boolean dataCopy(RandomAccessFile fis, FileOutputStream fos, long pSize) throws IOException {
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

    public static void unPack (String packedFile, String extractFile, String destFile) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(packedFile , "rw");
        int fileNum = raf.readInt();
        long pos, len;
        String name;
        for (int i = 0; i < fileNum; i++) {
            pos = raf.readLong();
            len = raf.readLong();
            name = raf.readUTF();
            if (name.equalsIgnoreCase(extractFile)) { //extract
                raf.seek(pos);
                FileOutputStream fos = new FileOutputStream(destFile);
                dataCopy(raf,fos,len);
                fos.close();
                break;
            }
        }
        raf.close();
    }


    public static void main(String[] args) throws IOException {
        String folder = "G:\\temp\\pack";
        String packegefile = "G:\\temp\\abc_pack";
        pack(folder,packegefile);
//        String extractName = "text.txt";
//        String destFile = "G:\\temp\\text.txt";
//        unPack(packegefile, extractName, destFile);
    }
}
