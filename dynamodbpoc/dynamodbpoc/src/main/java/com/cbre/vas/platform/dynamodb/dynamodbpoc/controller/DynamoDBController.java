package com.cbre.vas.platform.dynamodb.dynamodbpoc.controller;

import com.cbre.vas.platform.dynamodb.dynamodbpoc.model.Student;
import com.cbre.vas.platform.dynamodb.dynamodbpoc.repositories.DynamoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDBController {

  @Autowired
  private DynamoDBRepository repository;

  @PostMapping
  public String insertIntoDynamoDB(@RequestBody Student student) {
    repository.insertIntoDynamoDB(student);
    return "Successfully Saved!!!";
  }

  @GetMapping
  public ResponseEntity<Student> getOneStudent(@RequestParam String studentId,
      @RequestParam String lastName) {
    Student student = repository.getOneStudentDetail(studentId, lastName);
    return new ResponseEntity<Student>(student, HttpStatus.OK);
  }

  @PutMapping
  public void updateStudent(@RequestBody Student student) {
    repository.updateStudentDetail(student);
  }

  @DeleteMapping(value = "{studentId}/{lastName}")
  public void deleteStudent(@PathVariable("studentId") String studentId,
      @PathVariable("lastName") String lastName) {
    Student student = new Student();
    student.setStudentId(studentId);
    student.setLastName(lastName);

    repository.deleteStudent(student);
  }

}
