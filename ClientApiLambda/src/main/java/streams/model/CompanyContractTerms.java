package streams.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyContractTerms implements Serializable {

  @JsonProperty("IS_STANDARD_CONTRACT_TERMS")
  private Boolean isStandardContactTerms;
  @JsonProperty("RELIENCE_APPROVED_DATE")
  private Timestamp relianceApprovedDate;
  @JsonProperty("RELIANCE_LANGUAGE_PARTIES")
  private List<String> relianceLanguageParties;
  @JsonProperty("AUDIT_INFO")
  private AuditInfo auditInfo;
  @JsonProperty("OFFICES")
  private List<Office> Offices;
}
