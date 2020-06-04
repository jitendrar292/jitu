package streams.model;

public enum AddressType {

  PRIMARY("primary"),
  MAILING("mailing"),
  SOLDTO("soldto"),
  BILLTO("billto"),
  WORK("work"),
  PERMANENT("permanent"),
  FOREIGN("foreign"),
  CO("c/o");


  AddressType(String addressType) {
    this.addressType = addressType;
  }

  public String addressType;

  public String getAddressType() {
    return this.addressType;
  }

}
