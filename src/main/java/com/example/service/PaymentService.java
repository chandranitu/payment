package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.CreditCard;
import com.example.model.OTPRequest;
import com.example.model.Transaction;
import com.example.repository.CreditCardRepository;
import com.example.repository.OTPRequestRepository;
import com.example.repository.TransactionRepository;

@Service

public class PaymentService {

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private OTPRequestRepository otpRequestRepository;

	public Transaction initiatePayment(String cardNumber, String cvv, BigDecimal amount) {
		// Fetch credit card by card number
		System.out.println("Card Number:-->>> " + cardNumber);
		System.out.println("cvv:-->>> " + cvv);
		System.out.println("amount:-->>> " + amount);
		CreditCard creditCard = creditCardRepository.findByCardNumber(cardNumber)
				.orElseThrow(() -> new IllegalArgumentException("Invalid card number"));

		// Validate CVV
		if (!creditCard.getCvv().equals(cvv)) {
			throw new IllegalArgumentException("Invalid CVV");
		}

		// Validate balance
		if (creditCard.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("Insufficient balance");
		}

		// Create and save transaction
		Transaction transaction = new Transaction();
		transaction.setCreditCard(creditCard);
		transaction.setAmount(amount);
		transaction.setStatus("pending");
		transaction.setCreatedAt(LocalDateTime.now());

		transactionRepository.save(transaction);

		// Generate and save OTP
		String otp = generateOtp();
		OTPRequest otpRequest = new OTPRequest();
		otpRequest.setCreditCard(creditCard);
		otpRequest.setOtp(otp);
		otpRequest.setExpiresAt(LocalDateTime.now().plusMinutes(5));

		otpRequestRepository.save(otpRequest);

		// Simulate OTP sent via SMS/Email
		System.out.println("Generated OTP: " + otp);

		return transaction;
	}

	public Transaction verifyOtp(String transactionId, String otp) {
		// Fetch transaction by ID
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID"));

		// Validate OTP
		OTPRequest otpRequest = otpRequestRepository.findByOtpAndCreditCardIdAndExpiresAtAfter(otp,
				transaction.getCreditCard().getId(), LocalDateTime.now())
				.orElseThrow(() -> new IllegalArgumentException("Invalid or expired OTP"));
				

		// Approve transaction and deduct balance
		transaction.setStatus("approved");

		CreditCard creditCard = transaction.getCreditCard();
		creditCard.setBalance(creditCard.getBalance().subtract(transaction.getAmount()));

		// Save the updated transaction and credit card
		transactionRepository.save(transaction);
		creditCardRepository.save(creditCard);

		return transaction;
	}

	// Helper method to generate a 6-digit OTP
	private String generateOtp() {
		return String.format("%06d", new Random().nextInt(999999));
	}
}
