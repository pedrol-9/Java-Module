package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImp implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public ResponseEntity<List<ClientDTO>> getAllClients(){
    List<ClientDTO> clientDTOs = clientRepository.findAll().stream().map(ClientDTO::new)
            .collect(toList());

    return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
  }


  @Override
  public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
    Client client = clientRepository.findById(id).orElse(null);

    if (client == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    ClientDTO clientDTO = new ClientDTO(client);

    return new ResponseEntity<>(clientDTO, HttpStatus.OK);
  }
}
