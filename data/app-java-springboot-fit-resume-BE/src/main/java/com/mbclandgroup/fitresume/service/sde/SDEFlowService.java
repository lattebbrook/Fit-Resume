package com.mbclandgroup.fitresume.service.sde;

import com.mbclandgroup.fitresume.instance.SharedInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** <h2>String Encryption Service </h2>
 *  <p>Encrypting String data before sending to database</p>
 *  <p>Standard AES-128, 16 bit key requires Encryption and Decryption</p>
 */
@Service
public class SDEFlowService {

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private DecryptionService decryptionService;

    private String uuid;

    public String encrypt(SharedInstance instance, String data){
        try {
            if(!data.isEmpty() || !data.equals(" ") || data != null) {
                encryptionService.setKeyValue(instance.getResourceConfig().getConfigModel().getAES128Key());
                return encryptionService.encryptingData(data);
            } else {
                return "N/A";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String decrypt(SharedInstance instance, String encryptedData){
        try {
            if(!encryptedData.isEmpty() || !encryptedData.equals(" ") || encryptedData != null) {
                decryptionService.setKeyValue(instance.getResourceConfig().getConfigModel().getAES128Key());
                return decryptionService.decrypt(encryptedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }
}
