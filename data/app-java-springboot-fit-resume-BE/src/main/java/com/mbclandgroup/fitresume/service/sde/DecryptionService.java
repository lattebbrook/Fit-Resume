package com.mbclandgroup.fitresume.service.sde;

import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.model.Candidate;
import org.springframework.stereotype.Service;

@Service
public
class DecryptionService {


    public Candidate decipher(SharedInstance instance, Candidate data) {
        if(instance instanceof SharedInstance && instance.getInstanceUUID() == instance.getInstanceUUID()) {
//            return deconstructAES256(data);
            return null;
        }
        return null;
    }

//    private Candidate deconstructAES256(Candidate data) {
//        //AES256 decrypted
//        return new Candidate();
//    }
}
