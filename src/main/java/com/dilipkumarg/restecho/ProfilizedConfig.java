package com.dilipkumarg.restecho;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Profile;


public class ProfilizedConfig {

    @Profile("!mongo")
    @EnableAutoConfiguration(
            exclude = {
                    MongoAutoConfiguration.class,
                    MongoDataAutoConfiguration.class,
                    MongoRepositoriesAutoConfiguration.class
            })
    static class WithoutDB {

    }

    @Profile("mongo")
    @EnableAutoConfiguration
    static class WithDB {

    }
}
