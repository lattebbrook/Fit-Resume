package com.mbclandgroup.fitresume.service.sde;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Service
public class EncryptionService {

    //ENCRYPTION
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static byte[] keyValue;
    private static final byte[] iv = new byte[16]; // Use a fixed or generate a random IV

    public String encryptingData(String data) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] encVal = c.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Invalid key range, required 16 bytes of key but found " + keyValue.length);
        }
        return "ERROR";
    }

    public void setKeyValue(String key) {
        keyValue = key.getBytes(); // Convert string directly to bytes
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, "AES");
    }

}
