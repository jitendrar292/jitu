package streams.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import lombok.experimental.UtilityClass;
import streams.model.Client;
import streams.model.Company;
import streams.model.Contact;
import streams.model.Office;

@UtilityClass
public class ClientUtils {

  public static final String CONTACT = "CONTACT";
  public static final String OFFICE = "OFFICE";
  public static final String COMPANY = "COMPANY";

  ObjectMapper objectMapper = new ObjectMapper();

  private LambdaLogger logger;

  public static boolean isCompanyEvent(String partitionKey, String sortKey) {
    return partitionKey.contains(COMPANY) && sortKey.contains(COMPANY);
  }

  public static boolean isOfficeEvent(String partitionKey, String sortKey) {
    return partitionKey.contains(OFFICE) && sortKey.contains(OFFICE);
  }

  public static boolean isContactEvent(String partitionKey, String sortKey) {
    return partitionKey.contains(CONTACT) && sortKey.contains(CONTACT);
  }

  public static boolean isCompanyOfficeEvent(String partitionKey, String sortKey) {
    return partitionKey.contains(COMPANY) && sortKey.contains(OFFICE);
  }

  public static boolean isOfficeContactEvent(String partitionKey, String sortKey) {
    return partitionKey.contains(OFFICE) && sortKey.contains(CONTACT);
  }

  public String getJsonString(Map<String, AttributeValue> newImage) {
    String json = null;
    List<Map<String, AttributeValue>> listOfMaps = new ArrayList<>();
    listOfMaps.add(newImage);
    List<Item> itemList = ItemUtils.toItemList(listOfMaps);
    for (Item item : itemList) {
      json = item.toJSON();
    }
    return json;
  }

  public String getClientJson(Map<String, AttributeValue> newImage) {
    Client client = new Client();
    Company company;
    String clientJson = null;
    List<Map<String, AttributeValue>> listOfMaps = new ArrayList<>();
    listOfMaps.add(newImage);
    List<Item> itemList = ItemUtils.toItemList(listOfMaps);
    try {
      for (Item item : itemList) {
        company = objectMapper.readValue(item.toJSON(), Company.class);
        client.setCompany(company);
        clientJson = objectMapper.writeValueAsString(client);
      }
    } catch (Exception ex) {
      logger.log("Error occured while creating client json : " + ex.getMessage());
    }
    return clientJson;
  }

  public static String getClientWithCompanyAndOfficeJson(String clientAsString, Item officeItem) {
    String clientWithCompanyAndOfficesJson = null;
    try {
      Client client = getClientFromString(clientAsString);
      if (client != null) {
        Office office = objectMapper.readValue(officeItem.toJSON(), Office.class);
        List<Office> offices = client.getCompany().getOffices();
        if (null == offices) {
          offices = new ArrayList<>();
          offices.add(office);
        } else {
          offices.add(office);
        }
        client.getCompany().setOffices(offices);
        clientWithCompanyAndOfficesJson = objectMapper.writeValueAsString(client);
      }
    } catch (Exception exception) {
      logger.log(
          "Caught an exception in getClientWithCompanyAndOfficeJson: " + exception.getMessage());
    }
    return clientWithCompanyAndOfficesJson;
  }

  public static String getClientWithCompanyOfficeAndContactJson(String clientAsString,
      String partitionKey, Item contactItem) {

    String clientWithCompanyOfficesAndContactJson = null;
    try {
      Client client = getClientFromString(clientAsString);
      Contact contact = objectMapper.readValue(contactItem.toJSON(), Contact.class);

      if (client != null) {

        for (Office office : client.getCompany().getOffices()) {
          if (office.getOfficePK().equals(partitionKey)) {
            List<Contact> contacts = office.getContacts();
            if (null == contacts) {
              contacts = new ArrayList<>();
              contacts.add(contact);
            } else {
              contacts.add(contact);
            }
            office.setContacts(contacts);
            break;
          }
        }
      }
      clientWithCompanyOfficesAndContactJson = objectMapper.writeValueAsString(client);
    } catch (Exception exception) {
      logger.log(
          "Caught an exception in getClientWithCompanyOfficeAndContactJson: " + exception
              .getMessage());
    }
    return clientWithCompanyOfficesAndContactJson;

  }

  public static Client getClientFromString(String clientAsString) {
    Client client = null;
    try {
      client = objectMapper.readValue(clientAsString, Client.class);
    } catch (IOException e) {
      logger.log("Exception occured in getClientFromString");
    }
    return client;
  }

  public static String getStringUpdatedCompany(String clientAsString,
      Map<String, AttributeValue> updatedCompanyImage) {

    String toBeUpdatedDoc = null;

    try {
      Client client = getClientFromString(clientAsString);
      if (client != null) {
        Company company = objectMapper.readValue(getJsonString(updatedCompanyImage), Company.class);

        if (client.getCompany() != null && client.getCompany().getOffices() != null) {
          company.setOffices(client.getCompany().getOffices());
          client.setCompany(company);
        }
        toBeUpdatedDoc = objectMapper.writeValueAsString(client);
      }
    } catch (Exception exception) {
      logger.log(
          "Caught an exception in getStringUpdatedCompany: " + exception.getMessage());
    }
    return toBeUpdatedDoc;
  }

  public static String getStringWithUpdatedOffice(String clientAsString,
      Map<String, AttributeValue> updatedOfficeImage) {

    String toBeUpdatedDoc = null;
    try {
      Client client = getClientFromString(clientAsString);
      if (client != null) {
        Office updatedOffice = objectMapper
            .readValue(getJsonString(updatedOfficeImage), Office.class);
        AtomicReference<Office> toBeDeletedOffice = new AtomicReference<>();

        client.getCompany().getOffices().stream().forEach(office -> {
          if (office.getOfficePK().equals(updatedOffice.getOfficePK())) {
            toBeDeletedOffice.set(office);
            updatedOffice.setContacts(office.getContacts());
          }
        });
        client.getCompany().getOffices().remove(toBeDeletedOffice.get());
        client.getCompany().getOffices().add(updatedOffice);
        toBeUpdatedDoc = objectMapper.writeValueAsString(client);
      }
    } catch (Exception exception) {
      logger.log(
          "Caught an exception in getStringWithUpdatedOffice: " + exception.getMessage());
    }
    return toBeUpdatedDoc;

  }

  public static String getStringWithUpdatedContact(String clientAsString,
      Map<String, AttributeValue> updatedContactImage) {

    String toBeUpdatedDoc = null;

    try {
      Client client = getClientFromString(clientAsString);

      if (client != null) {
        Contact updatedContact = objectMapper
            .readValue(getJsonString(updatedContactImage), Contact.class);

        List<Office> officesList = client.getCompany().getOffices();

        for (Office office : officesList) {
          List<Contact> contacts = new ArrayList<>();
          for (Contact contact : office.getContacts()) {
            if (contact.getContactPK().equals(updatedContact.getContactPK())) {
              contacts.add(updatedContact);
            } else {
              contacts.add(contact);
            }
          }
          office.setContacts(contacts);
        }

        client.getCompany().setOffices(officesList);

        toBeUpdatedDoc = objectMapper.writeValueAsString(client);
      }
    } catch (Exception exception) {
      logger.log(
          "Caught an exception in getStringWithUpdatedContact: " + exception.getMessage());
    }
    return toBeUpdatedDoc;
  }

  public static void setLogger(LambdaLogger logger) {
    ClientUtils.logger = logger;
  }

}
