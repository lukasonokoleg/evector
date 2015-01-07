package eu.itreegroup.spark.application.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;

public class SecureKeyUtils {

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final int INT_256 = 256;
    private static final int INT_128 = 128;

    /**
     * @return generates random salt of desired length
     */
    public static String generateRandomSalt(int length) {
        if (length < 1 || length % 2 != 0) {
            throw new IllegalArgumentException("Illegal salt length passed to createRandomSalt (positive even number expected): " + length);
        }
        byte[] salt = new byte[length / 2];
        secureRandom.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    public static String generateRandomSalt256() {
        return generateRandomSalt(INT_256);
    }

    public static String generateRanfomSalt128() {
        return generateRandomSalt(INT_128);
    }

    /**
     * Generate SHA-256 digest from password and salt.
     *
     * @param password 
     * @param salt 
     * @return 32 bytes length digest
     */
    public static byte[] toSHA256Digest(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            md.update(salt.getBytes("UTF-8"));
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate hex encoded SHA-256 digest from password and salt.
     *
     * @param password 
     * @param salt 
     * @return a hex encoded digest (64 chars length String)
     */
    public static String toSHA256DigestHexString(String password, String salt) {
        return Hex.encodeHexString(toSHA256Digest(password, salt));
    }

    /**
     * Generates session key using random UUID generator.
     * @return
     */
    public static String generateSessionKey() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Generates universal unique identifier;
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
