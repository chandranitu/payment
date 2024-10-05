package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.model.RecurringPayment;


public interface RecurringPaymentRepository extends MongoRepository<RecurringPayment, String>{



}
