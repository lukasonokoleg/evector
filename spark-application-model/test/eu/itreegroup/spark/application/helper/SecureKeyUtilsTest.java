package eu.itreegroup.spark.application.helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SecureKeyUtilsTest {

    @Test
    public void testGenerateRandomSalt() {
        {
            String salt = SecureKeyUtils.generateRandomSalt(16);
            System.out.println("salt: " + salt);
            Assert.assertEquals(16, salt.length());
        }
        {
            String salt = SecureKeyUtils.generateRandomSalt(32);
            System.out.println("salt: " + salt);
            Assert.assertEquals(32, salt.length());
        }
        {
            String salt = SecureKeyUtils.generateRandomSalt(128);
            System.out.println("salt: " + salt);
            Assert.assertEquals(128, salt.length());
        }
    }

    @Test
    public void testGenerateDigestAndSalt() {
        String password = "test";
        String salt = SecureKeyUtils.generateRandomSalt256();
        String digest = SecureKeyUtils.toSHA256DigestHexString(password, salt);
        System.out.println("admin: " + password);
        System.out.println("salt: " + salt);
        System.out.println("digest: " + digest);
    }

    @Test
    public void testToSHA256DigestHexString() {
        String password = "Password";
        String salt = "salt";
        String digest = SecureKeyUtils.toSHA256DigestHexString(password, salt);
        System.out.println("hash: " + digest);
        Assert.assertEquals("c990daffb01d8091e8af9a91227370cfa6f7d1c44e7ab062940486c57676418c", digest);
    }

    @Test
    public void testGenerateSessionKey() {
        String key1 = SecureKeyUtils.generateSessionKey();
        System.out.println("sessionKey: " + key1);
        String key2 = SecureKeyUtils.generateSessionKey();
        System.out.println("sessionKey: " + key2);
        Assert.assertNotEquals(key1, key2);
    }

}
