package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -4475526913447776715L;
  private String memberId;
  private CheckoutRecord checkoutRecord;

  public LibraryMember(String memberId, String firstName, String lastName, String phoneNumber, Address add) {
    super(firstName, lastName, phoneNumber, add);
    this.memberId = memberId;
    this.setCheckoutRecord(new CheckoutRecord());
  }

  public String getMemberId() {
    return memberId;
  }

  @Override
  public String toString() {
    return "Member Id: " + memberId + " , Name: " + getFirstName() + " " + getLastName() + " , Phone Number "
        + getPhoneNumber() + " , Address: " + getAddress();
  }

  public CheckoutRecord getCheckoutRecord() {
    return checkoutRecord;
  }


  public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
    this.checkoutRecord = checkoutRecord;
  }

}
