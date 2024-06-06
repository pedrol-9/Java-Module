package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.DTOs.CreateCardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CardService {

  ResponseEntity<?> getCards(Authentication authentication);

  ResponseEntity<?> createCardForAuthenticatedClient(Authentication authentication,
                                                     @RequestBody CreateCardDTO createCardDTO);

  Client getAuthenticatedClientByEmail(Authentication authentication);

  List<CardDTO> getCardsByAuthenticatedClient(Client client);

  boolean existsByNumber(String cardNumber);

  void saveCard(Card card);

}
