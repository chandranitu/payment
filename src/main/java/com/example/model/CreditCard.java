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
    private String cardType;    // "Visa", "MasterCard", "Amex", etc
    private BigDecimal creditLimit; // The maximum credit limit
    private String status;          // "active", "inactive", "suspended", "expired"
    private String billingAddress;  // The billing address
    private String shippingAddress; // The shipping address
    private LocalDateTime lastTransactionDate; // The date and time of the last transaction
    private boolean isActive; // Whether the card is active or not
    private boolean hasFraudAlert; // Whether fraud alerts are triggered
    private double rewardPoints; // The accumulated reward points
    private double interestRate; // The interest rate applied to outstanding balances
    private double minimumPayment; // The minimum payment required for the current cycle
    private LocalDateTime lastPaymentDate; // The date of the last payment
    private String currency; // The currency used for transactions (e.g., USD, EUR)
    private String securityQuestion; // Security question for card recovery
    private String securityAnswer; // The answer to the security question

    private String createdBy; // User who created the record
    private String updatedBy; // User who last updated the record

    @CreatedDate
    private LocalDateTime createdAt; // Timestamp of when the record was created

    @LastModifiedDate
    private LocalDateTime updatedAt; // Timestamp of the last update to the record

    // Default constructor
    public CreditCard() {
        logger.debug("CreditCard default constructor called.");
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
                      BigDecimal balance, String cardType, BigDecimal creditLimit, String status,
                      String billingAddress, String shippingAddress, LocalDateTime lastTransactionDate,
                      boolean isActive, boolean hasFraudAlert, double rewardPoints, double interestRate,
                      double minimumPayment, LocalDateTime lastPaymentDate, String currency,
                      String securityQuestion, String securityAnswer, String createdBy, String updatedBy,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.balance = balance;
        this.cardType = cardType;
        this.creditLimit = creditLimit;
        this.status = status;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.lastTransactionDate = lastTransactionDate;
        this.isActive = isActive;
        this.hasFraudAlert = hasFraudAlert;
        this.rewardPoints = rewardPoints;
        this.interestRate = interestRate;
        this.minimumPayment = minimumPayment;
        this.lastPaymentDate = lastPaymentDate;
        this.currency = currency;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        logger.debug("CreditCard full constructor called with cardNumber: {}", cardNumber);
    }

    // Getters and Setters with logging

    public String getId() {
        logger.debug("getId called.");
        return id;
    }

    public void setId(String id) {
        logger.debug("setId called with id: {}", id);
        this.id = id;
    }

    public String getCardNumber() {
        logger.debug("getCardNumber called.");
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        logger.debug("setCardNumber called with cardNumber: {}", cardNumber);
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        logger.debug("getCardHolder called.");
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        logger.debug("setCardHolder called with cardHolder: {}", cardHolder);
        this.cardHolder = cardHolder;
    }

    public String getCvv() {
        logger.debug("getCvv called.");
        return cvv;
    }

    public void setCvv(String cvv) {
        logger.debug("setCvv called with cvv: {}", cvv);
        this.cvv = cvv;
    }

    public LocalDate getExpiryDate() {
        logger.debug("getExpiryDate called.");
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        logger.debug("setExpiryDate called with expiryDate: {}", expiryDate);
        this.expiryDate = expiryDate;
    }

    public BigDecimal getBalance() {
        logger.debug("getBalance called.");
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        logger.debug("setBalance called with balance: {}", balance);
        this.balance = balance;
    }

    public String getCardType() {
        logger.debug("getCardType called.");
        return cardType;
    }

    public void setCardType(String cardType) {
        logger.debug("setCardType called with cardType: {}", cardType);
        this.cardType = cardType;
    }

    public BigDecimal getCreditLimit() {
        logger.debug("getCreditLimit called.");
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        logger.debug("setCreditLimit called with creditLimit: {}", creditLimit);
        this.creditLimit = creditLimit;
    }

    public String getStatus() {
        logger.debug("getStatus called.");
        return status;
    }

    public void setStatus(String status) {
        logger.debug("setStatus called with status: {}", status);
        this.status = status;
    }

    public String getBillingAddress() {
        logger.debug("getBillingAddress called.");
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        logger.debug("setBillingAddress called with billingAddress: {}", billingAddress);
        this.billingAddress = billingAddress;
    }

    public String getShippingAddress() {
        logger.debug("getShippingAddress called.");
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        logger.debug("setShippingAddress called with shippingAddress: {}", shippingAddress);
        this.shippingAddress = shippingAddress;
    }

    public LocalDateTime getLastTransactionDate() {
        logger.debug("getLastTransactionDate called.");
        return lastTransactionDate;
    }

    public void setLastTransactionDate(LocalDateTime lastTransactionDate) {
        logger.debug("setLastTransactionDate called with lastTransactionDate: {}", lastTransactionDate);
        this.lastTransactionDate = lastTransactionDate;
    }

    public boolean isActive() {
        logger.debug("isActive called.");
        return isActive;
    }

    public void setActive(boolean active) {
        logger.debug("setActive called with active: {}", active);
        isActive = active;
    }

    public boolean hasFraudAlert() {
        logger.debug("hasFraudAlert called.");
        return hasFraudAlert;
    }

    public void setFraudAlert(boolean fraudAlert) {
        logger.debug("setFraudAlert called with fraudAlert: {}", fraudAlert);
        this.hasFraudAlert = fraudAlert;
    }

    public double getRewardPoints() {
        logger.debug("getRewardPoints called.");
        return rewardPoints;
    }

    public void setRewardPoints(double rewardPoints) {
        logger.debug("setRewardPoints called with rewardPoints: {}", rewardPoints);
        this.rewardPoints = rewardPoints;
    }

    public double getInterestRate() {
        logger.debug("getInterestRate called.");
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        logger.debug("setInterestRate called with interestRate: {}", interestRate);
        this.interestRate = interestRate;
    }

    public double getMinimumPayment() {
        logger.debug("getMinimumPayment called.");
        return minimumPayment;
    }

    public void setMinimumPayment(double minimumPayment) {
        logger.debug("setMinimumPayment called with minimumPayment: {}", minimumPayment);
        this.minimumPayment = minimumPayment;
    }

    public LocalDateTime getLastPaymentDate() {
        logger.debug("getLastPaymentDate called.");
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
        logger.debug("setLastPaymentDate called with lastPaymentDate: {}", lastPaymentDate);
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getCurrency() {
        logger.debug("getCurrency called.");
        return currency;
    }

    public void setCurrency(String currency) {
        logger.debug("setCurrency called with currency: {}", currency);
        this.currency = currency;
    }

    public String getSecurityQuestion() {
        logger.debug("getSecurityQuestion called.");
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        logger.debug("setSecurityQuestion called with securityQuestion: {}", securityQuestion);
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        logger.debug("getSecurityAnswer called.");
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        logger.debug("setSecurityAnswer called with securityAnswer: {}", securityAnswer);
        this.securityAnswer = securityAnswer;
    }

    public String getCreatedBy() {
        logger.debug("getCreatedBy called.");
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        logger.debug("setCreatedBy called with createdBy: {}", createdBy);
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        logger.debug("getUpdatedBy called.");
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        logger.debug("setUpdatedBy called with updatedBy: {}", updatedBy);
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        logger.debug("getCreatedAt called.");
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        logger.debug("setCreatedAt called with createdAt: {}", createdAt);
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        logger.debug("getUpdatedAt called.");
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        logger.debug("setUpdatedAt called with updatedAt: {}", updatedAt);
        this.updatedAt = updatedAt;
    }
}
