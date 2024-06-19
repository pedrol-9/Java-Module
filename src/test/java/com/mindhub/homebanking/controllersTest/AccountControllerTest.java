/*
package com.mindhub.homebanking.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

   // test de getByNumber
  @Test
  public void accountNumberIsNotNull() {
    String accountNumber = Utils.generateAccountNumber();
    assertThat(accountNumber, is(notNullValue()));
  }
}*/
