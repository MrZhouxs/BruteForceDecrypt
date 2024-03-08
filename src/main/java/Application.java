import service.impl.Decrypt7zService;
import service.impl.DecryptRarService;
import service.impl.DecryptZipService;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("未给出待破解的文件路径");
            return;
        }
        String filepath = args[0];
        String password = null;
        long startTime = System.currentTimeMillis();
        if (filepath.endsWith("rar")) {
            password = new DecryptRarService().decrypt(filepath);
        } else if (filepath.endsWith("zip")) {
            password = new DecryptZipService().decrypt(filepath);
        } else if (filepath.endsWith("7z")) {
            password = new Decrypt7zService().decrypt(filepath);
        } else {
            System.out.println("暂不支持其它类型的压缩文件");
        }
        long endTime = System.currentTimeMillis();
        System.out.printf("总花费时间为：%d%n", endTime - startTime);
        System.out.printf("破解出的密码为：%s%n", password);
    }
}
