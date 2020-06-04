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
public class Email implements Serializable {

  @JsonProperty("EMAIL_ID")
  private String emailId;
  @JsonProperty("EMAIL_USAGE")
  private String emailUsage;

}
