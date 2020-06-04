package streams;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import streams.util.ClientUtils;


public class ClientStreamsLambdaES implements RequestHandler<DynamodbEvent, Void> {

  private static String index = "clients";
  private static final String HOST = "search-clients-sqluxsrtsa64lsw33vxyhsgwgu.us-east-1.es.amazonaws.com";
  private static final int PORT = 443;
  private static final String PROTOCOL = "https";
  private static final String DYNAMODB_TABLE_NAME = "client";

  private static final RestHighLevelClient elasticSearchClient = new RestHighLevelClient(
      RestClient.builder(new HttpHost(HOST, PORT, PROTOCOL)));

  private DynamoDB dynamoDB;
  private Table table;

  public Void handleRequest(DynamodbEvent ddbEvent, Context context) {

    LambdaLogger logger = context.getLogger();
    ClientUtils.setLogger(logger);

    try {
      for (DynamodbStreamRecord record : ddbEvent.getRecords()) {

        if (null == dynamoDB) {
          dynamoDB = new DynamoDB(Regions.fromName(record.getAwsRegion()));
          table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);
        }

        if (record.getEventName().equals("INSERT")) {

          String partitionKey = record.getDynamodb().getNewImage().get("PK").getS();
          String sortKey = record.getDynamodb().getNewImage().get("SK").getS();

          if (ClientUtils.isCompanyEvent(partitionKey, sortKey)) {

            logger.log("Entered CompanyEvent");

            String clientJSON = ClientUtils.getClientJson(record.getDynamodb().getNewImage());
            logger.log("Got complete client json : " + clientJSON);
            String companyUniqueId = partitionKey;
            logger.log("clientUniqueId : " + companyUniqueId);

            IndexRequest indexRequest = new IndexRequest(index);
            indexRequest.id(companyUniqueId);
            indexRequest.source(clientJSON, XContentType.JSON);
            try {
              IndexResponse indexResponse = elasticSearchClient
                  .index(indexRequest, RequestOptions.DEFAULT);
              logger.log("Status returned while adding the company : " + indexResponse
                  .status());
              logger.log("Successfully processed event to create client");
            } catch (IOException e) {
              logger.log(
                  "Exception while adding company the data to elastic : " + e.getMessage());
            }
          } else if (ClientUtils.isCompanyOfficeEvent(partitionKey, sortKey)) {
            try {
              logger.log("Entered Company Office Event");
              GetRequest requestForClient = new GetRequest(index, partitionKey);
              GetItemSpec getItemSpecForOffice = new GetItemSpec()
                  .withPrimaryKey("PK", sortKey, "SK", sortKey);
              GetResponse clientGetResponse = elasticSearchClient
                  .get(requestForClient, RequestOptions.DEFAULT);
              Item office = table.getItem(getItemSpecForOffice);
              String clientWithCompanyAndOfficeJson = ClientUtils
                  .getClientWithCompanyAndOfficeJson(clientGetResponse.getSourceAsString(), office);
              logger.log("clientWithCompanyAndOfficeJson : " + clientWithCompanyAndOfficeJson);
              UpdateRequest request = new UpdateRequest(index, partitionKey);
              request.doc(clientWithCompanyAndOfficeJson, XContentType.JSON);

              UpdateResponse updateResponse = elasticSearchClient.update(
                  request, RequestOptions.DEFAULT);
              logger.log("Successfully processed event to add office to company, Status : "
                  + updateResponse.toString());
            } catch (IOException e) {
              logger.log("Caught Exception while adding office to company : " + e.getMessage());
            }
          } else if (ClientUtils.isOfficeContactEvent(partitionKey, sortKey)) {
            try {
              logger.log("Entered Office Contact Event");

              GetItemSpec getItemSpecForContact = new GetItemSpec()
                  .withPrimaryKey("PK", sortKey, "SK", sortKey);
              Item contact = table.getItem(getItemSpecForContact);

              String clientKey = getPrimaryKey(partitionKey,
                  ClientUtils.COMPANY, logger).get(0);

              logger.log("Client key while adding contact : " + clientKey);

              GetRequest requestForClient = new GetRequest(index, clientKey);
              GetResponse clientGetResponse = elasticSearchClient
                  .get(requestForClient, RequestOptions.DEFAULT);

              String clientWithCompanyOfficeAndContactJson = ClientUtils
                  .getClientWithCompanyOfficeAndContactJson(clientGetResponse.getSourceAsString(),
                      partitionKey,
                      contact);
              UpdateRequest request = new UpdateRequest(index, clientKey);
              request.doc(clientWithCompanyOfficeAndContactJson, XContentType.JSON);

              UpdateResponse updateResponse = elasticSearchClient.update(
                  request, RequestOptions.DEFAULT);
              logger.log("Successfully processed event to add contact to office, Status : "
                  + updateResponse.toString());
            } catch (IOException e) {
              logger.log("Exception occured while adding contact to office : " + e.getMessage());
            }
          }

        } else if (record.getEventName().equals("MODIFY")) {

          logger.log("Inside MODIFY event");

          String partitionKey = record.getDynamodb().getNewImage().get("PK").getS();
          String sortKey = record.getDynamodb().getNewImage().get("SK").getS();

          if (ClientUtils.isCompanyEvent(partitionKey, sortKey)) {
            try {
              logger.log("Going to update the company details");
              GetRequest requestForClient = new GetRequest(index, partitionKey);
              GetResponse clientGetResponse = elasticSearchClient
                  .get(requestForClient, RequestOptions.DEFAULT);

              String toBeUpdatedDoc = ClientUtils
                  .getStringUpdatedCompany(clientGetResponse.getSourceAsString()
                      , record.getDynamodb().getNewImage());

              UpdateRequest request = new UpdateRequest(index, partitionKey);
              request.doc(toBeUpdatedDoc, XContentType.JSON);

              UpdateResponse updateResponse = elasticSearchClient.update(
                  request, RequestOptions.DEFAULT);
              logger.log(
                  "Successfully processed event to update company, Status : " + updateResponse
                      .toString());
            } catch (Exception exception) {
              logger.log("Exception occurred while updating company : " + exception.getMessage());
            }
          } else if (ClientUtils.isOfficeEvent(partitionKey, sortKey)) {
            try {
              logger.log("Going to update the office details");

              String clientKey = getPrimaryKey(partitionKey,
                  ClientUtils.COMPANY, logger).get(0);

              logger.log("Client key while adding office : " + clientKey);

              GetRequest requestForClient = new GetRequest(index, clientKey);
              GetResponse clientGetResponse = elasticSearchClient
                  .get(requestForClient, RequestOptions.DEFAULT);

              String toBeUpdatedDoc = ClientUtils
                  .getStringWithUpdatedOffice(clientGetResponse.getSourceAsString()
                      , record.getDynamodb().getNewImage());

              UpdateRequest request = new UpdateRequest(index, clientKey);
              request.doc(toBeUpdatedDoc, XContentType.JSON);

              UpdateResponse updateResponse = elasticSearchClient.update(
                  request, RequestOptions.DEFAULT);
              logger.log("Successfully processed event to update office, Status : " + updateResponse
                  .toString());
            } catch (Exception exception) {
              logger.log("Exception occurred while updating office : " + exception.getMessage());
            }
          } else if (ClientUtils.isContactEvent(partitionKey, sortKey)) {
            try {
              logger.log("Going to update the contact details");

              List<String> clientKeyList = getDocKeyFromContact(partitionKey, logger);

              for (String clientKey : clientKeyList) {
                logger.log("Client key while adding contact : " + clientKey);

                GetRequest requestForClient = new GetRequest(index, clientKey);
                GetResponse clientGetResponse = elasticSearchClient
                    .get(requestForClient, RequestOptions.DEFAULT);

                String toBeUpdatedDoc = ClientUtils
                    .getStringWithUpdatedContact(clientGetResponse.getSourceAsString()
                        , record.getDynamodb().getNewImage());

                UpdateRequest request = new UpdateRequest(index, clientKey);
                request.doc(toBeUpdatedDoc, XContentType.JSON);

                UpdateResponse updateResponse = elasticSearchClient.update(
                    request, RequestOptions.DEFAULT);
                logger.log(updateResponse.toString() + " for client key : " + clientKey);
              }
              logger.log("Successfully processed event to update contact");
            } catch (Exception exception) {
              logger.log("Exception occurred while updating contact : " + exception.getMessage());
            }
          }
        }
      }

      elasticSearchClient.close();

    } catch (IOException ioException) {
      logger.log("Exception occurred while processing lambda");
    }
    return null;
  }

  private List<String> getDocKeyFromContact(String contactPartitionKey, LambdaLogger logger) {

    List<String> officePKList = getPrimaryKey(contactPartitionKey, ClientUtils.OFFICE, logger);
    List<String> clientKeyList = new ArrayList<>();

    officePKList.stream()
        .forEach(officePK -> clientKeyList
            .add(getPrimaryKey(officePK, ClientUtils.COMPANY, logger).get(0)));

    return clientKeyList.stream().distinct().collect(Collectors.toList());
  }

  private List<String> getPrimaryKey(String partitionKey, String entity, LambdaLogger logger) {

    List<String> clientKeyList = new ArrayList<>();
    Index gsiIndex = table.getIndex("GSI1");
    HashMap<String, String> nameMap = new HashMap<>();
    nameMap.put("#SK", "SK");
    nameMap.put("#PK", "PK");
    HashMap<String, Object> valueMap = new HashMap<>();
    valueMap.put(":SK", partitionKey);
    valueMap.put(":PK", entity);

    QuerySpec querySpec = new QuerySpec()
        .withKeyConditionExpression("#SK = :SK and begins_with(#PK, :PK)").withNameMap(nameMap)
        .withValueMap(valueMap);

    ItemCollection<QueryOutcome> items = null;
    Iterator<Item> iterator = null;
    Item item = null;
    try {
      items = gsiIndex.query(querySpec);
      iterator = items.iterator();
      while (iterator.hasNext()) {
        item = iterator.next();
        clientKeyList.add(item.getString("PK"));
      }
    } catch (Exception e) {
      logger.log("Error in getCompanyPartitionKeyFromOfficePartitionKey : " + e.getMessage());
    }
    return clientKeyList;
  }
}