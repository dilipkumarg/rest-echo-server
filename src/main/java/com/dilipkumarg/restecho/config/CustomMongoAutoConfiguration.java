/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dilipkumarg.restecho.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

//@Profile("mongo")
//@Configuration
public class CustomMongoAutoConfiguration extends MongoAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(type = {"com.mongodb.MongoClient", "com.mongodb.client.MongoClient"})
    public MongoClient mongo(
            MongoProperties properties, ObjectProvider<MongoClientOptions> options,
            Environment environment) {
        return new MongoClientFactory(properties, environment).createMongoClient(options.getIfAvailable());
    }
}