package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class ClientTest {

  @Test
  public void canAddCardClient() {
    Client client = new Client("Pedro", "Sanabria", "pedro@sanabria.com", "12345**");

    Card newCard = new Card();
    newCard.setClient(client);
    newCard.setCardColor(CardColor.GOLD);
    newCard.setCardType(CardType.DEBIT);
    newCard.setNumber(Utils.generateCardNumber());
    newCard.setCcv(123);
    newCard.setThruDate(LocalDate.now());
    newCard.setFromDate(LocalDate.now());

    client.addCard(newCard);

    assert (client.getCards().size() == 1);
  }
}
