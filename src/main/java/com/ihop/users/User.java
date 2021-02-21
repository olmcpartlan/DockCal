package com.ihop.users;

import org.springframework.security.crypto.bcrypt.BCrypt;


public class User {

  static DbConnection conn = new DbConnection();

  public User(String userName, String password, String firstName, String lastName, String email) {
    this.firstName  = firstName;
    this.lastName   = lastName;
    this.userName   = userName.length() == 0 ? createUserName() : userName;
    this.email      = email;
    // Get the last ID used in the DB and return id++.
    this.userId     = conn.incrementLastId();
    // Encrypt the password before it hits the DB.
    this.password   = encryptPassword(password);

  }


  // Member variables
  public int userId;
  public String userName;
  public String password;
  public String firstName;
  public String lastName;
  public String email;


  String encryptPassword(String input) {
    return BCrypt.hashpw(input, BCrypt.gensalt());

  }

  // If a user name was not provided, create one using first char and last name.
  public String createUserName() {
    return String.format("%s_%s", this.firstName.toCharArray()[0], this.lastName);
  }

  public User insertNewUser() {
    DbConnection conn = new DbConnection();
    conn.executeInsert(this);
    return this;
  }

  boolean validatePassword(String input) {
    if(BCrypt.checkpw(input, this.password)) {
      print("*** PASSWORD MATCHES");
      return true;

    }
    else {
      print("*** DOES NOT MATCH");
      return false;
    }

  }

  void print(String input) {
    System.out.println(input);
  }

    
}