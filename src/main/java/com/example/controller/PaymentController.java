package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CreditCard;
import com.example.model.Transaction;
import com.example.request.OtpVerificationRequest;
import com.example.request.PaymentRequest;
import com.example.service.PaymentService;

/**
 * Controller for handling payment-related operations.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

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
        Transaction transaction = paymentService.verifyOtp(request.getTransactionId(), request.getOtp()); // Verify OTP using the service
        return ResponseEntity.ok(transaction); // Return the transaction details with 200 status
    }

    /*
    // Example commented-out endpoint to create a user with specific roles.
    @PostMapping("/data")
    public ResponseEntity<User> createData(@RequestParam String key, @RequestParam String value,
            @RequestParam List<String> roles) {
        User user = userService.createUser(key, value, roles); // Create user using the service
        return new ResponseEntity<>(user, HttpStatus.CREATED); // Return the created user with 201 status
    }
    */
}
