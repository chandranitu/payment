package com.example.repository;

import java.util.Optional;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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

    /**
     * Finds all Transactions by their associated credit card number.
     * 
     * This method returns a List of Transactions that are associated with the provided
     * credit card number.
     * 
     * @param cardNumber the credit card number to search for
     * @return a List of Transactions associated with the given credit card number
     */
   // @Query("{ 'creditCard.cardNumber': ?0 }")
    //List<Transaction> findByCardNumber(String creditCard);
     @Query("{ 'creditCard.$id': ?0 }")
    List<Transaction> findByCreditCardRef(ObjectId creditCardId);
}
