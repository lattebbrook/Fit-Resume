package com.mbclandgroup.fitresume.controller;

import com.mbclandgroup.fitresume.config.ResourceConfig;
import com.mbclandgroup.fitresume.enums.ECommand;
import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.service.api.InputFileFlow;
import com.mbclandgroup.fitresume.service.sde.DecryptionService;
import com.mbclandgroup.fitresume.service.sde.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    public final ResourceConfig resourceConfig;
    private final DecryptionService decrypt;
    private final EncryptionService encrypt;

    @Autowired
    public MainController(ResourceConfig resourceConfig, DecryptionService decrypt, EncryptionService encrypt){
        this.resourceConfig = resourceConfig;
        this.decrypt = decrypt;
        this.encrypt = encrypt;
    }

    @PostMapping("/scan")
    public ResponseEntity<?> scan() throws IOException {
        SharedInstance instance = new SharedInstance(resourceConfig);
        InputFileFlow inputFileFlow = new InputFileFlow(decrypt, encrypt);
        return ResponseEntity.ok(inputFileFlow.doAction(instance, ECommand.SCAN));
    }

    /** <h3>Method readAndConvert()</h3>
     *  <p> 1. after python done with formatter --> send request to this expose PostMapping </p>
     *  <p> 2. check path tmp, if empty throws error back to the python, python return request back to the caller. </p>
     *  <p> 3. -- if not error then it will read each json file and mapped to Candidate object </p>
     *  <p> 4. -- if(read)
     *  <p>       '-- for each key of the json object --> mapped into each field of Candidate.java </p>
     *  <p>       '-- where field name = key & value = value stored in the json object file. </p>
     *  <p> 5. After reading through all files, encrypt all data and post to mongodb. </p>
     */
    @PostMapping("/convert")
    public ResponseEntity<?> readAndConvert() throws IOException {
        SharedInstance instance = new SharedInstance(resourceConfig);
        InputFileFlow inputFileFlow = new InputFileFlow(decrypt, encrypt);

        return ResponseEntity.ok(inputFileFlow.doAction(instance, ECommand.READ));
    }

    @GetMapping("/test")
    public Object test() throws IOException {
        SharedInstance instance = new SharedInstance(resourceConfig);
        File file = new File(instance.getPathFrom());
        File [] f = file.listFiles();
        ArrayList<String> arr = new ArrayList<>();
        for(File elem : f) {
            arr.add(elem.getName().toString());
        }

        return arr;
    }
}
