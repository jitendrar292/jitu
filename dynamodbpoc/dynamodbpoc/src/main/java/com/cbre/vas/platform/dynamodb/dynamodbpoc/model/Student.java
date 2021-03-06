package com.cbre.vas.platform.dynamodb.dynamodbpoc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

  @DynamoDBHashKey
  @DynamoDBAutoGeneratedKey
  private String studentId;
  @DynamoDBAttribute
  private String firstName;
  @DynamoDBRangeKey
  private String lastName;
  @DynamoDBAttribute
  private String age;
  @DynamoDBAttribute
  private String randomValueInsert;
  @DynamoDBAttribute
  private String randomValueModify;
  @DynamoDBAttribute
  private Address address;



}
