package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.CreateCardDTO;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class CardController {

  @Autowired
  private CardService cardService;

  @GetMapping("/current/cards")
  public ResponseEntity<?> getCards(Authentication authentication) {
    return cardService.getCards(authentication);
  }

  @PostMapping("/current/cards")
  public ResponseEntity<?> createCardForAuthenticatedClient(Authentication authentication,
                                                            @RequestBody CreateCardDTO createCardDTO) {
      return cardService.createCardForAuthenticatedClient(authentication, createCardDTO);
  }
}
