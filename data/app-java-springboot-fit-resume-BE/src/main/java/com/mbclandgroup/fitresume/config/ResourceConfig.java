package com.mbclandgroup.fitresume.config;

import com.google.gson.Gson;
import com.mbclandgroup.fitresume.model.ConfigModel;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;

@Component
@Scope("singleton")
public class ResourceConfig {

    private ConfigModel configModel;

    @PostConstruct
    public void setConfigModel() {
        configModel = null;

        try {
            File file = new File("../../conf/envconfig.json");
            String configContent = new String(Files.readAllBytes(file.toPath()));
            Gson gson = new Gson();
            configModel = new ConfigModel();
            configModel = gson.fromJson(configContent, ConfigModel.class);
        } catch (Exception e) {
            System.err.println("ERROR: Cannot instantiate config model, post construct failed with message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ConfigModel getConfigModel(){
        return configModel;
    }

}
