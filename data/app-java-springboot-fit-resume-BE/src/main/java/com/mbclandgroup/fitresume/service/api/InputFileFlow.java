package com.mbclandgroup.fitresume.service.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mbclandgroup.fitresume.enums.ECommand;
import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.model.Candidate;
import com.mbclandgroup.fitresume.repository.impl.CandidateRepositoryImpl;
import com.mbclandgroup.fitresume.service.sde.DecryptionService;
import com.mbclandgroup.fitresume.service.sde.EncryptionService;
import com.mbclandgroup.fitresume.service.sde.SDEFlowService;
import com.mbclandgroup.fitresume.utils.MiddleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class InputFileFlow {

    private final DecryptionService decrypt;
    private final EncryptionService encrypt;
    private boolean isReadyToMoveFile = false;
    private boolean isFailValidate = false;

    @Autowired
    public InputFileFlow(DecryptionService decrypt, EncryptionService encrypt) {
        this.decrypt = decrypt;
        this.encrypt = encrypt;
    }

    public Object doAction(SharedInstance sharedInstance, ECommand Command) throws IOException {
        switch (Command) {
            case SCAN -> {
                return scanFileForWork(sharedInstance);
            }
            case READ -> readAndConvert(sharedInstance);
            case ENCRYPT -> encryptFlow(sharedInstance);
            case DECRYPT -> decryptFlow(sharedInstance);
        }
        return null;
    }


    /** <h3>TODO Create adaptor to assign corresponding field into the proper field in object </h3>
    * <p> 1. Iterate over the path temp and call json object then read from json object. class</p>
    * <p> 2. Iterating from the jsonObject entry set and then load json element file </p>
    * <p> 3. Check value from specific entry set content from tempMap if contain</p>
    * <p><b># Note : the candidate extracted data supposed to keep data HashMap[File, Object]</b></p>
    **/
    public void readAndConvert(SharedInstance instance) throws IOException {
        HashMap<String, Candidate> candidateMap = new LinkedHashMap<>();

        Gson gson = new Gson();
        Path pathTemp = new File(instance.getPathTo()).toPath().toAbsolutePath();
        Candidate candidate;

        if (pathTemp.toFile().listFiles().length > 0) {

            for (File element : pathTemp.toFile().listFiles()) {
                JsonObject myJsonObject = new Gson().fromJson(new FileReader(element), JsonObject.class);

                for (Map.Entry<String, JsonElement> entry : myJsonObject.entrySet()) {

                    //TODO add key into the map objectMap key = file pdf name value = Candidate
                    String key = entry.getKey();
                    JsonElement jsonElement = JsonParser.parseString(entry.getValue().getAsString());
                    String prevJsonString = gson.toJson(jsonElement);

                    String formattedString = prevJsonString
                            .replace("ชื่อ", "name")
                            .replace("อายุ", "age")
                            .replace("วันเกิด", "dateOfBirth")
                            .replace("เบอร์โทร", "tel")
                            .replace("ที่อยู่", "address")
                            .replace("วุฒิการศึกษา", "degree")
                            .replace("ตำแหน่งปัจจุบัน", "currentPosition")
                            .replace("ที่ทำงานปัจจุบัน", "currentWorkplace")
                            .replace("ระยะเวลาทำงาน", "durationOfWork")
                            .replace("ลักษณะงาน", "skills")
                            .replace("เงินเดือนที่คาดหวัง", "expectedSalary")
                            .replace("เงินเดือนปัจจุบัน", "currentSalary");

                    JsonElement formattedJsonElement = JsonParser.parseString(formattedString);
                    candidate = gson.fromJson(formattedJsonElement, Candidate.class);
                    candidateMap.put(key, candidate);
                }

                instance.putObjectMap(element, candidateMap);
            }

            //to encryption
            System.out.println(instance.getObjectMap().toString());
        } else {
            //log out that it is empty.
            System.out.println("System error --> ERROR the system couldn't find any file, maybe the operation is not yet complete.");
        }
    }

    public HashMap<String, String> scanFileForWork(SharedInstance instance){

        //initialization
        HashMap<String, String> resultHashMap = new LinkedHashMap<>();
        Path currentDir = new File(instance.getPathFrom()).toPath().toAbsolutePath();

        if(currentDir.toFile().listFiles().length > 0) {
            String line = null;
            try {

                String pathPython = "../../data/app-python-BE/fit-resume-python-app/main.py";
                ProcessBuilder pb = new ProcessBuilder("python", pathPython);

                Process process = pb.start();
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                /* read the output from the command */
                while ((line = stdInput.readLine()) != null) {
                    resultHashMap.put("ReturnResult", line);
                }

                // read any errors from the attempted command
                while ((line = stdError.readLine()) != null) {
                    resultHashMap.put("ReturnResult", line);
                }

                stdInput.close();
                stdError.close();
            } catch (IOException e) {
                System.err.println("ERROR: Unable to process scanFileForWork " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            //log out that it is empty.
            System.out.println("System stdout --> There's no file in path main directory, upload or place file first.");
        }

        return resultHashMap;
    }

    public void encryptFlow(SharedInstance instance){
        SDEFlowService sdeFlowService = new SDEFlowService(decrypt, encrypt, instance);

        if(instance.getInstanceUUID() != null) {

            if(!MiddleService.isPathFromEmpty(new File(instance.getPathFrom()))) {

                try {

                    File file = new File(instance.getPathFrom());
                    ArrayList<File> arrFile = new ArrayList<>();
                    File [] f = file.listFiles();

                    for(File element : f) {
                        if(element.getName().length() <= 256) {
                            arrFile.add(element);
                        } else {
                            this.isFailValidate = true;
                            instance.addReasonOfErrors(element.getName() + " file name length is " + element.getName().length() + " is greater than 256 character!");
                            System.err.println("ERROR: Cannot process because the file length is greater than 256.");
                            break;
                        }
                    }

                    if(!isFailValidate) {
                        instance.setListFile(arrFile);
                        for(int i = 0; i < arrFile.size(); i++) {
                            //TODO read file and put each file into map
//                            this.readFileAPI(instance, file);
                        }

                        try {
                            /**  after reading through file and set data to map on instance, set encrypted map to new map */
                            for(File element : instance.getMapObjectCandidate().keySet()) { //TODO FIXME create object map stored in the instance
                                instance.putEncryptedMap(element, sdeFlowService.reqResponseFromEncrypt(instance));
                            }

                            /** post encrypted data to MongoDB */
                            this.PostMongoDBFlow(instance);

                        } catch (Exception e) {
                            this.isFailValidate = true;
                            System.err.println("ERROR: Cannot post data to database, contact administrator team immediately! " + e.getMessage());

                            e.printStackTrace();
                        }

                        /** after encrypted then move file to path ready to be rm -r */
                        if(isReadyToMoveFile) {
                            //TODO redesign move file service
                        }
                    } else {
                        System.err.println("Failed to validate file, unable to continue. Reason of Error(s): \n");
                        System.err.println(instance.getReasonOfErrors().toString());
                    }
                } catch (Exception e) {
                    System.err.println("ERROR: Spring boot application cannot find any file(s) at all.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Folder empty, will perform work, please put file(s) in folder or upload through Front End.");
            }
        }
    }

    public void decryptFlow(SharedInstance instance){
        if(instance.getInstanceUUID() != null) {
            //TODO decrypt flow
        }
    }

//    public void readFileAPI(SharedInstance instance, File file){
//        //TODO f(x) read file api -->
//        /* iterate through each line of file when has next line
//         *  - map each String object iterate correctly to the Candidate class
//         *  - Save data in shared instance */
//        Candidate candidate = new Candidate(
//
//        );
//
//        instance.putMapObjectCandidate(file, candidate);
//    }

    public void PostMongoDBFlow(SharedInstance instance) {
        if(instance.getEncryptedMap().size() > 0) {
            isReadyToMoveFile = true;
            CandidateRepositoryImpl candidateRepository = new CandidateRepositoryImpl();
            //TODO save to db, take LinkedHashMap from instance first ** don't forget **
        }
    }

    // Helper method to get a String value from JsonElement, or return null if not found
    private String getStringOrNull(JsonElement jsonElement, String key) {
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement element = jsonObject.get(key);
            return (element != null && !element.isJsonNull()) ? element.getAsString() : "NULL";
        }
        return "NULL";
    }

    // Helper method to get an Integer value from JsonElement, or return null if not found
    private Integer getIntegerOrNull(JsonElement jsonElement, String key) {
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement element = jsonObject.get(key);
            return (element != null && !element.isJsonNull()) ? element.getAsInt() : 0;
        }
        return 0;
    }

}
