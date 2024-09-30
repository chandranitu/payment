package com.example.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
	//Optional<Transaction> findByTransaction(String transaction);
	Optional<Transaction> findByStatus(String status);

    // You can also find by other fields in your entity
    Optional<Transaction> findById(String id);
}


