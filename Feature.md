@PostMapping("/refund")
    public ResponseEntity<Transaction> refundPayment(@RequestBody String transactionId) {
        Transaction refundedTransaction = paymentService.refundPayment(transactionId); // Refund payment using the
                                                                                       // service
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
 * Endpoint to check for potential fraud in a transaction.
 * 
 * @param transactionId The transaction ID to check for fraud.
 * @return ResponseEntity indicating whether fraud is suspected.
 */
@GetMapping("/fraud-check/{transactionId}")
public ResponseEntity<Boolean> fraudCheck(@PathVariable String transactionId) {
    boolean isFraudulent = paymentService.checkForFraud(transactionId); // Check if the transaction is fraudulent
    return ResponseEntity.ok(isFraudulent); // Return fraud status with 200 status
}


/**
 * Endpoint to set up recurring payments for a credit card.
 * 
 * @param request The payment request with details about the recurrence.
 * @return ResponseEntity containing the recurring payment details.
 */
@PostMapping("/recurring")
public ResponseEntity<String> setupRecurringPayment(@RequestBody RecurringPaymentRequest request) {
    String scheduleId = paymentService.setupRecurringPayment(request); // Set up recurring payment
    return ResponseEntity.ok("Recurring payment scheduled with ID: " + scheduleId); // Return the schedule ID with 200 status
}


/**
 * Endpoint to check the status of a specific payment transaction.
 * 
 * @param transactionId The transaction ID to check.
 * @return ResponseEntity containing the transaction status.
 */
@GetMapping("/status/{transactionId}")
public ResponseEntity<String> getTransactionStatus(@PathVariable String transactionId) {
    String status = paymentService.getTransactionStatus(transactionId); // Fetch the transaction status
    return ResponseEntity.ok(status); // Return the status with 200 status
}





/**
 * Endpoint to set a spending limit for a credit card.
 * 
 * @param cardNumber The card number to set the limit for.
 * @param limit The spending limit.
 * @return ResponseEntity confirming the limit setting.
 */
@PostMapping("/set-limit/{cardNumber}")
public ResponseEntity<String> setSpendingLimit(@PathVariable String cardNumber, @RequestBody BigDecimal limit) {
    paymentService.setSpendingLimit(cardNumber, limit); // Set the spending limit
    return ResponseEntity.ok("Spending limit set successfully");
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
