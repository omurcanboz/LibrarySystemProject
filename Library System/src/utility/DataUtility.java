package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import business.Book;
import business.CheckoutEntry;
import business.LibraryMember;

public class DataUtility {

  public static LibraryMember getLibraryMember(HashMap<String, LibraryMember> libraryMembers, String id) {
    for (Entry<String, LibraryMember> entry : libraryMembers.entrySet()) {
      if (entry.getKey().equals(id)) {
        return entry.getValue();
      }
    }
    return null;
  }

  public static Book getBook(HashMap<String, Book> books, String isbn) {
    for (Entry<String, Book> entry : books.entrySet()) {
      if (entry.getKey().equals(isbn)) {
        return entry.getValue();
      }
    }
    return null;
  }

  public static List<CheckoutEntry> getCheckoutEntries(HashMap<String, LibraryMember> members, String memberId) {
    for (Entry<String, LibraryMember> entry : members.entrySet()) {
      String entryId = entry.getKey();
      LibraryMember member = entry.getValue();
      if (entryId.equals(memberId)) {
        return member.getCheckoutRecord().getCheckoutEntries();
      }
    }
    return new ArrayList<>();
  }

}
