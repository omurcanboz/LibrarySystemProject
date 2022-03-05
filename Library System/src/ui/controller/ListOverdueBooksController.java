package ui.controller;

import business.*;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.*;
import java.util.Map.Entry;

public class ListOverdueBooksController extends Stage {
  @FXML
  private TableView<OverdueDetails> ovTable;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableISBN;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableTitle;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableMemberID;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableMemberName;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableMemberLastName;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableCheck;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableDue;
  @FXML
  private TableColumn<OverdueDetails, String> ovTableLateFee;

  public void listAllOverdueBooks(ActionEvent event) {
    ovTableISBN.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("isbn"));
    ovTableTitle.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("title"));
    ovTableMemberID.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("memberId"));
    ovTableMemberName.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("memberName"));
    ovTableMemberLastName.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("memberLastName"));
    ovTableCheck.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("checkoutDate"));
    ovTableDue.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("dueDate"));
    ovTableLateFee.setCellValueFactory(new PropertyValueFactory<OverdueDetails, String>("lateFee"));

    DataAccessFacade daf = new DataAccessFacade();
    HashMap<String, LibraryMember> members = daf.readMemberMap();
    List<OverdueDetails> list = new ArrayList<>();
    for (Entry<String, LibraryMember> entry : members.entrySet()) {
      LibraryMember member = entry.getValue();
      for (CheckoutEntry checkoutEntry : member.getCheckoutRecord().getCheckoutEntries()) {
        if (!checkoutEntry.getBookCopy().isAvailable() && new Date().compareTo(checkoutEntry.getDueDate()) > 0) {
          Book book = checkoutEntry.getBookCopy().getBook();
          BookCopy bookCopy = checkoutEntry.getBookCopy();
          list.add(new OverdueDetails(book.getIsbn(), book.getTitle(), member.getMemberId(), member.getFirstName(),
              member.getLastName(), checkoutEntry.getCheckoutDate(), checkoutEntry.getDueDate()));
        }
        ovTable.getItems().setAll(list);
      }
    }
  }

}
