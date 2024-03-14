import cn.hutool.core.date.DateUtil;
import service.impl.Decrypt7zService;
import service.impl.DecryptRarService;
import service.impl.DecryptZipService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Application {

    private static String datetime2String(Date datetime) {
        SimpleDateFormat tempDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return tempDateTimeFormat.format(datetime);
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        if (args.length == 0) {
            System.out.println("未给出待破解的文件路径");
            return;
        }
        String filepath = args[0];
        String password = null;
        Calendar startCalendar = Calendar.getInstance();
        System.out.printf("准备开始破解密码，%s%n", datetime2String(startCalendar.getTime()));
        if (filepath.endsWith("rar")) {
            password = new DecryptRarService().decrypt(filepath);
        } else if (filepath.endsWith("zip")) {
            password = new DecryptZipService().decrypt(filepath);
        } else if (filepath.endsWith("7z")) {
            password = new Decrypt7zService().decrypt(filepath);
        } else {
            System.out.println("暂不支持其它类型的压缩文件");
        }
        Calendar endCalendar = Calendar.getInstance();

        System.out.printf("密码破解已完成，%s%n", datetime2String(endCalendar.getTime()));
        System.out.printf("总花费时间为：%s%n", DateUtil.formatBetween(endCalendar.getTime().getTime() - startCalendar.getTime().getTime()));
        System.out.printf("破解出的密码为：%s%n", password);
    }
}
