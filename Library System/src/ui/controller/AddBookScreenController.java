package ui.controller;

import java.util.ArrayList;
import java.util.HashMap;

import business.Address;
import business.Author;
import business.Book;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.CharacterUtility;
import utility.DataUtility;
import utility.HelperUtility;
import utility.ObjectFactory;

public class AddBookScreenController extends Stage {
  @FXML
  private TextField SerialNum;
  @FXML
  private TextField Title;
  @FXML
  private RadioButton SevenDays;
  @FXML
  private RadioButton TwentyOneDays;
  @FXML
  private TextField NumCopies;
  @FXML
  private TextField authorName;
  @FXML
  private TextField authorLastName;
  @FXML
  private TextField authorPhone;
  @FXML
  private TextField authorCity;
  @FXML
  private TextField authorStreet;
  @FXML
  private TextField authorState;
  @FXML
  private TextField authorZip;
  @FXML
  private TextArea authorBio;

  Alert alertInfo = new Alert(AlertType.INFORMATION);
  Alert alertError = new Alert(AlertType.ERROR);
  public static String addedBook;

  public boolean addNewBook(ActionEvent event) {
    String isbn = SerialNum.getText();
    String title = Title.getText();
    int checkOutMaxLen = SevenDays.isSelected() ? 7 : 21;
    String numCopies = NumCopies.getText();


    if (isbn.isEmpty() || title.isEmpty() || numCopies.isEmpty()) {
      alertInfo.setContentText("Please fill all fields!");
      alertInfo.show();
      return false;
    }

    if (!CharacterUtility.isNumeric(numCopies)) {
      alertInfo.setContentText("Number of Copies must be numeric!");
      alertInfo.show();
      return false;
    }

    addedBook = isbn;
    DataAccessFacade da = new DataAccessFacade();
    HashMap<String, Book> books = da.readBooksMap();

    AddBookCopyController copyController = new AddBookCopyController();
    int numOfCopies = Integer.parseInt(numCopies);
    Book book = DataUtility.getBook(books, isbn);

    if (book != null) {
      alertInfo.setContentText("The ISBN number already exist!");
      alertInfo.show();
      return false;
    }

    book = ObjectFactory.newBook(isbn, title, checkOutMaxLen, new ArrayList<>());
    books.put(book.getIsbn(), book);
    da.saveBooks(books);

    return copyController.addCopiesAndSave(da, books, book, numOfCopies < 2 ? 1 : numOfCopies - 1);
  }

  public void backToLogin(ActionEvent event) {
    try {
      LoginController.showLoginScreen(event);

    } catch (Exception e) {
      alertError.setContentText(e.getMessage());
      alertError.show();
    }
  }

  public void saveAndgoToAuthorScreen(ActionEvent event) {
    try {
      if (addNewBook(event)) {
        Scene scene = HelperUtility.createScene(event, getClass(), "../AddAuthorScreen.fxml");
        setScene(scene);
        setTitle("Add Author");
        show();
      }
    } catch (Exception e) {
      alertError.setContentText(e.getMessage());
      alertError.show();
    }
  }

  public void saveAndAddAnotherAuthor(ActionEvent event) {
    try {
      if (saveAuthor()) {
        Scene scene = HelperUtility.createScene(event, getClass(), "../AddAuthorScreen.fxml");
        setScene(scene);
        setTitle("Add Author");
        show();
      }
    } catch (Exception e) {
      alertError.setContentText(e.getMessage());
      alertError.show();
    }
  }

  public boolean saveAuthor() {
    String firstName = authorName.getText();
    String lastName = authorLastName.getText();
    String phoneNumber = authorPhone.getText();
    String state = authorState.getText();
    String street = authorStreet.getText();
    String zip = authorZip.getText();
    String city = authorCity.getText();
    String biography = authorBio.getText();

    if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || street.isEmpty() || city.isEmpty()
        || state.isEmpty() || zip.isEmpty()) {
      alertInfo.setContentText("Please fill all fields!");
      alertInfo.show();
      return false;
    }

    Address address = ObjectFactory.newAddress(street, city, state, zip);
    Author author = ObjectFactory.newAuthor(firstName, lastName, phoneNumber, address, biography);

    DataAccessFacade da = new DataAccessFacade();
    HashMap<String, Book> books = da.readBooksMap();

    Book book = DataUtility.getBook(books, addedBook);
    if(book == null) {
      alertInfo.setContentText("The book does not exist!");
      alertInfo.show();
      return false;
    }
    book.getAuthors().add(author);
    books.put(book.getIsbn(), book);
    da.saveBooks(books);
    return true;
  }

  public void saveAuthorEvent(ActionEvent event) {
    try {
      if (saveAuthor()) {
        alertInfo.setContentText("Book and authors added succesfully!");
        alertInfo.show();
        alertInfo.setOnCloseRequest(e -> {
          if (alertInfo.getResult() == ButtonType.OK) {
            HelperUtility.backToMain(event);
          }
        });
      }
    } catch (Exception e) {
      alertError.setContentText(e.getMessage());
      alertError.show();
    }

  }
  
}
