package streams.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuditInfo {

  @JsonProperty("CREATED_BY")
  private String createdBy;
  @JsonProperty("UPDATED_BY")
  private String updatedBy;
  @JsonProperty("CREATED_DATE")
  private Timestamp createdDate;
  @JsonProperty("UPDATED_DATE")
  private Timestamp updatedDate;
  @JsonProperty("CHANGE_DESC")
  private String changeDesc;

}
