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

/**
 * Service class responsible for handling payment operations like adding credit cards,
 * initiating payments, and OTP verification.
 */
@Service
public class PaymentService {

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private OTPRequestRepository otpRequestRepository;

	/**
	 * Adds a new credit card to the repository.
	 * 
	 * @param creditCard the CreditCard object containing card details to be added
	 * @return the saved CreditCard object
	 */
	public CreditCard addCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    } 

	/**
	 * Initiates a payment process for a given credit card.
	 * Validates card details, checks the available balance, generates OTP, and creates a pending transaction.
	 * 
	 * @param cardNumber the card number of the credit card
	 * @param cvv the CVV of the credit card
	 * @param amount the amount to be charged
	 * @return the created Transaction object with pending status
	 * @throws IllegalArgumentException if the card number, CVV, or balance is invalid
	 */
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

	/**
	 * Verifies the OTP for a given transaction. If the OTP is valid and not expired,
	 * the transaction is approved, and the balance is deducted from the credit card.
	 * 
	 * @param transactionId the ID of the transaction being verified
	 * @param otp the OTP provided by the user
	 * @return the updated Transaction object with approved status
	 * @throws IllegalArgumentException if the transaction ID or OTP is invalid
	 */
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

	/**
	 * Helper method to generate a 6-digit OTP.
	 * 
	 * @return a randomly generated 6-digit OTP as a String
	 */
	private String generateOtp() {
		return String.format("%06d", new Random().nextInt(999999));
	}
}
