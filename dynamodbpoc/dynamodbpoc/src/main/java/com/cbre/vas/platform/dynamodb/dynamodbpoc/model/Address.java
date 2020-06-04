package com.cbre.vas.platform.dynamodb.dynamodbpoc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBDocument
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  @DynamoDBAttribute
  private String addressLine1;
  @DynamoDBAttribute
  private String addressLine2;
  @DynamoDBAttribute
  private String city;
  @DynamoDBAttribute
  private String state;
  @DynamoDBAttribute
  private String zipCode;


}
