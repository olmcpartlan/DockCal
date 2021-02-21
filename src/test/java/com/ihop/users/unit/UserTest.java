package com.ihop.mediocrity.users;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.ihop.users.*;
import org.junit.Test;

public class UserTest {
  @Test
  public void userTests()
  {
    /*
    System.out.println(user.getFullName());
    */

    User user = new User("", "Str0ngP@ssw0rd!", "David", "Wallace", "david@dm.com");

    /*

    DbConnection context = new DbConnection();
    context.executeInsert(user);
    context.executeSelect("SELECT * FROM users;"); 

    */


    // The method responsible for creating the new UserId will return 0 if there was 
    // a problem getting the last ID From DB.
    assertThat(user.userId, not(0));

    // Make sure the user name is being created if not provided.
    assertNotEquals("", user.userName);

    // Simple validation for user email, could expand here.
    assertThat(user.email, containsString("@"));



  }

}