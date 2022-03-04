package ui.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import business.Book;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utility.CharacterUtility;
import utility.DataUtility;

public class AddBookCopyController extends Stage{
	@FXML
	private TextField serialNum;
	
	@FXML
	private TextField copyNumbers;
	
	Alert alertError = new Alert(AlertType.ERROR);
	Alert alertSuccess = new Alert(AlertType.INFORMATION);
	
	public void addBookCopy(ActionEvent event) {
		String isbnNum = serialNum.getText();
		String copyNum = copyNumbers.getText();
		
		if(isbnNum == null && copyNum == null) {
        alertError.setContentText("All fields are required!");
        alertError.show();
		} else {
		  DataAccessFacade da = new DataAccessFacade();
	    HashMap<String, Book> books = da.readBooksMap();
		  if(DataUtility.getBook(books, isbnNum) == null) {
		    alertError.setContentText("The book does not exist!");
        alertError.show();
		  } else {
		    if(!CharacterUtility.isNumeric(copyNum)) {
	        alertError.setContentText("Copy Number must be numeric!");
	        alertError.show();
	      } else {
	        Integer bookCopies = Integer.parseInt(copyNumbers.getText());
	        if(addCopiesToBookAndSave(da, books, DataUtility.getBook(books, isbnNum), bookCopies)) {
	          alertSuccess.setContentText("Book copies added succesfully!");
	          alertSuccess.show();
	          alertSuccess.setOnCloseRequest( e -> {
	                    if (alertSuccess.getResult() == ButtonType.OK) {
	                    	backToLogin(event);
	                    }});
	        } else {
	          alertError.setContentText("Book copy not added! Problem occured!");
	          alertError.show();
	        }
	      }
		  }
		  
		}
		
	}
	
	public boolean addCopiesToBookAndSave(DataAccessFacade da, HashMap<String, Book> books, Book book, int copies) {
		for (int i = 0; i < copies; i++) {
      book.addCopy();
    }
    books.put(book.getIsbn(), book);
    da.saveBooks(books);
    return true;
	}
	
	public void backToLogin(ActionEvent event) {
		try {
			LoginController.showLoginScreen(event);
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
 	
}
