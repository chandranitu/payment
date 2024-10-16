 
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

