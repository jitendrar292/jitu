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
public class CommunicationChannel implements Serializable {

  @JsonProperty("ADDRESS")
  private List<Address> address;

  @JsonProperty("EMAIL")
  private List<Email> email;

  @JsonProperty("PHONE")
  private List<Phone> phone;

  @JsonProperty("FAX")
  private List<Phone> fax;

  @JsonProperty("AUDIT_INFO")
  private AuditInfo auditInfo;

}
