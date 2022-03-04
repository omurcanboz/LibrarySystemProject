package ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import business.Book;
import business.BookCopy;
import business.CheckoutDetails;
import business.CheckoutEntry;
import business.LibraryMember;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.DataUtility;
import utility.ObjectFactory;

public class CheckoutBookController extends Stage {
  @FXML
  private TextField coID;
  @FXML
  private TextField coISBN;
  @FXML
  private TextField overdueISBN;
  @FXML
  private TextField memberId;
  @FXML
  private TableView<CheckoutDetails> checkoutRecordTable;
  @FXML
  private TableColumn<CheckoutDetails, String> recordISBN;
  @FXML
  private TableColumn<CheckoutDetails, String> recordTitle;
  @FXML
  private TableColumn<CheckoutDetails, String> recordCopy;
  @FXML
  private TableColumn<CheckoutDetails, String> recordDue;
  @FXML
  private TableColumn<CheckoutDetails, String> recordCheckout;
  @FXML
  private TableView<CheckoutDetails> oTable;
  @FXML
  private TableColumn<CheckoutDetails, String> oTableISBN;
  @FXML
  private TableColumn<CheckoutDetails, String> oTableTitle;
  @FXML
  private TableColumn<CheckoutDetails, String> oTableCopy;
  @FXML
  private TableColumn<CheckoutDetails, String> oTableMember;
  @FXML
  private TableColumn<CheckoutDetails, String> oTableDue;

  Alert alertError = new Alert(AlertType.ERROR);
  Alert alertSuccess = new Alert(AlertType.INFORMATION);

  public void checkoutBook(ActionEvent event) {
    String id = coID.getText();
    String ISBN = coISBN.getText();
    DataAccessFacade da = new DataAccessFacade();
    HashMap<String, LibraryMember> libraryMembers = da.readMemberMap();
    HashMap<String, Book> books = da.readBooksMap();
    Book theBook = null;
    LibraryMember theMember = null;

    if (id.isEmpty() && ISBN.isEmpty()) {
      alertError.setContentText("All fields are required!");
      alertError.show();
    } else {
      theMember = DataUtility.getLibraryMember(libraryMembers, id);
      if (theMember == null) {
        alertError.setContentText("Library Member cannot be found!");
        alertError.show();
      } else {
        theBook = DataUtility.getBook(books, ISBN);
        if (theBook == null) {
          alertError.setContentText("ISBN cannot be found!");
          alertError.show();
        } else {

          BookCopy bookCopy = checkAvailabilityAndSet(theBook);
          if (bookCopy != null) {
            books.put(theBook.getIsbn(), theBook);

            CheckoutEntry entry = ObjectFactory.newCheckOutEntry(bookCopy, new Date(),
                calculateDueDate(theBook.getMaxCheckoutLength(), new Date()));
            theMember.getCheckoutRecord().getCheckoutEntries().add(entry);
            libraryMembers.put(theMember.getMemberId(), theMember);

            da.saveMembers(libraryMembers);
            da.saveBooks(books);

            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            thisStage.close();
            try {
              FXMLLoader loader = new FXMLLoader(getClass().getResource("../ShowCheckoutRecord.fxml"));
              loader.setController(this);
              Parent root = loader.load();
              Scene scene = new Scene(root);
              setScene(scene);
              setTitle("Member's Checkout Records");
              showCheckoutRecord(theMember.getCheckoutRecord().getCheckoutEntries());
              show();
            } catch (IOException e1) {
              e1.printStackTrace();
            }

          }

        }

      }

    }

  }

  public Date calculateDueDate(int dateLong, Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DAY_OF_MONTH, dateLong);
    return c.getTime();

  }

  public BookCopy checkAvailabilityAndSet(Book book) {
    BookCopy nextAvailable = book.getNextAvailableCopy();

    if (nextAvailable == null) {
      alertError.setContentText("Book copy does not available!");
      alertError.show();
    } else {
      nextAvailable.changeAvailability();
    }
    return nextAvailable;
  }

  public void printCheckoutDetails(ActionEvent event) {
    String memId = memberId.getText();
    DataAccessFacade daf = new DataAccessFacade();
    HashMap<String, LibraryMember> members = daf.readMemberMap();
    List<CheckoutEntry> list = null;

    if (memId != null || !memId.isEmpty()) {
      if (DataUtility.getLibraryMember(members, memId) != null) {
        for (Entry<String, LibraryMember> entry : members.entrySet()) {
          String entryId = entry.getKey();
          LibraryMember member = entry.getValue();
          if (entryId.equals(memId)) {
            list = member.getCheckoutRecord().getCheckoutEntries();
          }
        }

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("../ShowCheckoutRecord.fxml"));
          loader.setController(this);
          Parent root = loader.load();
          Scene scene = new Scene(root);
          setScene(scene);
          setTitle("Member's Checkout Record Details");
          showCheckoutRecord(list);
          show();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }

    }



  }

  public void showCheckoutRecord(List<CheckoutEntry> entries) {
    recordISBN.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("isbn"));
    recordTitle.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("title"));
    recordCopy.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("copyNum"));
    recordCheckout.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("checkoutDate"));
    recordDue.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("dueDate"));
    List<CheckoutDetails> list = new ArrayList<>();
    for (CheckoutEntry entry : entries) {
      Book book = entry.getBookCopy().getBook();
      CheckoutDetails details = new CheckoutDetails(book.getIsbn(), book.getTitle(), entry.getBookCopy().getCopyNum(),
          entry.getCheckoutDate(), entry.getDueDate());
      list.add(details);
    }
    checkoutRecordTable.getItems().setAll(list);
  }

  /**
   * This method checks books, which is not available and overdue by their
   * borrowers. Iterate all members and their checkout entry records. If input
   * ISBN same with that record ISBN, then check for availability. If it is not
   * then compare its due date with today.
   * 
   * @param event
   */
  public void checkOverdueBooks(ActionEvent event) {
    oTableISBN.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("isbn"));
    oTableTitle.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("title"));
    oTableCopy.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("copyNum"));
    oTableMember.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("member"));
    oTableDue.setCellValueFactory(new PropertyValueFactory<CheckoutDetails, String>("dueDate"));

    String ISBN = overdueISBN.getText();
    DataAccessFacade daf = new DataAccessFacade();
    HashMap<String, LibraryMember> members = daf.readMemberMap();
    List<CheckoutDetails> list = new ArrayList<>();
    for (Entry<String, LibraryMember> entry : members.entrySet()) {
      LibraryMember member = entry.getValue();
      for (CheckoutEntry checkoutEntry : member.getCheckoutRecord().getCheckoutEntries()) {
        if (checkoutEntry.getBookCopy().getBook().getIsbn().equals(ISBN) && !checkoutEntry.getBookCopy().isAvailable()
            && new Date().compareTo(checkoutEntry.getDueDate()) > 0) {
          Book book = checkoutEntry.getBookCopy().getBook();
          BookCopy bookCopy = checkoutEntry.getBookCopy();
          String memberFullname = member.getFirstName() + " " + member.getLastName();
          list.add(new CheckoutDetails(book.getIsbn(), book.getTitle(), bookCopy.getCopyNum(), memberFullname,
              checkoutEntry.getDueDate()));
        }
        oTable.getItems().setAll(list);
      }
    }

  }

 

  public String abbreviateString(String input, int maxLength) {
    if (input.length() <= maxLength)
      return input;
    else
      return input.substring(0, maxLength - 2) + "..";
  }
}
