package com.example.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.CreditCard;

public interface CreditCardRepository extends MongoRepository<CreditCard, String> {
    Optional<CreditCard> findByCardNumber(String cardNumber);
    //void deleteByKey(String cardNumber);
}

