package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

  ResponseEntity<List<ClientDTO>> getAllClients();

  ResponseEntity<ClientDTO> getClient(@PathVariable Long id);

  List<ClientDTO> getListClientsDTO();

  Client getOptionalClientById(Long id);

  Client getActualClient(Authentication authentication);

  void saveClient(Client client);
}
