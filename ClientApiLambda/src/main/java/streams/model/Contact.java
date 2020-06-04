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
public class Contact implements Serializable {

  @JsonProperty("PK")
  private String contactPK;

  @JsonProperty("SK")
  private String contactSK;

  @JsonProperty("SNAME")
  private String searchName;

  @JsonProperty("SALUTATION")
  private String salutation;

  @JsonProperty("TITLE")
  private String title;

  @JsonProperty("FIRST_NAME")
  private String firstName;

  @JsonProperty("MIDDLE_NAME")
  private String middleName;

  @JsonProperty("LAST_NAME")
  private String lastName;

  @JsonProperty("FULL_NAME")
  private String fullName;

  @JsonProperty("IS_ACTIVE")
  private Boolean isActive;

  @JsonProperty("IS_PENDING_VERIFICATION")
  private Boolean isPendingVerification;

  @JsonProperty("NOTES")
  private List<String> notes;

  @JsonProperty("COMMUNICATION_CHANNEL")
  private CommunicationChannel communicationChannel;

  @JsonProperty("AUDIT_INFO")
  private AuditInfo auditInfo;


}
