package logback.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AES {
    private static String key = null;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("logback_filter");
            key = bundle.getString("logback.filter.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static byte[] encrypt(String content) {
        return encrypt(content, key);
    }


    public static byte[] decrypt(String content) {
        byte[] decryptFrom = parseHexStr2Byte(content);
        return decrypt(decryptFrom, key);
    }


    public static byte[] encrypt(String content, String encryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encryptKey.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(1, key);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] decrypt(byte[] content, String decryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(decryptKey.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static String getDesString(String src) {
        return getDesString(src, key);
    }


    public static String getDesString(String src, String decryptKey) {
        byte[] decryptFrom = parseHexStr2Byte(src);
        byte[] decryptResult = decrypt(decryptFrom, decryptKey);

        return new String(decryptResult);
    }


    public static String getEncString(String src) {
        return getEncString(src, key);
    }


    public static String getEncString(String src, String encryptKey) {
        byte[] encryptResult = encrypt(src, encryptKey);
        return parseByte2HexStr(encryptResult);
    }


    public static void main(String[] args) {
        String content = "13347|0000009670";

        System.out.println("加密前：" + content);

        String end = getEncString(content, "henry");

        System.out.println("加密后：" + end);

        System.out.println("解密后：" + getDesString(end, "henry"));
        end = getEncString(content);
        System.out.println("加密后：" + end);
        System.out.println("解密后：" + getDesString(end));

    }
}