package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public abstract class DecryptService {
    public final Path tmpFileDir = Files.createTempDirectory("unzip");

    protected DecryptService() throws IOException {
    }

    public String[] letter() {
        String number = "0123456789";
        String character = "abcdefghijklmnopqrsstuvwxyz";
//        String specialChar = "!@#$%^&*()_+~`！@#￥%……&*（）——+·{}|[]\\:\";'<>?,./，。、《》？：”；’【】、{}";
        return (number + character + character.toUpperCase()).split("");
    }

    public void deleteDir(File deletedFile) {
        if (deletedFile.exists()) {
            for (File file : Objects.requireNonNull(deletedFile.listFiles())) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    file.delete();
                }
            }
            // 删除本身的文件夹
            deletedFile.delete();
        }
    }

    public String decrypt(String filepath) throws FileNotFoundException {
        File fileHandler = new File(filepath);
        if (!fileHandler.exists()) {
            throw new FileNotFoundException("待破解文件不存在");
        }
        String[] passwords = this.letter();
        // 1位密码
        for (String letter1 : passwords) {
            if (this.check_password(fileHandler, letter1)) {
                return letter1;
            }
        }

        // 2位秘密
        for (String letter1 : passwords) {
            for (String letter2 : passwords) {
                this.check_password(fileHandler, letter1 + letter2);
                String password = letter1 + letter2;
                if (this.check_password(fileHandler, password)) {
                    return password;
                }
            }
        }

        // 3位秘密
        for (String letter1 : passwords) {
            for (String letter2 : passwords) {
                for (String letter3 : passwords) {
                    String password = letter1 + letter2 + letter3;
                    if (this.check_password(fileHandler, password)) {
                        return password;
                    }
                }
            }
        }

        // 4位秘密
        for (String letter1 : passwords) {
            for (String letter2 : passwords) {
                for (String letter3 : passwords) {
                    for (String letter4 : passwords) {
                        String password = letter1 + letter2 + letter3 + letter4;
                        if (this.check_password(fileHandler, password)) {
                            return password;
                        }
                    }
                }
            }
        }

        // 5位秘密
        for (String letter1 : passwords) {
            for (String letter2 : passwords) {
                for (String letter3 : passwords) {
                    for (String letter4 : passwords) {
                        for (String letter5 : passwords) {
                            String password = letter1 + letter2 + letter3 + letter4 + letter5;
                            if (this.check_password(fileHandler, password)) {
                                return password;
                            }
                        }
                    }
                }
            }
        }

        // 6位秘密
        for (String letter1 : passwords) {
            for (String letter2 : passwords) {
                for (String letter3 : passwords) {
                    for (String letter4 : passwords) {
                        for (String letter5 : passwords) {
                            for (String letter6 : passwords) {
                                String password = letter1 + letter2 + letter3 + letter4 + letter5 + letter6;
                                if (this.check_password(fileHandler, password)) {
                                    return password;
                                }
                            }
                        }
                    }
                }
            }
        }

        // 7位秘密
        for (String letter1 : passwords) {
            for (String letter2 : passwords) {
                for (String letter3 : passwords) {
                    for (String letter4 : passwords) {
                        for (String letter5 : passwords) {
                            for (String letter6 : passwords) {
                                for (String letter7 : passwords) {
                                    String password = letter1 + letter2 + letter3 + letter4 + letter5 + letter6 + letter7;
                                    if (this.check_password(fileHandler, password)) {
                                        return password;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 8位秘密
        for (String letter1 : passwords) {
            for (String letter2 : passwords) {
                for (String letter3 : passwords) {
                    for (String letter4 : passwords) {
                        for (String letter5 : passwords) {
                            for (String letter6 : passwords) {
                                for (String letter7 : passwords) {
                                    for (String letter8 : passwords) {
                                        String password = letter1 + letter2 + letter3 + letter4 + letter5 + letter6 + letter7 + letter8;
                                        if (this.check_password(fileHandler, password)) {
                                            return password;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        throw new RuntimeException("待破解的密码大于8位，暂不支持");
    }

    public abstract boolean check_password(File fileHandler, String password);

}
