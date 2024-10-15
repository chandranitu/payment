package com.example.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CreditCard;
import com.example.model.RecurringPayment;
import com.example.model.Transaction;
import com.example.request.OtpVerificationRequest;
import com.example.request.PaymentRequest;
import com.example.service.PaymentService;
import com.example.service.RecurringService;
import com.example.service.TransactionNotFoundException;

import org.slf4j.Logger;

/**
 * Controller for handling payment-related operations.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService; // Service to handle payment logic

    @Autowired
    private RecurringService recurringService; // Service to handle recurring logic

    /**
     * Health check endpoint to verify if the payment service is running.
     * 
     * @return A message indicating the service status.
     */
    @GetMapping("/status")
    public String status() {
        return "Payment Service is up and running!";
    }

    /**
     * Endpoint to add a new credit card.
     * 
     * @param creditCard The credit card details to be added.
     * @return ResponseEntity containing the created CreditCard and HTTP status.
     */
    @PostMapping("/addcard")
    public ResponseEntity<CreditCard> addCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard card = paymentService.addCreditCard(creditCard); // Add credit card using the service
        return new ResponseEntity<>(card, HttpStatus.CREATED); // Return the created card with 201 status
    }

    /**
     * Endpoint to initiate a payment transaction.
     * 
     * @param request The payment request containing card details and amount.
     * @return ResponseEntity containing the initiated Transaction.
     */

    @PostMapping("/initiate")
    public ResponseEntity<Transaction> initiatePayment(@RequestBody PaymentRequest request) {
        Transaction transaction = paymentService.initiatePayment(request.getCardNumber(), request.getCvv(),
                request.getAmount()); // Initiate payment using the service
        return ResponseEntity.ok(transaction); // Return the transaction details with 200 status
    }

    /**
     * Endpoint to verify an OTP for a payment transaction.
     * 
     * @param request The request containing transaction ID and OTP.
     * @return ResponseEntity containing the verified Transaction.
     */

    @PostMapping("/verify")
    public ResponseEntity<Transaction> verifyOtp(@RequestBody OtpVerificationRequest request) {
        Transaction transaction = paymentService.verifyOtp(request.getTransactionId(), request.getOtp()); // Verify OTP
                                                                                                          // using the
                                                                                                          // service
        return ResponseEntity.ok(transaction); // Return the transaction details with 200 status
    }

    /**
     * Endpoint to process a refund for a payment transaction.
     * 
     * @param transactionId The ID of the transaction to be refunded.
     * @return ResponseEntity containing the refunded Transaction.
     */

    /**
     * Endpoint to update a credit card's information.
     * 
     * @param creditCard The updated credit card details.
     * @return ResponseEntity containing the updated CreditCard.
     */

    @PutMapping("/updatecard")
    public ResponseEntity<CreditCard> updateCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard updatedCard = paymentService.updateCreditCard(creditCard); // Update credit card details using the
                                                                              // service
        return ResponseEntity.ok(updatedCard); // Return the updated card with 200 status
    }

    /**
     * Endpoint to delete a saved credit card.
     * 
     * @param cardNumber The credit card number to delete.
     * @return ResponseEntity with status of deletion.
     */
    @DeleteMapping("/deletecard/{cardNumber}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable String cardNumber) {
        paymentService.deleteCreditCard(cardNumber); // Delete the credit card using the service
        return ResponseEntity.noContent().build(); // Return 204 status with no content
    }

    /**
     * Endpoint to get all stored credit cards.
     * 
     * @return ResponseEntity containing the list of credit cards and HTTP status.
     */
    @GetMapping("/allcards")
    public ResponseEntity<List<CreditCard>> getAllCreditCards() {
        List<CreditCard> cards = paymentService.getAllCreditCards(); // Fetch all cards from the service
        return ResponseEntity.ok(cards); // Return the list with 200 status
    }

    /**
     * Endpoint to process a refund for a payment transaction.
     * 
     * @param transactionId The ID of the transaction to be refunded.
     * @return ResponseEntity containing the refunded Transaction.
     */

    @PostMapping("/refund")
    public ResponseEntity<Transaction> refundPayment(@RequestBody String transactionId) {
        Transaction refundedTransaction = paymentService.refundPayment(transactionId); // Refund payment using the
        logger.info(" In refund section "); // service
        return ResponseEntity.ok(refundedTransaction); // Return the refunded transaction details with 200 status
    }

    /**
     * Endpoint to get the transaction history for a specific credit card.
     * 
     * @param cardNumber The card number to fetch transaction history for.
     * @return ResponseEntity containing the list of transactions.
     */
    @GetMapping("/history/{cardNumber}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String cardNumber) {
        List<Transaction> transactions = paymentService.getTransactionHistory(cardNumber); // Fetch transaction history
        return ResponseEntity.ok(transactions); // Return the transaction list with 200 status
    }

    /**
     * Endpoint to set up recurring payments for a credit card.
     * 
     * @param request The payment request with details about the recurrence.
     * @return ResponseEntity containing the recurring payment details.
     */

    @PostMapping("/recurring")
    public ResponseEntity<String> setupRecurringPayment(@RequestBody RecurringPayment request) {
        // Log the start of the recurring payment setup process
        logger.info("Setting up recurring payment for card: {} with amount: {}", request.getCardNumber(),
                request.getAmount());

        try {
            // Use the recurringService instance to set up the recurring payment
            String scheduleId = recurringService.setupRecurringPayment(request);

            // Log the successful setup
            logger.info("Recurring payment scheduled with ID: {}", scheduleId);

            // Return a response with the schedule ID and 200 OK status
            return ResponseEntity.ok("Recurring payment scheduled with ID: " + scheduleId);
        } catch (IllegalArgumentException ex) {
            // Log the error if any validation or processing issue occurs
            logger.error("Failed to set up recurring payment: {}", ex.getMessage());

            // Return a bad request response with the error message
            return ResponseEntity.badRequest().body("Failed to set up recurring payment: " + ex.getMessage());
        } catch (Exception ex) {
            // Log any other unexpected errors
            logger.error("Unexpected error occurred: {}", ex.getMessage());

            // Return a server error response
            return ResponseEntity.status(500).body("Internal server error occurred while setting up recurring payment");
        }

    }

    /**
     * Endpoint to check the status of a specific payment transaction.
     * 
     * @param transactionId The transaction ID to check.
     * @return ResponseEntity containing the transaction status.
     */
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<String> getTransactionStatus(@PathVariable String transactionId) {
        try {
            String status = paymentService.getTransactionStatus(transactionId);
            return ResponseEntity.ok(status);
        } catch (TransactionNotFoundException e) {
            // Return 404 Not Found if the transaction is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle generic exceptions with a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching transaction status.");
        }
    }

    /**
     * Endpoint to cancel a pending transaction.
     * 
     * @param transactionId The ID of the transaction to cancel.
     * @return ResponseEntity indicating success or failure of the cancellation.
     */
    @PostMapping("/cancel/{transactionId}")
    public ResponseEntity<String> cancelTransaction(@PathVariable String transactionId) {
        paymentService.cancelTransaction(transactionId); // Cancel the transaction
        return ResponseEntity.ok("Transaction canceled successfully");
    }

}
