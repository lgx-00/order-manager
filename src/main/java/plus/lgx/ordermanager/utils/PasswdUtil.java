package plus.lgx.ordermanager.utils;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class PasswdUtil {

    public static final String      RANDOM_SALT_BASE_STRING_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";
    public static final String RANDOM_SALT_BASE_STRING_NUMBER = "0123456789";
    public static final String RANDOM_SALT_BASE_STRING_COMMA = " !@#$%^&*()-=_+[]{}\\|;:'\",.<>/?`~";

    public static String md5(String str) {
        if (str == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte b : byteDigest) {
                i = b;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // 32位加密(小写)
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("加密失败", e);
            System.exit(1);
            return null;
        }

    }

    public static String genSalt() {
        char alpha1 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_ALPHA);
        char alpha2 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_ALPHA);
        char number1 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_NUMBER);
        char number2 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_NUMBER);
        char comma1 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_COMMA);
        char comma2 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_COMMA);
        char[] chars = new char[]{alpha1, alpha2, number1, number2, comma1, comma2};
        for (int i = 0, ind; i < 6; i++) {
            ind = RandomUtil.randomInt(6);
            char ch = chars[i];
            chars[i] = chars[ind];
            chars[ind] = ch;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            log.info(genSalt());
        }
    }
}