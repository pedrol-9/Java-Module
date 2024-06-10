package com.mindhub.homebanking.RepositoriesTest;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRepositoryTest {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Test
  public void existCards() {
    List<Card> cards = cardRepository.findAll();
    assertThat(cards, is(not(empty())));
  }

  @Test
  public void existDebitCards() {
    List<Card> cards = cardRepository.findAll();
    assertThat(cards, hasItem(hasProperty("cardType", is(CardType.DEBIT))));
  }


  @Test
  public void existGoldCards() {
    List<Card> cards = cardRepository.findAll();
    assertThat(cards, hasItem(hasProperty("cardColor", is(CardColor.GOLD))));
  }

  @Test
  public void shouldExistCardByNumberInDatabase () {
    String cardNumber = Utils.generateCardNumber();

    Client client = new Client("Pedro", "Sanabria", "pedro@sanabria.com", "12345**");
    Card card = new Card();

    card.setCardColor(CardColor.GOLD);
    card.setCardType(CardType.DEBIT);
    card.setCcv(123);
    card.setClient(client);
    card.setFromDate(LocalDate.now());
    card.setNumber(cardNumber);
    card.setThruDate(LocalDate.now().plusYears(5));

    clientRepository.save(client);
    cardRepository.save(card);

    boolean existsCardByNumber = cardRepository.existsByNumber(cardNumber);

    assertThat(existsCardByNumber, is(true));
  }

  @Test
  public void shouldFindCardByClientInRepository() {
    Client client = new Client("Pedro", "Sanabria", "pedro@sanabria.com", "12345**");
    Card card = new Card();

    card.setCardColor(CardColor.GOLD);
    card.setCardType(CardType.DEBIT);
    card.setCcv(123);
    card.setClient(client);
    card.setFromDate(LocalDate.now());
    card.setNumber(Utils.generateCardNumber());
    card.setThruDate(LocalDate.now().plusYears(5));

    clientRepository.save(client);
    cardRepository.save(card);

    assertThat(cardRepository.findByClient(client), contains(card));
  }
}
