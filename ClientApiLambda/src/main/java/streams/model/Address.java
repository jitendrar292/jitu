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
public class Address implements Serializable {

  @JsonProperty("ADDRESS_TYPE")
  private AddressType addressType;

  @JsonProperty("UNIT_NUMBER")
  private String unitNumber;

  @JsonProperty("BLOCK_NUMBER")
  private String blockNumber;

  @JsonProperty("FLOOR_NUMBER")
  private String floorNumber;

  @JsonProperty("STREET_NUMBER")
  private String streetNumber;

  @JsonProperty("STREET_NAME")
  private String streetName;

  @JsonProperty("STREET_TYPE")
  private String streetType;

  @JsonProperty("ADDRESS_LINE_1")
  private String addressLine1;

  @JsonProperty("COUNTRY")
  private String country;

  @JsonProperty("COUNTRY_CODE")
  private String countryCode;

  @JsonProperty("STATE")
  private String state;

  @JsonProperty("STATE_CODE")
  private String stateCode;

  @JsonProperty("DISTRICT")
  private String district;

  @JsonProperty("DISTRICT_CODE")
  private String districtCode;

  @JsonProperty("CITY")
  private String city;

  @JsonProperty("CITY_CODE")
  private String cityCode;

  @JsonProperty("SUB_DISTRICT")
  private String subDistrict;

  @JsonProperty("SUB_DISTRICT_CODE")
  private String subDistrictCode;

  @JsonProperty("POSTAL_CODE")
  private String postalCode;

  @JsonProperty("POSTAL_CODE_EXT")
  private String postalCodeExtension;

  @JsonProperty("LATITUDE")
  private String latitude;

  @JsonProperty("LONGITUDE")
  private String longitude;

  @JsonProperty("FORMATTED_ADDRESS")
  private String formattedAddress;

  @JsonProperty("STANDARD_ADDRESS")
  private String standardAddress;

}

