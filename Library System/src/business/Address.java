package business;

import java.io.Serializable;

final public class Address implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1536914921728904768L;
  private String street;
  private String city;
  private String state;
  private String zip;

  public Address(String street, String city, String state, String zip) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  @Override
  public String toString() {
    return "(" + street + ", " + city + ", " + zip + ", " + state + ")";

  }
}
