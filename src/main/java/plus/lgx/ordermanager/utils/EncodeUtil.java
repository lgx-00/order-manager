package plus.lgx.ordermanager.utils;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Slf4j
public class EncodeUtil {

    public static final String RANDOM_SALT_BASE_STRING_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
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
        char alpha3 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_ALPHA);
        char number1 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_NUMBER);
        char number2 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_NUMBER);
        char number3 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_NUMBER);
        char comma1 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_COMMA);
        char comma2 = RandomUtil.randomChar(RANDOM_SALT_BASE_STRING_COMMA);
        char[] chars = new char[]{alpha1, alpha2, number1, number2, comma1, comma2, alpha3, number3};
        for (int i = 0, ind; i < 8; i++) {
            ind = RandomUtil.randomInt(8);
            char ch = chars[i];
            chars[i] = chars[ind];
            chars[ind] = ch;
        }
        return new String(chars);
    }

    public static boolean isEqualed(String encoded, String origin, String salt) {
        return Objects.equals(encoded, EncodeUtil.md5(origin + salt));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            log.info(genSalt());
        }
    }
}