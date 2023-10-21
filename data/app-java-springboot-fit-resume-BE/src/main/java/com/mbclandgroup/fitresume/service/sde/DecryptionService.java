package com.mbclandgroup.fitresume.service.sde;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class DecryptionService {

    //DECRYPTION
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static byte[] keyValue;
    private static final byte[] iv = new byte[16]; // Use a fixed or generate a random IV

    public String decrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            BadPaddingException, IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] plainText = c.doFinal(Base64.getDecoder().decode(data));
        return new String(plainText, "UTF-8");
    }

    public void setKeyValue(String key) {
        keyValue = key.getBytes(); // Convert string directly to bytes
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, "AES");
    }

}
