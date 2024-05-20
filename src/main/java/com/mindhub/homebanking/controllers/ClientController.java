package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin( origins = "*" )
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clientDTOs = clientRepository.findAll().stream().map(ClientDTO::new)
                .collect(toList());

        return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClients(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElse(null);

        if (client == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        ClientDTO clientDTO = new ClientDTO(client);

        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }
}
