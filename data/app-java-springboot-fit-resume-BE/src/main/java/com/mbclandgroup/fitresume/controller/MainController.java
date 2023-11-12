package com.mbclandgroup.fitresume.controller;

import com.google.gson.Gson;
import com.mbclandgroup.fitresume.config.ConfigInterface;
import com.mbclandgroup.fitresume.enums.ECommand;
import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.service.api.InputFileFlow;
import com.mbclandgroup.fitresume.service.sde.SDEFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    private final ConfigInterface resourceConfig;
    private final InputFileFlow inputFileFlow;
    private final SDEFlowService sdeFlowService;

    @Autowired
    public MainController(ConfigInterface resourceConfig, InputFileFlow inputFileFlow, SDEFlowService sdeFlowService, Gson gson){
        this.resourceConfig = resourceConfig;
        this.inputFileFlow = inputFileFlow;
        this.sdeFlowService = sdeFlowService;
    }

    @PostMapping("/scan")
    public ResponseEntity<?> scan() throws Exception {
        SharedInstance instance = new SharedInstance(resourceConfig);
        return ResponseEntity.ok().body(inputFileFlow.doAction(instance, ECommand.SCAN));
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
    public ResponseEntity<?> readAndConvert() throws Exception {
        SharedInstance instance = new SharedInstance(resourceConfig);
        return ResponseEntity.ok().body(inputFileFlow.doAction(instance, ECommand.CREATE));
    }

    @GetMapping("/cipher")
    @Deprecated
    public String cipher(@RequestParam String text){
        Map<String, Boolean> cipherMap = new LinkedHashMap<>();
        SharedInstance instance = new SharedInstance(resourceConfig);
        String encryption = sdeFlowService.encrypt(instance, text);
        String decryption = sdeFlowService.decrypt(instance, encryption);
        return encryption;
    }

    @GetMapping("/decipher")
    @Deprecated
    public String decipher(@RequestParam String text){
        Map<String, Boolean> cipherMap = new LinkedHashMap<>();
        SharedInstance instance = new SharedInstance(resourceConfig);
        String decryption = sdeFlowService.decrypt(instance, text);
        return decryption;
    }
}
