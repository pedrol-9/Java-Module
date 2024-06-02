package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.DTOs.CreateCardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImp implements CardService {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private CardRepository cardRepository;

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

  public ResponseEntity<?> createCardForAuthenticatedClient(Authentication authentication,
                                                            @RequestBody CreateCardDTO createCardDTO) {
    // Obtener el cliente actualmente autenticado
    Client client = clientRepository.findByEmail(authentication.getName());

    // Convertir los valores de cardType y cardColor a los tipos de enumeración correspondientes
    CardType cardType = CardType.valueOf(createCardDTO.cardType().toUpperCase());
    CardColor cardColor = CardColor.valueOf(createCardDTO.cardColor().toUpperCase());

    if (client.getCards().size() >= 3) {
      return new ResponseEntity<>("Client already has 3 cards", HttpStatus.FORBIDDEN);
    }

    // Verificar si el cliente ya tiene una tarjeta del mismo tipo y color
    boolean cardExists = client.getCards().stream()
            .anyMatch(card -> card.getCardType() == cardType && card.getCardColor() == cardColor);
    if (cardExists) {
      return new ResponseEntity<>("Client already has this card, consider requesting a different one", HttpStatus.CONFLICT);
    }

    // Generar un número de tarjeta único
    String cardNumber;
    do {
      cardNumber = Utils.generateCardNumber();
    } while (cardRepository.existsByNumber(cardNumber));

    int ccv = Utils.generateCcv();
    LocalDate fromDate = LocalDate.now();
    LocalDate thruDate = fromDate.plusYears(5);

    Card newCard = new Card(client, cardType, cardColor, cardNumber, ccv, thruDate, fromDate);
    client.addCard(newCard);
    clientRepository.save(client);
    cardRepository.save(newCard);

    return new ResponseEntity<>("Card created for authenticated client", HttpStatus.CREATED);
  }
}
