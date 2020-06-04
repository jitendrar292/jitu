package streams.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Company implements Serializable {

  @JsonProperty("PK")
  private String companyPK;

  @JsonProperty("SK")
  private String companySK;

  @JsonProperty("SNAME")
  private String searchName;

  @JsonProperty("COMPANY_NAME")
  private String companyName;

  @JsonProperty("PARENT_COMPANY")
  private String parentCompany;

  @JsonProperty("IS_DIVISION")
  private Boolean isDivision;

  @JsonProperty("IS_ACTIVE")
  private Boolean isActive;

  @JsonProperty("FOREIGN_SYSTEM_IDS")
  private Map<String, String> foreignSystemIds;

  @JsonProperty("COMPANY_ATTACHMENT_DOCUMENT_IDS")
  private List<String> companyAttachmentDocumentIds;

  @JsonProperty("COMPANY_CONTACT_TERMS")
  private CompanyContractTerms companyContractTerms;

  @JsonProperty("COMMUNICATION_CHANNEL")
  private CommunicationChannel communicationChannel;

  @JsonProperty("AUDIT_INFO")
  private AuditInfo auditInfo;

  @JsonProperty("OFFICES")
  private List<Office> offices;

}
