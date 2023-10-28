package com.mbclandgroup.fitresume.config;

import com.mbclandgroup.fitresume.model.ConfigModel;
import org.springframework.context.annotation.Configuration;

@Configuration
public interface ConfigInterface {
    void setConfigModel();
    ConfigModel getConfigModel();
}
