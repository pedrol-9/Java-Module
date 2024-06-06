package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    /* List<ClientDTO> clientDTOs = clientRepository.findAll().stream().map(ClientDTO::new)
            .collect(toList()); */

    List<ClientDTO> clientDTOs = getListClientsDTO();

    return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
    Client client = getOptionalClientById(id);

    if (client == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    ClientDTO clientDTO = new ClientDTO(client);

    return new ResponseEntity<>(clientDTO, HttpStatus.OK);
  }

  @Override
  public List<ClientDTO> getListClientsDTO() {
    return clientRepository.findAll().stream().map(ClientDTO::new)
            .collect(toList());
  }

  @Override
  public Client getOptionalClientById(Long id) {
    return clientRepository.findById(id).orElse(null);
  }

  @Override
  public Client getActualClient(Authentication authentication) {
    return clientRepository.findByEmail(authentication.getName());
  }

  @Override
  public void saveClient(Client client) {
    clientRepository.save(client);
  }

}
