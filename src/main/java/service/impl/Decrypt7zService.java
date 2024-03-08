package service.impl;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import service.DecryptService;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;

public class Decrypt7zService extends DecryptService {

    public Decrypt7zService() throws IOException {
    }

    @Override
    public boolean check_password(File fileHandler, String password) {
        try {
            SevenZFile zipFile = new SevenZFile(fileHandler, password.toCharArray());
            SevenZArchiveEntry entry = zipFile.getNextEntry();
            if (entry == null) {
                throw new RuntimeException("待破解压缩包不存在文件");
            }
            if (!entry.isDirectory()) {
                File file = new File(this.tmpFileDir.toFile(), entry.getName());
                if (!file.exists()) {
                    // 创建当前文件的上级目录
                    new File(file.getParent()).mkdirs();
                    OutputStream outputStream = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = zipFile.read(buf)) != -1) {
                        bos.write(buf, 0, len);
                    }
                    bos.close();
                    outputStream.close();
                    zipFile.close();
                }
            }

            deleteDir(this.tmpFileDir.toFile());
            return true;
        } catch (Exception e) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        return false;
    }
}
