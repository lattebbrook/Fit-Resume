package com.mbclandgroup.fitresume.service.sde;

import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.model.Candidate;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    public Candidate cipher(SharedInstance instance, Candidate data){
        if(instance instanceof SharedInstance && instance.getInstanceUUID() == instance.getInstanceUUID()) {
//            return constructAES256Logic(data);
            return null;
        }
        return null;
    }

//    private Candidate constructAES256Logic(Candidate data) {
//        //AES256 encrypted whole class
//        return new Candidate();
//    }

}
