package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.CreditCard;
import com.example.model.OTPRequest;
import com.example.model.Transaction;
import com.example.repository.CreditCardRepository;
import com.example.repository.OTPRequestRepository;
import com.example.repository.TransactionRepository;

/**
 * Service class responsible for handling payment operations like adding credit
 * cards,
 * initiating payments, and OTP verification.
 */
@Service
public class PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private OTPRequestRepository otpRequestRepository;

	@Autowired
	public PaymentService(TransactionRepository transactionRepository, CreditCardRepository creditCardRepository) {
		this.transactionRepository = transactionRepository;
		this.creditCardRepository = creditCardRepository;
	}

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
	 * Update the credit card details.
	 * 
	 * @param creditCard The credit card details to update.
	 * @return The updated CreditCard object.
	 */
	public CreditCard updateCreditCard(CreditCard creditCard) {
		// Check if the card exists in the database
		CreditCard existingCard = creditCardRepository.findByCardNumber(creditCard.getCardNumber())
				.orElseThrow(() -> new IllegalArgumentException("Credit Card not found"));

		// Update relevant fields
		existingCard.setCardNumber(creditCard.getCardNumber());
		existingCard.setExpiryDate(creditCard.getExpiryDate());
		existingCard.setCardHolder(creditCard.getCardHolder());
		existingCard.setCvv(creditCard.getCvv());

		// Save the updated credit card back to the repository
		return creditCardRepository.save(existingCard);
	}

	/**
	 * Deletes a credit card by its card number.
	 * 
	 * @param cardNumber The credit card number to delete.
	 * @throws IllegalArgumentException if the card is not found.
	 */
	public void deleteCreditCard(String cardNumber) {
		creditCardRepository.findByCardNumber(cardNumber)
				.ifPresentOrElse(
						card -> creditCardRepository.delete(card),
						() -> {
							throw new IllegalArgumentException("Credit Card not found");
						});
	}

	/**
	 * Fetch all credit cards from the database.
	 * 
	 * @return A list of all stored credit cards.
	 */
	public List<CreditCard> getAllCreditCards() {
		return creditCardRepository.findAll(); // Return all credit cards
	}

	/**
	 * Initiates a payment process for a given credit card.
	 * Validates card details, checks the available balance, generates OTP, and
	 * creates a pending transaction.
	 * 
	 * @param cardNumber the card number of the credit card
	 * @param cvv        the CVV of the credit card
	 * @param amount     the amount to be charged
	 * @return the created Transaction object with pending status
	 * @throws IllegalArgumentException if the card number, CVV, or balance is
	 *                                  invalid
	 */
	public Transaction initiatePayment(String cardNumber, String cvv, BigDecimal amount) {
		// Fetch credit card by card number

		logger.info("Attempting to Initiate transaction with : {}", cardNumber, cvv, amount);

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
	 * Verifies the OTP for a given transaction. If the OTP is valid and not
	 * expired,
	 * the transaction is approved, and the balance is deducted from the credit
	 * card.
	 * 
	 * @param transactionId the ID of the transaction being verified
	 * @param otp           the OTP provided by the user
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

	public Transaction refundPayment(String transactionId) {
		// Log the start of the refund process
		logger.info("Attempting to refund transaction with ID: {}", transactionId);

		// Fetch the transaction by ID
		Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
		if (transactionOptional.isEmpty()) {
			logger.error("Transaction with ID {} not found.", transactionId);
			throw new IllegalArgumentException("Transaction with ID " + transactionId + " not found.");
		}

		// Get the transaction object
		Transaction transaction = transactionOptional.get();
		BigDecimal refundAmount = transaction.getAmount(); // Assuming 'getAmount' returns the transaction amount
		logger.info("Refund amount: {}", refundAmount);

		// Fetch the associated card number from the transaction
		CreditCard creditCardNumber = transaction.getCreditCard(); // Assuming 'getCreditCard()' returns the card number
		logger.info("Associated credit card number: {}", creditCardNumber);

		// Fetch the account associated with the transaction using the credit card
		// number

		Optional<CreditCard> accountOptional = creditCardRepository.findByCardNumber(creditCardNumber.getCardNumber());
		if (accountOptional.isEmpty()) {
			logger.error("Credit card associated with transaction ID {} not found.", transactionId);
			throw new IllegalArgumentException("Credit card not found for transaction " + transactionId);
		}

		// Get the account object
		CreditCard creditCard = accountOptional.get();
		logger.info("Current account balance: {}", creditCard.getBalance());

		// Add the refund amount to the account balance
		BigDecimal newBalance = creditCard.getBalance().add(refundAmount);
		creditCard.setBalance(newBalance); // Assuming 'setBalance' method exists
		logger.info("New account balance after refund: {}", newBalance);

		// Save the updated credit card account to the database
		creditCardRepository.save(creditCard);
		logger.info("Account balance updated successfully.");

		// Update the transaction status to 'refunded'
		transaction.setStatus("refunded"); // Assuming 'setStatus' method exists
		transactionRepository.save(transaction);
		logger.info("Transaction status updated to 'refunded'.");

		// Return the refunded transaction
		return transaction;
	}

	/**
	 * Retrieves the transaction history for a given credit card number.
	 *
	 * @param cardNumber the credit card number to search for transactions
	 * @return a list of transactions associated with the credit card
	 */
	/*
	 * public Transaction getTransactionHistory(String cardNumber) {
	 * logger.info("Fetching transaction history for card number: {}", cardNumber);
	 * 
	 * // first fetch card reference id
	 * // through reference id fetch transaction id
	 * Optional<Transaction> transactionOptional =
	 * transactionRepository.findById(transactionId);
	 * Transaction transaction = transactionOptional.get();
	 * return transaction;
	 * }
	 */

	public List<Transaction> getTransactionHistory(String cardNumber) {
		logger.info("Fetching transactions for credit card number: {}", cardNumber);

		// Step 1: Fetch the CreditCard object by card number
		Optional<CreditCard> creditCardOptional = creditCardRepository.findByCardNumber(cardNumber);

		if (creditCardOptional.isEmpty()) {
			logger.warn("No credit card found for card number: {}", cardNumber);
			throw new IllegalArgumentException("Credit card not found for card number: " + cardNumber);
		}

		CreditCard creditCard = creditCardOptional.get();
		ObjectId creditCardId = new ObjectId(creditCard.getId()); // Assuming 'getId' returns ObjectId

		// Step 2: Fetch transactions using the CreditCard reference (DBRef)
		List<Transaction> transactions = transactionRepository.findByCreditCardRef(creditCardId);

		if (transactions.isEmpty()) {
			logger.warn("No transactions found for credit card number: {}", cardNumber);
		} else {
			logger.info("Found {} transactions for credit card number: {}", transactions.size(), cardNumber);
		}

		return transactions;
	}

	public String getTransactionStatus(String transactionId) {
		Optional<Transaction> transaction = transactionRepository.findById(transactionId); 
	
		if (!transaction.isPresent()) {
			throw new TransactionNotFoundException("Transaction not found for ID: " + transactionId);
		}
	
		return transaction.get().getStatus(); // Assuming the Transaction object has a getStatus() method
	}

    public void cancelTransaction(String transactionId) {
		// Fetch the transaction from the database
		Transaction transaction = transactionRepository.findById(transactionId)
			.orElseThrow(() -> new IllegalArgumentException("Transaction not found with ID: " + transactionId));
		
		// Check if the transaction is already completed or canceled
		if ("COMPLETED".equals(transaction.getStatus())) {
			throw new UnsupportedOperationException("Cannot cancel a completed transaction.");
		} else if ("CANCELED".equals(transaction.getStatus())) {
			throw new UnsupportedOperationException("Transaction is already canceled.");
		}
		
		// Mark the transaction as canceled
		transaction.setStatus("CANCELED");
		transaction.setCreatedAt(LocalDateTime.now());
	
		// Save the updated transaction back to the database
		transactionRepository.save(transaction);
	
		// If needed, perform any rollback or compensating actions here (e.g., refund payment)
		// refundService.processRefund(transaction);
	
		// Log the cancellation
		logger.info("Transaction with ID {} has been canceled.", transactionId);
	}

	public boolean checkForFraud(String transactionId) {
		// Simulate fraud detection logic
		if (transactionId == null || transactionId.isEmpty()) {
			throw new IllegalArgumentException("Transaction ID cannot be null or empty");
		}
	
		// Here you can add actual fraud detection logic, like checking against a database, 
		// calling an external service, or applying business rules.
	
		// For demonstration, we will use a simple rule: if the transactionId contains "fraud", we assume it's fraudulent.
		if (transactionId.toLowerCase().contains("fraud")) {
			return true; // Indicating this transaction is fraudulent
		}
		
		// Otherwise, the transaction is not fraudulent
		return false;
	}
	

}
