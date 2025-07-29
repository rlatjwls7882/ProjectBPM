package kr.kro.projectbpm.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256 기반 대칭키 암호화 유틸리티입니다.
 * CBC 모드와 랜덤 IV를 사용하며, 비밀번호는 SHA-256으로 해싱하여 키를 생성합니다.
 */
public final class Aes256Utils {
    private Aes256Utils() {} // 유틸 클래스 인스턴스화 방지
    /**
     * 비밀번호를 SHA-256으로 해싱하여 AES 키를 생성합니다.
     *
     * @param password AES 키로 사용할 비밀번호
     * @return AES-256용 SecretKeySpec
     */
    private static SecretKeySpec sha256Key(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * AES-256 CBC 모드로 문자열을 암호화합니다.
     *
     * @param plainText 암호화할 평문
     * @param password  암호화 키를 만들 비밀번호
     * @return Base64로 인코딩된 IV + 암호문
     */
    public static String encrypt(String plainText, String password) throws Exception {
        SecretKeySpec key = sha256Key(password);
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] ivPlusCipher = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, ivPlusCipher, 0, iv.length);
        System.arraycopy(encrypted, 0, ivPlusCipher, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(ivPlusCipher);
    }

    /**
     * AES-256 CBC 모드로 암호문을 복호화합니다.
     *
     * @param encryptedText Base64 인코딩된 IV + 암호문
     * @param password 암호화에 사용한 동일한 비밀번호
     * @return 복호화된 평문
     */
    public static String decrypt(String encryptedText, String password) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        byte[] iv = new byte[16];
        byte[] cipherText = new byte[combined.length - 16];

        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, cipherText, 0, cipherText.length);

        SecretKeySpec key = sha256Key(password);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        byte[] decrypted = cipher.doFinal(cipherText);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}