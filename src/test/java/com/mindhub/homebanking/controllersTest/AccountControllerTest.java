package com.mindhub.homebanking.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

//  @Test
//  public void canCreateAccountOk() throws Exception {
//
//    Account account = new Account();
//    mockMvc.perform(
//            post("/api/clients/current/accounts")
//                    .contentType("application/json")
//                    .content(
//                            objectMapper.writeValueAsString(account)
//                    )
//    )
//            .andDo(print())
//            .andExpect(status().isCreated());
//  }

//  @Test
//  public void accountExistsGreen() {
//    //Generamos un número de cuenta aleatorio
//    String accountNumber = NumberAccount.eightDigits();
//
//    // Creamos y guardamos una nueva cuenta con ese número, fecha de hoy y saldo inicial de 1000.0
//    Account account = new Account(accountNumber, LocalDate.now(), 1000.0);
//    accountRepository.save(account);
//
//    // Comprobamos si la cuenta con el número generado existe en la base de datos
//    boolean accountExists = accountRepository.existsByNumber(accountNumber);
//
//    //Verificamos que la cuenta realmente exista
//    assertThat(accountExists, is(true));
//  }




}
