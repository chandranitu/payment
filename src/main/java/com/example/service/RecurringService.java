package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.RecurringPayment;
import com.example.repository.RecurringPaymentRepository;

@Service
public class RecurringService {

    private static final Logger logger = LoggerFactory.getLogger(RecurringService.class);

    @Autowired
    private RecurringPaymentRepository recurringPaymentRepository;

    // Constructor-based dependency injection (optional since field injection is used above)
    @Autowired
    public RecurringService(RecurringPaymentRepository recurringPaymentRepository) {
        this.recurringPaymentRepository = recurringPaymentRepository;
    }

    public String setupRecurringPayment(RecurringPayment request) {
        // Log the setup process
        logger.info("Setting up recurring payment for card: {} with amount: {}", request.getCardNumber(), request.getAmount());

        // Validate the request parameters
        if (request.getStartDate() == null || request.getEndDate() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Invalid recurring payment request. Start/End Date or Amount is incorrect.");
            throw new IllegalArgumentException("Invalid request parameters");
        }

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        // Check if end date is after the start date
        if (endDate.isBefore(startDate)) {
            logger.error("End date {} is before start date {}", endDate, startDate);
            throw new IllegalArgumentException("End date must be after start date");
        }

        // Generate a unique ID for the scheduled payment
        String scheduleId = UUID.randomUUID().toString();

        // Create a new RecurringPayment entity
        RecurringPayment recurringPayment = new RecurringPayment();
        recurringPayment.setScheduleId(scheduleId);
        recurringPayment.setCardNumber(request.getCardNumber());
        recurringPayment.setAmount(request.getAmount());
        recurringPayment.setFrequency(request.getFrequency());
        recurringPayment.setStartDate(startDate);
        recurringPayment.setEndDate(endDate);

        // Save the recurring payment details in the database
        recurringPaymentRepository.save(recurringPayment);

        // Log the successful scheduling
        logger.info("Recurring payment successfully scheduled with ID: {}", scheduleId);

        // Return the generated schedule ID
        return scheduleId;
    }

}
