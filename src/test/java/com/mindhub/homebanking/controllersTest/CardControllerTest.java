package com.mindhub.homebanking.controllersTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.DTOs.LoginDTO;
import com.mindhub.homebanking.services.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CardService cardService;

  private String jwtToken;

  @BeforeEach
  public void setUp() throws Exception {
    jwtToken = mockMvc.perform(
            post("/api/auth/login")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(new LoginDTO("melba@mindhub.com", "123")))
            )
            .andReturn()
            .getResponse()
            .getContentAsString();
  }

  @Test
  public void canGetCards() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
    mockMvc.perform(
            get("/api/clients/current/cards")
            .headers(headers))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void canGetCardsByType() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
    mockMvc.perform(
            get("/api/clients/current/cards?type=DEBIT")
            .headers(headers))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void canGetCardsByColor() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
    mockMvc.perform(
            get("/api/clients/current/cards?color=PLATINUM")
            .headers(headers))
            .andDo(print())
            .andExpect(status().isOk());
  }

}
