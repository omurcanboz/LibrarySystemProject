package utility;

public class CharacterUtility {

  public static boolean isNumeric(String str) {
    if (str.isEmpty()) {
      return false;
    }
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;

  }
  
}
