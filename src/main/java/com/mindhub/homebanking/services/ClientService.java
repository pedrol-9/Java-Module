package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTOs.ClientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

  public ResponseEntity<List<ClientDTO>> getAllClients();

  public ResponseEntity<ClientDTO> getClient(@PathVariable Long id);
}
