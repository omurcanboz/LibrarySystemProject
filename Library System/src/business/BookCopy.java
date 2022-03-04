package business;

import java.io.Serializable;

final public class BookCopy implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -841603382995556801L;
  private Book book;
  private int copyNum;
  private boolean isAvailable;

  BookCopy(Book book, int copyNum, boolean isAvailable) {
    this.book = book;
    this.copyNum = copyNum;
    this.isAvailable = isAvailable;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public int getCopyNum() {
    return copyNum;
  }

  public Book getBook() {
    return book;
  }

  public void changeAvailability() {
    isAvailable = !isAvailable;
  }

  @Override
  public boolean equals(Object ob) {
    if (ob == null)
      return false;
    if (!(ob instanceof BookCopy))
      return false;
    BookCopy copy = (BookCopy) ob;
    return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
  }

}
