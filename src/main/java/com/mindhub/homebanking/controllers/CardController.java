package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.DTOs.CreateCardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class CardController {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private ClientRepository clientRepository;

  @GetMapping("/current/cards")
  public ResponseEntity<?> getCards(Authentication authentication) {
    Client client = clientRepository.findByEmail(authentication.getName());
    List<CardDTO> cardsList = cardRepository.findByClient(client)
            .stream()
            .map(CardDTO::new)
            .collect(Collectors.toList());

    if (!cardsList.isEmpty()) {
      return new ResponseEntity<>(cardsList, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Client has no cards", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/current/create-card")
  public ResponseEntity<?> createCardForAuthenticatedClient(Authentication authentication,
                                                            @RequestBody CreateCardDTO createCardDTO) {
    try {
      Client client = clientRepository.findByEmail(authentication.getName());
      CardType cardType = CardType.valueOf(createCardDTO.getCardType().toUpperCase());
      CardColor cardColor = CardColor.valueOf(createCardDTO.getCardColor().toUpperCase());

      if (client.getCards().size() >= 3) {
        return new ResponseEntity<>("Client already has 3 cards", HttpStatus.FORBIDDEN);
      }

      boolean cardExists = client.getCards().stream()
              .anyMatch(card -> card.getCardType() == cardType && card.getCardColor() == cardColor);

      if (cardExists) {
        return new ResponseEntity<>("Client already has this card, consider to request a different one", HttpStatus.CONFLICT);
      }

      String cardNumber = Utils.generateCardNumber();
      int ccv = Utils.generateCcv();
      LocalDate fromDate = LocalDate.now();
      LocalDate thruDate = fromDate.plusYears(5);

      Card newCard = new Card(client, cardType, cardColor, cardNumber, ccv, thruDate, fromDate);
      cardRepository.save(newCard);

      return new ResponseEntity<>("Card created for authenticated client", HttpStatus.CREATED);

    } catch (IllegalArgumentException e) {

      return new ResponseEntity<>("Invalid card type or card color", HttpStatus.BAD_REQUEST);

    } catch (Exception e) {

      return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }
}
