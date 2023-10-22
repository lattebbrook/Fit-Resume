package com.mbclandgroup.fitresume.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigModel {

    private String env;
    private String pathFrom;
    private String pathTo;
    private String pathError;
    private String pathBackUp;
    private String DatabaseURL;
    private byte Transaction_Limit_Rate;
    private String AES128Key;
    private boolean InstallStatus;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
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

    public String getPathError() {
        return pathError;
    }

    public void setPathError(String pathError) {
        this.pathError = pathError;
    }

    public String getPathBackUp() {
        return pathBackUp;
    }

    public void setPathBackUp(String pathBackUp) {
        this.pathBackUp = pathBackUp;
    }

    public String getDatabaseURL() {
        return DatabaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        DatabaseURL = databaseURL;
    }

    public byte getTransaction_Limit_Rate() {
        return Transaction_Limit_Rate;
    }

    public void setTransaction_Limit_Rate(byte transaction_Limit_Rate) {
        Transaction_Limit_Rate = transaction_Limit_Rate;
    }

    public String getAES128Key() {
        return AES128Key;
    }

    public void setAES128Key(String AES128Key) {
        this.AES128Key = AES128Key;
    }

    public boolean isInstallStatus() {
        return InstallStatus;
    }

    public void setInstallStatus(boolean installStatus) {
        InstallStatus = installStatus;
    }

    @Override
    public String toString(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
