package business;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Book implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -4437939188038213805L;
  private BookCopy[] copies;
  private List<Author> authors;
  private String isbn;
  private String title;
  private int maxCheckoutLength;

  public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
    this.isbn = isbn;
    this.title = title;
    this.maxCheckoutLength = maxCheckoutLength;
    this.authors = authors;
    copies = new BookCopy[] {new BookCopy(this, 1, true)};
  }

  public void addCopy() {
    BookCopy[] newArr = new BookCopy[copies.length + 1];
    System.arraycopy(copies, 0, newArr, 0, copies.length);
    newArr[copies.length] = new BookCopy(this, copies.length + 1, true);
    copies = newArr;
  }

  @Override
  public boolean equals(Object ob) {
    if (ob == null)
      return false;
    if (ob.getClass() != getClass())
      return false;
    Book b = (Book) ob;
    return b.isbn.equals(isbn);
  }

  public boolean isAvailable() {
    if (copies == null) {
      return false;
    }
    return Arrays.stream(copies).map(b -> b.isAvailable()).reduce(false, (x, y) -> x || y);
  }

  @Override
  public String toString() {
    return "ISBN: " + isbn + ", Max Checkout Length: " + maxCheckoutLength + ", Available: " + isAvailable();
  }

  public String getTitle() {
    return title;
  }

  public BookCopy[] getCopies() {
    return copies;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public String getIsbn() {
    return isbn;
  }

  public BookCopy getNextAvailableCopy() {
    Optional<BookCopy> optional = Arrays.stream(copies).filter(x -> x.isAvailable()).findFirst();
    return optional.isPresent() ? optional.get() : null;
  }

  //TODO
  public BookCopy getCopy(int copyNum) {
    for (BookCopy c : copies) {
      if (copyNum == c.getCopyNum()) {
        return c;
      }
    }
    return null;
  }

  public int getMaxCheckoutLength() {
    return maxCheckoutLength;
  }

}
