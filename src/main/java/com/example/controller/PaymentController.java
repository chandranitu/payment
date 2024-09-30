package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Transaction;
import com.example.request.OtpVerificationRequest;
import com.example.request.PaymentRequest;
import com.example.service.PaymentService;



@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;



	@GetMapping("/status")
	public String status() {
		return "Payment Service is up and running!";
	}

	@PostMapping("/initiate")
	public ResponseEntity<Transaction> initiatePayment(@RequestBody PaymentRequest request) {
		Transaction transaction = paymentService.initiatePayment(request.getCardNumber(), request.getCvv(),
				request.getAmount());
		return ResponseEntity.ok(transaction);
	}

	@PostMapping("/verify")
	public ResponseEntity<Transaction> verifyOtp(@RequestBody OtpVerificationRequest request) {
		Transaction transaction = paymentService.verifyOtp(request.getTransactionId(), request.getOtp());
		return ResponseEntity.ok(transaction);
	}

	
	//@PostMapping("/data")
	//public ResponseEntity<User> createData(@RequestParam String key, @RequestParam String value,
	//		@RequestParam List<String> roles) {
	//	User user = userService.createUser(key, value, roles);
	//	return new ResponseEntity<>(user, HttpStatus.CREATED);
	//}

	
}
