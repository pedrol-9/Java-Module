//package com.mindhub.homebanking.services;
//
//import com.mindhub.homebanking.DTOs.ClientLoanDTO;
//import com.mindhub.homebanking.DTOs.NewLoanDTO;
//import com.mindhub.homebanking.models.ClientLoan;
//import com.mindhub.homebanking.services.Implementation.LoanServiceException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//
//import java.util.List;
//
//public interface LoanService {
//
//  ResponseEntity<?> getLoansByClient(Authentication authentication);
//
//  ResponseEntity<?> createLoanForAuthenticatedClient(Authentication authentication, NewLoanDTO newLoanDTO) throws LoanServiceException;
//}
