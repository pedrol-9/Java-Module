package com.mindhub.homebanking.controllersTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.DTOs.LoginDTO;
import com.mindhub.homebanking.DTOs.RegisterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Estas anotaciones son usadas para integration tests
@SpringBootTest
@AutoConfigureMockMvc
public class ClienControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void canRegisterOk() throws Exception {
    RegisterDTO newUser = new RegisterDTO("Pedro", "Sanabria", "pedro@sanabria.com", "12345**");

    mockMvc.perform(
            post("/api/auth/register")
                    .contentType("application/json")
                    .content(
                            objectMapper.writeValueAsString(newUser)
                    )
            )
            .andDo(print())
            .andExpect(status().isCreated());
  }

  @Test
  public void canLoginOk() throws Exception {

    LoginDTO user = new LoginDTO("melba@mindhub.com", "123");
    mockMvc.perform(
            post("/api/auth/login")
                    .contentType("application/json")
                    .content(
                            objectMapper.writeValueAsString(user)
                    )
            )
            .andDo(print())
            .andExpect(status().isOk());
  }
}

