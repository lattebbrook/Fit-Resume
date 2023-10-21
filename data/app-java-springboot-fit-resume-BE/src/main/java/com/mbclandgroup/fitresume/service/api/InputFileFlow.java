package com.mbclandgroup.fitresume.service.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mbclandgroup.fitresume.enums.ECommand;
import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.model.Candidate;
import com.mbclandgroup.fitresume.repository.impl.CandidateRepositoryImpl;
import com.mbclandgroup.fitresume.service.sde.SDEFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

@Component
public class InputFileFlow {

    private final CandidateRepositoryImpl candidateRepImpl;
    private final SDEFlowService encryption;
    private final CandidateRepositoryImpl candidateRepository;

    private boolean isReadyToMoveFile = false;
    private boolean isFailValidate = false;

    @Autowired
    public InputFileFlow(CandidateRepositoryImpl candidateRepImpl, SDEFlowService encryption, CandidateRepositoryImpl candidateRepository) {
        this.candidateRepImpl = candidateRepImpl;
        this.encryption = encryption;
        this.candidateRepository = candidateRepository;
    }

    public Object doAction(SharedInstance sharedInstance, ECommand Command) throws IOException {
        switch (Command) {
            case SCAN -> {
                return scanFileForWork(sharedInstance);
            }
            case READ -> readAndConvert(sharedInstance);
            case ENCRYPT -> encryptFlow(sharedInstance);
        }
        return null;
    }

    public HashMap<String, String> scanFileForWork(SharedInstance instance){

        //initialization
        HashMap<String, String> resultHashMap = new LinkedHashMap<>();
        Path currentDir = new File(instance.getPathFrom()).toPath().toAbsolutePath();

        if(currentDir.toFile().listFiles().length > 0 && currentDir.toFile().listFiles() != null) {
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

    /** <h3> Read and Convert file from json to object in java using gson </h3>
     * <p> 1. Iterate over the path temp and call json object then read from json object.</p>
     * <p> 2. Iterating from the jsonObject entry set and then load json element as file to java object Candidate</p>
     * <p> 3. Check value from specific entry set content from tempMap if contain</p>
     **/
    public void readAndConvert(SharedInstance instance) throws IOException {

        Gson gson = new Gson();
        Path pathTemp = new File(instance.getPathTo()).toPath().toAbsolutePath();
        Candidate candidate = null;
        String key = "";

        if (pathTemp.toFile().listFiles().length > 0) {

            for (File element : pathTemp.toFile().listFiles()) {
                JsonObject myJsonObject = new Gson().fromJson(new FileReader(element), JsonObject.class);

                for (Map.Entry<String, JsonElement> entry : myJsonObject.entrySet()) {

                    //TODO add key into the map objectMap key = file pdf name value = Candidate
                    key = entry.getKey();
                    System.out.println("key[0]: " + key);
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
                    candidate.setFileName(key);
                    instance.addListCandidateFile(candidate);
                }
            }

            //to encryption
            System.out.println(instance.getListCandidateFile().toString());


            //TEST post data to mongodb
            for(int i = 0; i < instance.getListFile().size(); i++) {
//                candidateRepImpl.createData(instance.getListCandidateFile().get(i));
                System.out.println("Data created for candidate, instance of data " + instance.getListFile().get(i));
            }


        } else {
            //log out that it is empty.
            System.out.println("System error --> ERROR the system couldn't find any file, maybe the operation is not yet complete.");
        }
    }

    public void encryptFlow(SharedInstance instance){
        SDEFlowService sdeFlowService = new SDEFlowService();

        if(instance != null && instance.getInstanceUUID() != null) {
            //create copy for the operation
            Queue<Candidate> queueList = new LinkedList<>(instance.getListCandidateFile());

            for(Candidate element : queueList) {
                element.setName(encryption.encrypt(instance, element.getName()));
                element.setAge(encryption.encrypt(instance, element.getAge()));
                element.setDateOfBirth(encryption.encrypt(instance, element.getDateOfBirth()));
                element.setTel(encryption.encrypt(instance, element.getTel()));
                element.setAddress(encryption.encrypt(instance, element.getAddress()));
                element.setDegree(encryption.encrypt(instance, element.getDegree()));
                element.setCurrentPosition(encryption.encrypt(instance, element.getCurrentPosition()));
                element.setCurrentWorkplace(encryption.encrypt(instance, element.getCurrentWorkplace()));
                element.setDurationOfWork(encryption.encrypt(instance, element.getDurationOfWork()));
                element.setSkills(encryption.encrypt(instance, element.getSkills()));
                element.setExpectedSalary(encryption.encrypt(instance, element.getExpectedSalary()));
                element.setCurrentSalary(encryption.encrypt(instance, element.getCurrentSalary()));
            }

            if(checkEncryptionCorrectness(instance, queueList)) {
                PostMongoDBFlow(instance, queueList);
            }

            moveFileBackUp(instance);
            moveFileError(instance);
            System.gc();
        }
    }

    // Decrypting and then check, if the data "decrypted" with the key has the same value as getListCandidate then
    // We only need to check 1 object to be sure encryption is working
    public boolean checkEncryptionCorrectness(SharedInstance instance, Queue<Candidate> queueList){
        if(instance != null && instance.getInstanceUUID() != null) {
            //TODO Test
            return instance.getListCandidateFile().get(0).getName().equals(encryption.decrypt(instance, queueList.peek().getName()));
        }
        return false;
    }

    public void PostMongoDBFlow(SharedInstance instance, Queue<Candidate> queueList) {
        if(instance != null && instance.getInstanceUUID() != null) {
            if(queueList.size() > 0) {
                isReadyToMoveFile = true;
                while(queueList.size() > 0) {
                    candidateRepository.createData(queueList.poll());
                }
            }
        }
    }

    public void moveFileBackUp(SharedInstance instance) {
        if(isReadyToMoveFile && instance.getListFile().size() > 0) {
            //TODO move file to path backup
        } else {

        }
    }

    public void moveFileError(SharedInstance instance) {
        //TODO move file to path error in case there's any, create data structure to keep error file
    }

}
