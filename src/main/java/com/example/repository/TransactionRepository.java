package com.example.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Transaction;

/**
 * TransactionRepository provides CRUD operations and custom queries
 * for the Transaction collection in MongoDB.
 * 
 * Extends MongoRepository to leverage Spring Data MongoDB support.
 */
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    /**
     * Finds a Transaction by its status.
     * 
     * This method will return an Optional of Transaction where the status
     * matches the provided status.
     * 
     * @param status the status of the transaction to search for
     * @return an Optional containing the matching Transaction, or empty if not found
     */
    Optional<Transaction> findByStatus(String status);

    /**
     * Finds a Transaction by its ID.
     * 
     * This method returns an Optional of Transaction based on the transaction's ID.
     * MongoDB uses the String type for document IDs.
     * 
     * @param id the ID of the transaction to search for
     * @return an Optional containing the matching Transaction, or empty if not found
     */
    Optional<Transaction> findById(String id);
}
