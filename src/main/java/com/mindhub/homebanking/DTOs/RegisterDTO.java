package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.Account;

public record RegisterDTO (String firstName, String lastName, String email, String password){
}
