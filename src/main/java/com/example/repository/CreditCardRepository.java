package com.example.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.model.CreditCard;

/**
 * CreditCardRepository provides CRUD operations and custom queries
 * for the CreditCard collection in MongoDB.
 * 
 * Extends MongoRepository to leverage Spring Data MongoDB support.
 */
public interface CreditCardRepository extends MongoRepository<CreditCard, String> {

    /**
     * Finds a CreditCard by its card number.
     * 
     * @param cardNumber the card number to search for
     * @return an Optional containing the CreditCard if found, or empty if not found
     */
    Optional<CreditCard> findByCardNumber(String cardNumber);

    /**
     * Deletes a CreditCard by its card number.
     * Uncomment the method signature below if you want to implement this behavior.
     * 
     * @param cardNumber the card number of the CreditCard to delete
     */
    // void deleteByCardNumber(String cardNumber);
}
