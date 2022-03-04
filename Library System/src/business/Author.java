package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -1543913803529656236L;
  private String biography;

  public String getBiography() {
    return biography;
  }

  public Author(String firstName, String lastName, String phoneNumber, Address address, String biography) {
    super(firstName, lastName, phoneNumber, address);
    this.biography = biography;
  }

}
