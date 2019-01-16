package com.sboot.study.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;

/**
 * @author faraway
 * @date 2019/1/16 14:46
 */
public class SecurityUtil {

    /**密钥32位*/
    public static final String KEY = "abcdefghijklmnopqrstuvwxyz645910";

    /**密钥算法*/
    public static final String KEY_ALGORITHM = "DESede";

    /**加密/解密算法*/
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";


    /**
     * 字符串加密
     */
    public static String encodeString(String decodeString) {
        String str = "";
        try {

            if (StringUtils.isNotEmpty(decodeString)) {
                str = encryptString(decodeString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String encryptString(String data) throws Exception {
        // 还原密钥
        Key key = toKey(Base64.decodeBase64(KEY));
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 执行操作
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }

    private static Key toKey(byte[] key) throws Exception {
        // 实例化DES密钥材料
        DESedeKeySpec dks = new DESedeKeySpec(key);
        // 实例化秘密密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 生成秘密密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 字符串解密
     *
     * @param encodeString
     * @return
     */
    public static String decodeString(String encodeString) {
        String str = "";
        try {
            if (StringUtils.isNotEmpty(encodeString)) {
                str = decryptString(encodeString);
            }
        } catch (Exception e) {
            str = "--";
        }
        return str;
    }

    public static String decryptString(String data) throws Exception {
        // 还原密钥
        Key key = toKey(Base64.decodeBase64(KEY));
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 执行操作
        return new String(cipher.doFinal(Base64.decodeBase64(data)));
    }

    public static void main(String[] args) {
        try {
            String str1 = "420624199408152917";
            System.out.println("明文：" + str1);

            String str2 = encodeString(str1);
            System.out.println("加密后：" + str2);

            String str3 = decodeString(str2);
            System.out.println("解密后：" + str3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
