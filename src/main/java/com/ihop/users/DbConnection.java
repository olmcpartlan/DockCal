package com.ihop.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



/*
import java.sql.PreparedStatement;
*/


public class DbConnection {
  public DbConnection()
  {
    String driver = "com.mysql.cj.jdbc.Driver";
    String connection = "jdbc:mysql://localhost:3306/mediocrity";
    try
    {
      // Returns the Class object associated with the class or interface with the given string name, 
      // using the given class loader.
      Class.forName(driver);

      // Configure the connection with URL and login info.
      conn = DriverManager.getConnection(connection, "owen", "mcpartlan");

    } 
    catch (ClassNotFoundException | SQLException e) { }
  }

  static Connection conn;

  static Statement statement = null;
  static ResultSet res = null;

  // Only dealing with Users now, will create overloads for different types.
  public void executeInsert(User newUser) {


    // Insert the values of the new user obj.
    String sqlStatement = String.format(
          "INSERT INTO Users VALUES(%s, '%s', '%s', '%s', '%s', '%s', %s);", 
          newUser.userId, newUser.userName, newUser.password, newUser.firstName, newUser.lastName, newUser.email, 0
          );



    try {
      statement = conn.createStatement();
      statement.executeUpdate(sqlStatement);
    }

    catch (SQLException e) {
      System.out.println("SQL Exception: "  + e.getMessage());
      System.out.println("SQL State: "      + e.getSQLState());
    }

}


  int incrementLastId()
  {
    String sqlStatement = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";
    executeSelect(sqlStatement);

    try {

      String lastId = res.getString(1);
      int lastIdValue = Integer.parseInt(lastId);

      // Clean the open connections after reading all values.
      flushConnections();

      return lastIdValue + 1;

    }
    catch (SQLException e) { 
      print("An error occurred : " + e);
    }

    return 0;

  }

  public ResultSet executeSelect(String sqlStatement) {

    try {
      statement = conn.createStatement();
      res = statement.executeQuery(sqlStatement);

      // ResultSet overload, displays the column names and values of SQL result.
      print(res);


    }
    catch (SQLException e) {
      System.out.println("SQL Exception: "  + e.getMessage());
      System.out.println("SQL State: "      + e.getSQLState());
    }


    return res;
  }


  void flushConnections() { 
    if(res != null) {
      try {
        // Close the ResultSet from receiving any remaining data.
        res.close();
      }
      catch (SQLException e) { }
      res = null;
    }

    if(statement != null) {
      try {
        // Close the statement used to execute the query.
        statement.close();
      }
      catch (SQLException e) { }

      statement = null;
    }

    print("*** All connections have been closed. ***");

  }


  void print(String input) {
    System.out.println(input);
  }

  void print(ResultSet rs) {
    try {
      ResultSetMetaData metaData = rs.getMetaData();
      int columnCount = metaData.getColumnCount();

      // Check if the next result exists.
      while(rs.next())
      {
        // Loop Columns
        for(int i = 1; i <= columnCount; i++) {
          String columnValue = rs.getString(i);
          System.out.print(metaData.getColumnName(i) + " " + columnValue);

          if(i != columnCount) System.out.print(", ");
          System.out.println();

        }

        if(columnCount == 1) break;

      }

    }
    catch(Exception e) 
    { 
      System.out.println("PRINT EXCEPTION TYPE: \t\t"   + e.getLocalizedMessage());
      System.out.println("PRINT EXCEPTION MESSAGE: \t"  + e.getMessage());
      
    }

    
  }


}