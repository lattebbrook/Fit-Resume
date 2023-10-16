package com.mbclandgroup.fitresume.instance;

import com.mbclandgroup.fitresume.config.ResourceConfig;
import com.mbclandgroup.fitresume.model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

@Component
public class SharedInstance {

    private UUID uuid;

    private String instanceUUID;
    private String pathFrom;
    private String pathTo;

    private byte processLimit;             //process limit set by user
    private final byte rateLimit;          //rate limit of application, hard coded to prevent performance issue

    private ArrayList<File> listFile;
    private HashMap<File, HashMap<String, Candidate>> objectMap = new LinkedHashMap<>();
    private LinkedHashMap<File, Candidate> encryptedMap;

    private ArrayList<String> reasonOfErrors;

    private final ResourceConfig resourceConfig;

    @Autowired
    public SharedInstance(ResourceConfig resourceConfig){
        this.resourceConfig = resourceConfig;
        this.instanceUUID = UUID.randomUUID().toString();
        this.rateLimit = 5;
        this.pathFrom = resourceConfig.getConfigModel().getPathFrom();
        this.pathTo = resourceConfig.getConfigModel().getPathTo();
        this.processLimit = resourceConfig.getConfigModel().getTransaction_Limit_Rate();

        if(this.processLimit > rateLimit) {
            processLimit = rateLimit;
        }
    }

    public String getInstanceUUID() {
        return instanceUUID;
    }

    public void setInstanceUUID(String instanceUUID) {
        this.instanceUUID = instanceUUID;
    }

    public String getPathFrom() {
        return pathFrom;
    }

    public void setPathFrom(String pathFrom) {
        this.pathFrom = pathFrom;
    }

    public String getPathTo() {
        return pathTo;
    }

    public void setPathTo(String pathTo) {
        this.pathTo = pathTo;
    }

    public ArrayList<File> getListFile() {
        return listFile;
    }

    public void setListFile(ArrayList<File> listFile) {
        this.listFile = listFile;
    }

    public HashMap<File, HashMap<String, Candidate>> getObjectMap() {
        return objectMap;
    }

    public void putObjectMap(File key, HashMap<String, Candidate> value) {
        this.objectMap.put(key, value);
    }

    public LinkedHashMap<File, Candidate> getEncryptedMap() {
        return encryptedMap;
    }

    public void putEncryptedMap(File key, Candidate value) {
        this.encryptedMap.put(key, value);
    }

    public ArrayList<String> getReasonOfErrors() {
        return reasonOfErrors;
    }

    public void addReasonOfErrors(String data) {
        this.reasonOfErrors.add(data);
    }

    public byte getProcessLimit() {
        return processLimit;
    }

    public void setProcessLimit(byte processLimit) {
        this.processLimit = processLimit;
    }

    public byte getRateLimit() {
        return rateLimit;
    }

    public ResourceConfig getResourceConfig() {
        return resourceConfig;
    }

    @Override
    public String toString(){
        return "";
    }
}
