package service.impl;

import fr.opensagres.xdocreport.core.io.IOUtils;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import service.DecryptService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DecryptRarService extends DecryptService {

    public DecryptRarService() throws IOException {
    }

    @Override
    public boolean check_password(File fileHandler, String password) {
        RandomAccessFile randomAccessFile;
        RandomAccessFileInStream randomAccessFileInStream;
        IInArchive rarFile;
        try {
            randomAccessFile = new RandomAccessFile(fileHandler, "r");
            randomAccessFileInStream = new RandomAccessFileInStream(randomAccessFile);
            rarFile = SevenZip.openInArchive(null, randomAccessFileInStream, password);
            ISimpleInArchive simpleInterface = rarFile.getSimpleInterface();
            for (ISimpleInArchiveItem item : simpleInterface.getArchiveItems()) {
                File outFile = new File(this.tmpFileDir.toFile(), item.getPath());
                ExtractOperationResult result = item.extractSlow(data -> {
                    FileOutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(outFile, true);
                        IOUtils.write(data, outputStream);
                    } catch (IOException e) {
                        return 0;
                    } finally {
                        // 不关闭流，文件不能删除
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return data.length;
                }, password);
                // 代表密码不对或其他错误
                if (result == ExtractOperationResult.WRONG_PASSWORD) {
                    return false;
                }
                break;
            }
            try {
                rarFile.close();
                randomAccessFileInStream.close();
                randomAccessFile.close();
            } catch (Exception e) {
                e.printStackTrace();
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
