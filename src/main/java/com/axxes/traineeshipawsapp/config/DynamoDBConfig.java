package com.axxes.traineeshipawsapp.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {
    @Value("${aws.dynamodb.accesskey}")
    private String accesKey;

    @Value("${aws.dynamodb.secretkey}")
    private String secretKey;


    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_WEST_1)
                .withCredentials(dynamoDbCredentialsProvider())
                .build();
    }

    @Bean
    public AWSCredentials dynamoDbCredentials() {
        return new BasicAWSCredentials(accesKey, secretKey);
    }

    @Bean
    public AWSCredentialsProvider dynamoDbCredentialsProvider() {
        return new AWSStaticCredentialsProvider(dynamoDbCredentials());
    }
}

