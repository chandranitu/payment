package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Transaction class represents a credit card transaction in the system.
 * It contains information about the transaction amount, OTP, status, and
 * timestamps. Additionally, it tracks the creation and update details.
 */
@Document(collection = "transactions")
public class Transaction {
    
    @Id
    private String id; // Unique identifier for the transaction

    @DBRef
    private CreditCard creditCard; // Reference to the associated credit card
    private CreditCardType cardType; // Renamed to more meaningful field (cardType)
    private BigDecimal amount; // The transaction amount
    private String otp; // The OTP used to authorize the transaction
    private String status; // Transaction status: "pending", "approved", "failed"
    private LocalDateTime createdAt; // Timestamp when the transaction was created

    private String createdBy; // User who created the transaction
    @LastModifiedBy
    private String updatedBy; // User who last updated the transaction

    // Logger to track the actions within this class
    private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

    /**
     * Gets the unique identifier of this transaction.
     * 
     * @return the ID of the transaction
     */
    public String getId() {
        logger.debug("Getting transaction ID: {}", id);
        return id;
    }

    /**
     * Sets the unique identifier of this transaction.
     * 
     * @param id the new ID to set
     */
    public void setId(String id) {
        logger.debug("Setting transaction ID to: {}", id);
        this.id = id;
    }

    /**
     * Gets the credit card associated with this transaction.
     * 
     * @return the associated credit card
     */
    public CreditCard getCreditCard() {
        logger.debug("Getting associated credit card for transaction ID: {}", id);
        return creditCard;
    }

    /**
     * Sets the credit card associated with this transaction.
     * 
     * @param creditCard the credit card to associate
     */
    public void setCreditCard(CreditCard creditCard) {
        logger.debug("Setting associated credit card for transaction ID: {}", id);
        this.creditCard = creditCard;
    }

    /**
     * Gets the card type associated with this transaction.
     * 
     * @return the card type of the transaction
     */
    public CreditCardType getCardType() {
        return cardType;
    }

    /**
     * Sets the card type associated with this transaction.
     * 
     * @param cardType the card type to associate
     */
    public void setCardType(CreditCardType cardType) {
        this.cardType = cardType;
    }

    /**
     * Gets the transaction amount.
     * 
     * @return the amount of the transaction
     */
    public BigDecimal getAmount() {
        logger.debug("Getting transaction amount for transaction ID: {}", id);
        return amount;
    }

    /**
     * Sets the transaction amount.
     * 
     * @param amount the amount to set for the transaction
     */
    public void setAmount(BigDecimal amount) {
        logger.debug("Setting transaction amount for transaction ID: {}", id);
        this.amount = amount;
    }

    /**
     * Gets the OTP used for this transaction.
     * 
     * @return the OTP for the transaction
     */
    public String getOtp() {
        logger.debug("Getting OTP for transaction ID: {}", id);
        return otp;
    }

    /**
     * Sets the OTP used for this transaction.
     * 
     * @param otp the OTP to set
     */
    public void setOtp(String otp) {
        logger.debug("Setting OTP for transaction ID: {}", id);
        this.otp = otp;
    }

    /**
     * Gets the current status of this transaction.
     * 
     * @return the status of the transaction
     */
    public String getStatus() {
        logger.debug("Getting status for transaction ID: {}", id);
        return status;
    }

    /**
     * Sets the current status of this transaction.
     * 
     * @param status the status to set ("pending", "approved", "failed")
     */
    public void setStatus(String status) {
        logger.debug("Setting status for transaction ID: {}", id);
        this.status = status;
    }

    /**
     * Gets the timestamp when this transaction was created.
     * 
     * @return the creation timestamp of the transaction
     */
    public LocalDateTime getCreatedAt() {
        logger.debug("Getting creation timestamp for transaction ID: {}", id);
        return createdAt;
    }

    /**
     * Sets the timestamp when this transaction was created.
     * 
     * @param createdAt the new timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        logger.debug("Setting creation timestamp for transaction ID: {}", id);
        this.createdAt = createdAt;
    }

    /**
     * Gets the user who created this transaction.
     * 
     * @return the user who created the transaction
     */
    public String getCreatedBy() {
        logger.debug("Getting createdBy for transaction ID: {}", id);
        return createdBy;
    }

    /**
     * Sets the user who created this transaction.
     * 
     * @param createdBy the user who created the transaction
     */
    public void setCreatedBy(String createdBy) {
        logger.debug("Setting createdBy for transaction ID: {}", id);
        this.createdBy = createdBy;
    }

    /**
     * Gets the user who last updated this transaction.
     * 
     * @return the user who last updated the transaction
     */
    public String getUpdatedBy() {
        logger.debug("Getting updatedBy for transaction ID: {}", id);
        return updatedBy;
    }

    /**
     * Sets the user who last updated this transaction.
     * 
     * @param updatedBy the user who last updated the transaction
     */
    public void setUpdatedBy(String updatedBy) {
        logger.debug("Setting updatedBy for transaction ID: {}", id);
        this.updatedBy = updatedBy;
    }

}
