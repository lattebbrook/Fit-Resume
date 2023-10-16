package com.mbclandgroup.fitresume.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigModel {

    private String token;
    private String env;
    private String OpenAI_API_Key;
    private String pathFrom;
    private String pathTo;
    private String pathError;
    private String pathBackUp;
    private String Database_Username;
    private String Database_Password;
    private byte Transaction_Limit_Rate;
    private boolean InstallStatus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getOpenAI_API_Key() {
        return OpenAI_API_Key;
    }

    public void setOpenAI_API_Key(String openAI_API_Key) {
        OpenAI_API_Key = openAI_API_Key;
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

    public String getDatabase_Username() {
        return Database_Username;
    }

    public void setDatabase_Username(String database_Username) {
        Database_Username = database_Username;
    }

    public String getDatabase_Password() {
        return Database_Password;
    }

    public void setDatabase_Password(String database_Password) {
        Database_Password = database_Password;
    }

    public byte getTransaction_Limit_Rate() {
        return Transaction_Limit_Rate;
    }

    public void setTransaction_Limit_Rate(byte transaction_Limit_Rate) {
        Transaction_Limit_Rate = transaction_Limit_Rate;
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
            return objectMapper.writeValueAsString(getClass().toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
