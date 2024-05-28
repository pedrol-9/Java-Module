package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRespository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByAccountClientId(long id);
}
