package service.impl;

import net.lingala.zip4j.ZipFile;
import service.DecryptService;

import java.io.File;
import java.io.IOException;

public class DecryptZipService extends DecryptService {

    public DecryptZipService() throws IOException {
    }

    @Override
    public boolean check_password(File fileHandler, String password) {

        try {
            ZipFile zipFile = new ZipFile(fileHandler, password.toCharArray());

            zipFile.extractAll(this.tmpFileDir.toFile().getCanonicalPath());
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
