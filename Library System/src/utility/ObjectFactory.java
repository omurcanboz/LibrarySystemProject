package utility;

import java.util.Date;
import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutEntry;
import business.LibraryMember;

public class ObjectFactory {

  public static Book newBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
    return new Book(isbn, title, maxCheckoutLength, authors);
  }

  public static Address newAddress(String street, String city, String state, String zip) {
    return new Address(street, city, state, zip);
  }

  public static Author newAuthor(String firstName, String lastName, String phoneNumber, Address address,
      String biography) {
    return new Author(firstName, lastName, phoneNumber, address, biography);
  }

  public static LibraryMember newMember(String memberId, String firstName, String lastName, String phoneNumber,
      Address address) {
    return new LibraryMember(memberId, firstName, lastName, phoneNumber, address);
  }

  public static CheckoutEntry newCheckOutEntry(BookCopy bookCopy, Date checkoutDate, Date dueDate) {
    return new CheckoutEntry(bookCopy, checkoutDate, dueDate);
  }


}
