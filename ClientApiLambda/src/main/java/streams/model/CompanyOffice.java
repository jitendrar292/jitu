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
public class CompanyOffice implements Serializable {

  @JsonProperty("PK")
  private String companyOfficePK;

  @JsonProperty("SK")
  private String companyOfficeSK;

  @JsonProperty("SNAME")
  private String searchName;

  @JsonProperty("OFFICE_NAME")
  private String officeName;

  @JsonProperty("IS_OFFICE_ACTIVE")
  private Boolean isOfficeActive;

  @JsonProperty("IS_COMPANY_ACTIVE")
  private Boolean isCompanyActive;

  @JsonProperty("COMPANY_NAME")
  private String companyName;


}
