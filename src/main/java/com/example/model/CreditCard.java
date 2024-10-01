package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

// Created by chandrashekhar

@Document(collection = "credit_cards")
public class CreditCard {

    private static final Logger logger = LoggerFactory.getLogger(CreditCard.class); // SLF4J Logger

    @Id
    private String id; // Unique identifier for the credit card
    private String cardNumber; // The credit card number
    private String cardHolder; // Name of the card holder
    private String cvv; // Card Verification Value
    private LocalDate expiryDate; // Expiration date of the credit card
    private BigDecimal balance; // Current balance on the card

    private String createdBy; // User who created the record
    private String updatedBy; // User who last updated the record

    @CreatedDate
    private LocalDateTime createdAt; // Timestamp of when the record was created

    @LastModifiedDate
    private LocalDateTime updatedAt; // Timestamp of the last update to the record

    // Default constructor
    public CreditCard() {
        logger.info("CreditCard default constructor called.");
    }

    /**
     * Full constructor for CreditCard class.
     *
     * @param id - Unique identifier for the credit card
     * @param cardNumber - The credit card number
     * @param cardHolder - Name of the card holder
     * @param cvv - Card Verification Value
     * @param expiryDate - Expiration date of the credit card
     * @param balance - Current balance on the card
     * @param createdBy - User who created the record
     * @param updatedBy - User who last updated the record
     * @param createdAt - Timestamp of when the record was created
     * @param updatedAt - Timestamp of the last update to the record
     */
    public CreditCard(String id, String cardNumber, String cardHolder, String cvv, LocalDate expiryDate,
                      BigDecimal balance, String createdBy, String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.balance = balance;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        logger.info("CreditCard full constructor called with cardNumber: {}", cardNumber);
    }

    // Getters and Setters with logging

    /**
     * Gets the unique identifier for the credit card.
     *
     * @return id - Unique identifier
     */
    public String getId() {
        logger.debug("getId called.");
        return id;
    }

    /**
     * Sets the unique identifier for the credit card.
     *
     * @param id - Unique identifier to set
     */
    public void setId(String id) {
        logger.debug("setId called with id: {}", id);
        this.id = id;
    }

    /**
     * Gets the credit card number.
     *
     * @return cardNumber - The credit card number
     */
    public String getCardNumber() {
        logger.debug("getCardNumber called.");
        return cardNumber;
    }

    /**
     * Sets the credit card number.
     *
     * @param cardNumber - The credit card number to set
     */
    public void setCardNumber(String cardNumber) {
        logger.debug("setCardNumber called with cardNumber: {}", cardNumber);
        this.cardNumber = cardNumber;
    }

    /**
     * Gets the name of the card holder.
     *
     * @return cardHolder - Name of the card holder
     */
    public String getCardHolder() {
        logger.debug("getCardHolder called.");
        return cardHolder;
    }

    /**
     * Sets the name of the card holder.
     *
     * @param cardHolder - Name of the card holder to set
     */
    public void setCardHolder(String cardHolder) {
        logger.debug("setCardHolder called with cardHolder: {}", cardHolder);
        this.cardHolder = cardHolder;
    }

    /**
     * Gets the Card Verification Value (CVV).
     *
     * @return cvv - The CVV of the credit card
     */
    public String getCvv() {
        logger.debug("getCvv called.");
        return cvv;
    }

    /**
     * Sets the Card Verification Value (CVV).
     *
     * @param cvv - The CVV to set
     */
    public void setCvv(String cvv) {
        logger.debug("setCvv called with cvv: {}", cvv);
        this.cvv = cvv;
    }

    /**
     * Gets the expiration date of the credit card.
     *
     * @return expiryDate - Expiration date of the credit card
     */
    public LocalDate getExpiryDate() {
        logger.debug("getExpiryDate called.");
        return expiryDate;
    }

    /**
     * Sets the expiration date of the credit card.
     *
     * @param expiryDate - Expiration date to set
     */
    public void setExpiryDate(LocalDate expiryDate) {
        logger.debug("setExpiryDate called with expiryDate: {}", expiryDate);
        this.expiryDate = expiryDate;
    }

    /**
     * Gets the current balance on the credit card.
     *
     * @return balance - Current balance on the card
     */
    public BigDecimal getBalance() {
        logger.debug("getBalance called.");
        return balance;
    }

    /**
     * Sets the current balance on the credit card.
     *
     * @param balance - Balance to set
     */
    public void setBalance(BigDecimal balance) {
        logger.debug("setBalance called with balance: {}", balance);
        this.balance = balance;
    }

    /**
     * Gets the user who created the record.
     *
     * @return createdBy - User who created the record
     */
    public String getCreatedBy() {
        logger.debug("getCreatedBy called.");
        return createdBy;
    }

    /**
     * Sets the user who created the record.
     *
     * @param createdBy - User to set as the creator
     */
    public void setCreatedBy(String createdBy) {
        logger.debug("setCreatedBy called with createdBy: {}", createdBy);
        this.createdBy = createdBy;
    }

    /**
     * Gets the user who last updated the record.
     *
     * @return updatedBy - User who last updated the record
     */
    public String getUpdatedBy() {
        logger.debug("getUpdatedBy called.");
        return updatedBy;
    }

    /**
     * Sets the user who last updated the record.
     *
     * @param updatedBy - User to set as the updater
     */
    public void setUpdatedBy(String updatedBy) {
        logger.debug("setUpdatedBy called with updatedBy: {}", updatedBy);
        this.updatedBy = updatedBy;
    }

    /**
     * Gets the timestamp of when the record was created.
     *
     * @return createdAt - Timestamp of creation
     */
    public LocalDateTime getCreatedAt() {
        logger.debug("getCreatedAt called.");
        return createdAt;
    }

    /**
     * Sets the timestamp of when the record was created.
     *
     * @param createdAt - Timestamp to set as creation time
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        logger.debug("setCreatedAt called with createdAt: {}", createdAt);
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp of the last update to the record.
     *
     * @return updatedAt - Timestamp of last update
     */
    public LocalDateTime getUpdatedAt() {
        logger.debug("getUpdatedAt called.");
        return updatedAt;
    }

    /**
     * Sets the timestamp of the last update to the record.
     *
     * @param updatedAt - Timestamp to set as last update time
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        logger.debug("setUpdatedAt called with updatedAt: {}", updatedAt);
        this.updatedAt = updatedAt;
    }
}
