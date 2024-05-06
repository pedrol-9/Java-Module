package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

public class ClientDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    Set<AccountDTO> accounts;

    // special builder
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());
    }

    // getters
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}
