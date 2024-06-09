package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.DTOs.LoginDTO;
import com.mindhub.homebanking.DTOs.RegisterDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.servicesSecurity.JwtUtilService;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);
            return ResponseEntity.ok(jwt);
        } catch (Exception e){
            return new ResponseEntity<>("Email or password invalid", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDTO registerDTO){

        if (registerDTO.firstName().isBlank()) {
            return  new ResponseEntity<>("The first name field can't be empty", HttpStatus.FORBIDDEN);
        }

        if (registerDTO.lastName().isBlank()) {
            return  new ResponseEntity<>("The last name field can't be empty", HttpStatus.FORBIDDEN);
        }

        if (registerDTO.email().isBlank()) {
            return  new ResponseEntity<>("The email field can't be empty", HttpStatus.FORBIDDEN);
        }

        //valida que el email del nuevo cliente no exista en la base de datos
        if(clientService.getClientByEmail(registerDTO.email()) != null){
            return  new ResponseEntity<>("The email already exists", HttpStatus.FORBIDDEN);
        }

        //validar que el email contentga @ y.com
        if (!registerDTO.email().contains("@") || !registerDTO.email().contains(".com")) {
            return  new ResponseEntity<>("The email must contain @ and .com", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password()));
        clientService.saveClient(client);

        String accountNumber = Utils.generateAccountNumber();
        Account account = new Account(accountNumber, LocalDate.now(), 0.0);
        account.setClient(client); // es necesaria esta línea?
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Client created", HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication) {
        Client client = clientService.getActualClient(authentication);
        return ResponseEntity.ok(new ClientDTO(client));
    }
}