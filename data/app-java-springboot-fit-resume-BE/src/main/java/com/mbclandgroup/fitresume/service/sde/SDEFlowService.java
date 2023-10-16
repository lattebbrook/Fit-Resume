package com.mbclandgroup.fitresume.service.sde;

import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SDEFlowService {

    private final DecryptionService decrypt;
    private final EncryptionService encrypt;
    private final SharedInstance instance;
    private String uuid;

    @Autowired
    public SDEFlowService(DecryptionService decrypt, EncryptionService encrypt, SharedInstance instance) {
        this.decrypt = decrypt;
        this.encrypt = encrypt;
        this.instance = instance;
        this.uuid = instance.getInstanceUUID();
    }

    public Candidate reqResponseFromEncrypt(SharedInstance instance){
        return doEncryptProcess(instance);
    }

    public void reqResponseFromDecrypt(){

    }

    private Candidate doEncryptProcess(SharedInstance instance){
        for(File element : instance.getMapObjectCandidate().keySet()) {
            return encrypt.cipher(instance, instance.getMapObjectCandidate().get(element));
        }
        return null;
    }

    private Candidate doDecryptProcess(Candidate data){
        return decrypt.decipher(instance, data);
    }

    public String getUuid() {
        return uuid;
    }

}
