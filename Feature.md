



    

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

