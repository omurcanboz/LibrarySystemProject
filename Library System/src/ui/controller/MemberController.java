package ui.controller;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.ObjectFactory;

public class MemberController extends Stage {
  @FXML
  private TextField firstName;
  @FXML
  private TextField lastName;
  @FXML
  private TextField telephone;
  @FXML
  private TextField city;
  @FXML
  private TextField street;
  @FXML
  private TextField state;
  @FXML
  private TextField zip;
  @FXML
  private Button backButton;
  @FXML
  private Button submitButton;

  Alert infoAlert = new Alert(AlertType.INFORMATION);

  public boolean addNewMember(ActionEvent event) {
    if (firstName.getText().equals("") || lastName.getText().equals("") || telephone.getText().equals("")
        || street.getText().equals("") || city.getText().equals("") || state.getText().equals("")
        || zip.getText().equals("")) {
      infoAlert.setContentText("Please fill all fields!");
      infoAlert.show();
      return false;
    }
    DataAccessFacade daf = new DataAccessFacade();
    int memberNum = daf.readMemberMap().size() + 1001;
    Address address = ObjectFactory.newAddress(street.getText(), city.getText(), state.getText(), zip.getText());
    LibraryMember member = ObjectFactory.newMember(String.valueOf(memberNum), firstName.getText(), lastName.getText(),
        telephone.getText(), address);
    DataAccess da = new DataAccessFacade();
    da.saveNewMember(member);
    infoAlert.setContentText("Member added succesfully!");
    infoAlert.show();
    infoAlert.setOnCloseRequest(e -> {
      if (infoAlert.getResult() == ButtonType.OK) {
        clear();
      }
    });
    return true;
  }

  public void backToLogin(ActionEvent event) {
    try {
      LoginController.showLoginScreen(event);

    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  public void clear() {
    firstName.clear();
    lastName.clear();
    telephone.clear();
    street.clear();
    city.clear();
    state.clear();
    zip.clear();
  }

}
