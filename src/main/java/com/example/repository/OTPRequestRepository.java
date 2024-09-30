package com.example.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.OTPRequest;

@Repository
public interface OTPRequestRepository extends MongoRepository<OTPRequest, String> {
	Optional<OTPRequest> findByOtpAndCreditCardIdAndExpiresAtAfter(String otp, String cardId, LocalDateTime now);
}
