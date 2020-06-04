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
public class Phone implements Serializable {

  @JsonProperty("PHONE_TYPE")
  private PhoneType phoneType;

  @JsonProperty("LOCAL_AREA_CODE")
  private String localAreaCode;

  @JsonProperty("PHONE_NUMBER")
  private String phoneNumber;

  @JsonProperty("PHONE_NUMBER_EXT")
  private String phoneNumberExt;

  @JsonProperty("FULL_PHONE_NUMBER")
  private String fullPhoneNumber;


}
