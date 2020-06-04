package com.cbre.vas.platform.dynamodb.dynamodbpoc.repositories;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.cbre.vas.platform.dynamodb.dynamodbpoc.model.Student;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDBRepository {

  public static final Logger logger = LoggerFactory.getLogger(DynamoDBRepository.class);

  @Autowired
  private DynamoDBMapper dynamoDBMapper;

  public void insertIntoDynamoDB(Student student) {
    dynamoDBMapper.save(student);
  }

  public Student getOneStudentDetail(String studentId, String lastName) {
    return dynamoDBMapper.load(Student.class, studentId, lastName);
  }

  public void deleteStudent(Student student) {
    dynamoDBMapper.delete(student);
  }

  public void updateStudentDetail(Student student) {
    try {
      dynamoDBMapper.save(student, buildSaveExpression(student));
    } catch (ConditionalCheckFailedException exceptio) {
      logger.error("condition failed");
    }
  }

  private DynamoDBSaveExpression buildSaveExpression(Student student) {

    DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
    Map<String, ExpectedAttributeValue> expected = new HashMap<>();

    expected.put("studentId", new ExpectedAttributeValue(new AttributeValue(student.getStudentId()))
        .withComparisonOperator(
            ComparisonOperator.EQ));

    dynamoDBSaveExpression.setExpected(expected);

    return dynamoDBSaveExpression;
  }


}
