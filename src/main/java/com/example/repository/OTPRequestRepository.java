package com.example.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.OTPRequest;

/**
 * OTPRequestRepository provides CRUD operations and custom queries
 * for the OTPRequest collection in MongoDB.
 * 
 * Extends MongoRepository to leverage Spring Data MongoDB support.
 */
@Repository
public interface OTPRequestRepository extends MongoRepository<OTPRequest, String> {

    /**
     * Finds an OTPRequest by its OTP value, associated CreditCard ID, 
     * and ensures that the OTP has not expired by comparing the current time 
     * with the expiresAt timestamp.
     * 
     * @param otp the One-Time Password to search for
     * @param cardId the associated CreditCard ID
     * @param now the current LocalDateTime to check if the OTP is still valid (not expired)
     * @return an Optional containing the OTPRequest if found and valid, or empty if not found or expired
     */
    Optional<OTPRequest> findByOtpAndCreditCardIdAndExpiresAtAfter(String otp, String cardId, LocalDateTime now);
}
