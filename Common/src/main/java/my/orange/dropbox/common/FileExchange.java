package my.orange.dropbox.common;

import java.io.*;

public class FileExchange {

    public static void download(ObjectInputStream objectInput, File file) {
        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(file);
            int count;
            byte[] buffer = new byte[1024];
            while ((count = objectInput.read(buffer)) == 1024) {
                fileOutput.write(buffer, 0, count);
            }
            fileOutput.write(buffer, 0, count);
            fileOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutput != null) {
                    fileOutput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void upload(ObjectOutputStream objectOutput, File file) {
        FileInputStream fileInput = null;
        int i =0;
        try {
            fileInput = new FileInputStream(file);
            int count;
            byte[] buffer = new byte[1024];
            while ((count = fileInput.read(buffer)) == 1024) {
                objectOutput.write(buffer, 0, count);
            }
            objectOutput.write(buffer, 0, count);
            objectOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
