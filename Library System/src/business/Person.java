package business;

import java.io.Serializable;

public class Person implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1790524684411952803L;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Address address;

  public Person(String firstName, String lastName, String phoneNumber, Address address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public Address getAddress() {
    return address;
  }

}
