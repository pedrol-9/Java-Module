/*
package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UtilsTests {

  @Test
  public void cardNumberIsCreated(){
    String cardNumber = Utils.generateCardNumber();
    assertThat(cardNumber,is(not(emptyOrNullString())));
  } // ok

  @Test
  public void ccvIsCreated(){
    int ccv = Utils.generateCcv();
    assertThat(ccv,is(greaterThan(100)));
  } // ok

  @Test
  public void accountNumberIsCreated(){
    String accountNumber = Utils.generateAccountNumber();
    assertThat(accountNumber,is(not(emptyOrNullString())));
  } // ok
}
*/
