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
import com.example.model.Transaction;
import com.example.request.OtpVerificationRequest;
import com.example.request.PaymentRequest;
import com.example.service.PaymentService;

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

}
