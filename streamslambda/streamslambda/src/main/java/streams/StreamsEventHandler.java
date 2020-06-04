package streams;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StreamsEventHandler implements RequestHandler<DynamodbEvent, Void> {

  private static final String DYNAMODB_TABLE_NAME = "student";
  private static final String INSERT = "INSERT";
  private static final String MODIFY = "MODIFY";

  private DynamoDB dynamoDB;
  private Table table;

  public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {

    LambdaLogger logger = context.getLogger();

    for (DynamodbStreamRecord record : dynamodbEvent.getRecords()) {

      try {

        logger.log("Eventname :" + record.getEventName());
        logger.log("Keys :" + record.getDynamodb().getKeys());

        ObjectMapper objectMapper = new ObjectMapper();

        if (null == dynamoDB) {
          dynamoDB = new DynamoDB(Regions.fromName(record.getAwsRegion()));
          table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);
        }

        String studentId = record.getDynamodb().getKeys().get("studentId").getS();
        String lastName = record.getDynamodb().getKeys().get("lastName").getS();

        logger.log("studentId : " + studentId);
        logger.log("lastName : " + lastName);
        logger.log("record.getEventName() : " + record.getEventName());

        if (record.getEventName().equals(INSERT)) {
          logger.log("New Image: INSERT " + record.getDynamodb().getNewImage());

          UpdateItemSpec updateItemSpec = new UpdateItemSpec()
              .withPrimaryKey("studentId", studentId, "lastName", lastName)
              .withUpdateExpression("set randomValueInsert = :RV")
              .withValueMap(
                  new ValueMap().withString(":RV", getRandomInteger(1, 100)))
              .withReturnValues(ReturnValue.UPDATED_NEW);

          try {
            logger.log("Updating the item... INSERT");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            logger.log("UpdateItem succeeded: INSERT \n" + outcome.getItem().toJSONPretty());

          } catch (Exception e) {
            logger.log("Unable to update item: INSERT " + studentId + " " + lastName);
            logger.log(e.getMessage());
          }
        } else if (record.getEventName().equals(MODIFY) &&
            !record.getDynamodb().getNewImage().containsKey("randomValueModify")) {
          logger.log("New Image: MODIFY  " + record.getDynamodb().getNewImage());
          logger.log("Old Image: MODIFY " + record.getDynamodb().getOldImage());

          UpdateItemSpec updateItemSpec = new UpdateItemSpec()
              .withPrimaryKey("studentId", studentId, "lastName", lastName)
              .withUpdateExpression("set randomValueModify = :RV")
              .withValueMap(
                  new ValueMap().withString(":RV", getRandomInteger(1, 100)))
              .withReturnValues(ReturnValue.UPDATED_NEW);

          try {
            logger.log("Updating the item... MODIFY");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            logger.log("UpdateItem succeeded MODIFY :\n" + outcome.getItem().toJSONPretty());

          } catch (Exception e) {
            logger.log("Unable to update item: MODIFY" + studentId + " " + lastName);
            logger.log(e.getMessage());
          }
        }

      } catch (Exception ex) {

        logger.log("Error : " + ex.getMessage());
      }
    }

    return null;
  }


  public static String getRandomInteger(int maximum, int minimum) {

    return String.valueOf((int) (Math.random() * (maximum - minimum)) + minimum);

  }


}

