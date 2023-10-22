package com.mbclandgroup.fitresume.service.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mbclandgroup.fitresume.enums.ECommand;
import com.mbclandgroup.fitresume.instance.SharedInstance;
import com.mbclandgroup.fitresume.model.Candidate;
import com.mbclandgroup.fitresume.repository.CandidateRepository;
import com.mbclandgroup.fitresume.service.sde.SDEFlowService;
import com.mbclandgroup.fitresume.utils.UtilsMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

@Component
@Scope("prototype")
public class InputFileFlow {

    private final SDEFlowService sdeFlowService;
    private final CandidateRepository candidateRepository;

    @Autowired
    public InputFileFlow( SDEFlowService sdeFlowService, CandidateRepository candidateRepository) {
        this.sdeFlowService = sdeFlowService;
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

    /** <h3> Read and Convert file from json to object in java using gson </h3>
     * <p> 1. Iterate over the path temp and call json object then read from json object.</p>
     * <p> 2. Iterating from the jsonObject entry set and then load json element as file to java object Candidate</p>
     * <p> 3. Check value from specific entry set content from tempMap if contain</p>
     **/
    public String readAndConvert(SharedInstance instance) throws IOException {

        Gson gson = new Gson();
        Path pathTemp = new File(instance.getPathTo()).toPath().toAbsolutePath();
        Candidate candidate = null;
        String key = "";
        File temp = null;
        FileReader fr = null;

        if (pathTemp.toFile().listFiles().length > 0) {

            for(File e : pathTemp.toFile().listFiles()) {
                instance.getListFile().add(e);
            }

            for (File element : pathTemp.toFile().listFiles()) {
                fr = new FileReader(element);
                JsonObject myJsonObject = new Gson().fromJson(fr, JsonObject.class);
                temp = element;

                for (Map.Entry<String, JsonElement> entry : myJsonObject.entrySet()) {

                    //TODO add key into the map objectMap key = file pdf name value = Candidate
                    key = entry.getKey();
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

            System.out.println(instance.getListCandidateFile().toString());
            fr.close();

            if(!instance.getListCandidateFile().isEmpty()) {
                encryptFlow(instance);
            }

            return instance.toString();
        } else {
            //log out that it is empty.
            System.out.println("System stdout --> The system couldn't find any file, please put file or wait the previous microservice operation to complete.");
        }

        return instance.toString();
    }

    public void encryptFlow(SharedInstance instance) throws IOException {

        if(instance != null && instance.getInstanceUUID() != null) {

            if(instance.getListCandidateFile().size() > 0) {
                //create copy for the operation
                Queue<Candidate> queueList = new LinkedList<>(instance.getListCandidateFile());

                for (Candidate element : queueList) {
                    element.setName(sdeFlowService.encrypt(instance, element.getName()));
                    element.setAge(sdeFlowService.encrypt(instance, element.getAge()));
                    element.setDateOfBirth(sdeFlowService.encrypt(instance, element.getDateOfBirth()));
                    element.setTel(sdeFlowService.encrypt(instance, element.getTel()));
                    element.setAddress(sdeFlowService.encrypt(instance, element.getAddress()));
                    element.setDegree(sdeFlowService.encrypt(instance, element.getDegree()));
                    element.setCurrentPosition(sdeFlowService.encrypt(instance, element.getCurrentPosition()));
                    element.setCurrentWorkplace(sdeFlowService.encrypt(instance, element.getCurrentWorkplace()));
                    element.setDurationOfWork(sdeFlowService.encrypt(instance, element.getDurationOfWork()));
                    element.setSkills(sdeFlowService.encrypt(instance, element.getSkills()));
                    element.setExpectedSalary(sdeFlowService.encrypt(instance, element.getExpectedSalary()));
                    element.setCurrentSalary(sdeFlowService.encrypt(instance, element.getCurrentSalary()));
                }

                //to database
                if(checkSDEFlowServiceCorrectness(instance, queueList)) {
                    PostMongoDBFlow(instance, queueList);
                }
            }

            //finishing flow
            validFlow(instance);
        }
    }

    // Decrypting and then check, if the data "decrypted" with the key has the same value as getListCandidate then
    // We only need to check 1 object to be sure sdeFlowService is working
    public boolean checkSDEFlowServiceCorrectness(SharedInstance instance, Queue<Candidate> queueList){
        if(instance != null && instance.getInstanceUUID() != null) {
            return instance.getListCandidateFile().get(0).getName().equals(queueList.peek().getName());
        }
        return false;
    }

    public String checkTest(SharedInstance instance, String text){
        System.out.println("Text to decipher: " + text);
        return sdeFlowService.decrypt(instance, text);
    }

    public void PostMongoDBFlow(SharedInstance instance, Queue<Candidate> queueList) {
        if(instance != null && instance.getInstanceUUID() != null) {
            if(queueList.size() > 0) {
                while(queueList.size() > 0) {
                    candidateRepository.insert(queueList.poll());
                }
            }
        }
    }

    public void moveFile(SharedInstance instance) throws IOException {
        System.out.println("getListFile size = " + instance.getListFile().size());
        if(instance.getListFile().size() > 0) {

            for(File element : instance.getListFile()) {
                UtilsMethod.moveFile(element, instance.getResourceConfig().getConfigModel().getPathBackUp());
            }
        } else {
            System.out.println("ERROR cannot move file due to unknown reason.");
        }

        if(instance.getReasonOfErrors().size() > 0) {
            for(Map.Entry<File, String> reasonErr : instance.getReasonOfErrors().entrySet()) {
                UtilsMethod.moveFileError(reasonErr.getKey(), instance.getResourceConfig().getConfigModel().getPathError());
            }
        }

        File file = new File(instance.getPathFrom());
        for(File element : file.listFiles()){
            UtilsMethod.moveFile(element, instance.getResourceConfig().getConfigModel().getPathBackUp() + "\\pdf");
        }


        System.out.println("Returning system to normal, awaiting new order.");
    }

    public void validFlow(SharedInstance instance) throws IOException {
        moveFile(instance);
        instance = null;
        System.gc();
    }


}
