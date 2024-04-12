package com.sales.BPS.msystem.service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionService {
    private static final String NEW_SECRET_KEY = "new_secret_key_here"; // 새로운 키값, 안전하게 관리해야 함

    // 데이터를 암호화하는 메서드
    public static String encrypt(String data) throws Exception {
        return encryptWithKey(data, NEW_SECRET_KEY);  // 새로운 키를 사용한 암호화
    }

    // 새로운 키로 암호화하는 메서드
    public static String encryptWithNewKey(String data, String keyString) throws Exception {
        return encryptWithKey(data, keyString);  // 주어진 키를 사용한 암호화
    }

    // 암호화 처리를 수행하는 범용 메서드
    public static String encryptWithKey(String data, String keyString) throws Exception {
        byte[] key = MessageDigest.getInstance("SHA-256").digest(NEW_SECRET_KEY.getBytes("UTF-8"));
        key = Arrays.copyOf(key, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encrypted);
    }


//    // 키 생성을 위한 메서드
//    private static byte[] generateKey(String keyString) throws Exception {
//        byte[] key = keyString.getBytes("UTF-8");
//        MessageDigest sha = MessageDigest.getInstance("SHA-256");
//        key = sha.digest(key);
//        key = Arrays.copyOf(key, 16); // AES-128 키 길이 (16 바이트)에 맞추기
//        return key;
//    }
}
