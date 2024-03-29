package business;

import java.io.Serializable;
import java.util.Date;

public class CheckoutEntry implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -110278488455409831L;
  private BookCopy bookCopy;
  private Date checkoutDate;
  private Date dueDate;

  public CheckoutEntry(BookCopy bookCopy, Date checkoutDate, Date dueDate) {
    this.bookCopy = bookCopy;
    this.checkoutDate = checkoutDate;
    this.dueDate = dueDate;
  }

  public Date getCheckoutDate() {
    return checkoutDate;
  }

  public void setCheckoutDate(Date checkoutDate) {
    this.checkoutDate = checkoutDate;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public BookCopy getBookCopy() {
    return bookCopy;
  }

  public void setBookCopy(BookCopy bookCopy) {
    this.bookCopy = bookCopy;
  }


}
