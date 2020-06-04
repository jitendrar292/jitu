package com.cbre.vas.platform.dynamodb.dynamodbpoc.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableDynamoDBRepositories(basePackages = "com.cbre.vas.platform.dynamodb.dynamodbpoc.repositories")
public class DynamoDBConfig {

  @Value("${amazon.dynamodb.endpoint}")
  private String dynamoDbEndpoint;

  @Value("${amazon.aws.accesskey}")
  private String awsAccessKey;

  @Value("${amazon.aws.secretkey}")
  private String awsSecretKey;

  @Value("${amazon.region}")
  private String awsRegion;


  @Bean
  public DynamoDBMapper dynamoDBMapper() {
    return new DynamoDBMapper(amazonDynamoDBConfig());
  }

  private AmazonDynamoDB amazonDynamoDBConfig() {

    return AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(new EndpointConfiguration(dynamoDbEndpoint, awsRegion))
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
        .build();
  }


}
