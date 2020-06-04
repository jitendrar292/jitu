package streams.model;

public enum PhoneType {

  HOME("home"),
  MOBILE("mobile"),
  WORK("work");


  PhoneType(String phoneType) {
    this.phoneType = phoneType;
  }

  public String phoneType;

  public String getPhoneType() {
    return this.phoneType;
  }

}
