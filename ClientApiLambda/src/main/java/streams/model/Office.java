package streams.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Office implements Serializable {

  @JsonProperty("PK")
  private String officePK;

  @JsonProperty("SK")
  private String officeSK;

  @JsonProperty("SNAME")
  private String searchName;

  @JsonProperty("OFFICE_NAME")
  private String officeName;

  @JsonProperty("OFFICE_DESC")
  private String officeDesc;

  @JsonProperty("BUSINESS_TYPE")
  private String businessType;

  @JsonProperty("OTHER_CATEGORY")
  private String otherCategory;

  @JsonProperty("IS_ACTIVE")
  private Boolean isActive;

  @JsonProperty("IS_PENDING_VERIFICATION")
  private Boolean isPendingVerification;

  @JsonProperty("COMPANY_CODE")
  private String companyCode;

  @JsonProperty("IS_PRIMARY_OFFICE")
  private Boolean isPrimaryOffice;

  @JsonProperty("COMMUNICATION_CHANNEL")
  private CommunicationChannel communicationChannel;

  @JsonProperty("AUDIT_INFO")
  private AuditInfo auditInfo;

  @JsonProperty("CONTACTS")
  private List<Contact> contacts;

}
