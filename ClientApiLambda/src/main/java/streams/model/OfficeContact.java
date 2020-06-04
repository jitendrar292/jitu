package streams.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OfficeContact implements Serializable {


  @JsonProperty("PK")
  private String officeContactPK;
  @JsonProperty("SK")
  private String officeContactSK;
  @JsonProperty("SNAME")
  private String searchName;
  @JsonProperty("OFFICE_NAME")
  private String officeName;
  @JsonProperty("IS_OFFICE_ACTIVE")
  private Boolean isOfficeActive;
  @JsonProperty("IS_CONTACT_ACTIVE")
  private Boolean isContactActive;
  @JsonProperty("CONTACT_NAME")
  private String contactName;

}
