package com.example.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class PaymentConfig {
    // This class enables auditing for MongoDB.
}
