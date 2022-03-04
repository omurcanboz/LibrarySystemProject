package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -9090287457119023506L;
  List<CheckoutEntry> checkoutEntries = new ArrayList<>();

  public void setCheckoutEntries(List<CheckoutEntry> checkoutEntries) {
    this.checkoutEntries = checkoutEntries;
  }

  public List<CheckoutEntry> getCheckoutEntries() {
    return checkoutEntries;
  }
}
