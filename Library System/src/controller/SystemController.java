package controller;

import java.util.HashMap;

import business.ControllerInterface;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static String userId = null;
	
	public void login(String id, String password) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new Exception("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new Exception("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		userId = id;
		
	}

}
