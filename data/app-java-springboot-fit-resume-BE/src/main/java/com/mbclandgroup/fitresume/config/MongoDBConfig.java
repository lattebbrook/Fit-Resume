package com.mbclandgroup.fitresume.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoDBConfig {

    /** Spring data mongodb version 3.1.x seem to not work well with URI, I have to put the database name before question mark in the uri.
    *   -- Tanawat */
    private final ConfigInterface configInterface;

    @Autowired
    public MongoDBConfig(ConfigInterface configInterface) {
        this.configInterface = configInterface;
    }

    @Bean
    public SimpleMongoClientDatabaseFactory mongoDatabaseFactory() {
        String connectionString = configInterface.getConfigModel().getDatabaseURL();
        return new SimpleMongoClientDatabaseFactory(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory());
    }
}
