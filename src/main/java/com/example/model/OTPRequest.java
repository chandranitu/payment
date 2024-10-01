package com.example.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents an OTP request associated with a specific credit card.
 * It stores the OTP value, its expiration time, and links to the corresponding credit card.
 */
@Document(collection = "otp_requests")
public class OTPRequest {

    @Id
    private String id; // Unique identifier for the OTP request

    @DBRef
    private CreditCard creditCard; // Reference to the associated credit card

    private String otp; // The generated OTP code
    private LocalDateTime expiresAt; // Expiration timestamp for the OTP

    // Logger for tracking the activity in this class
    private static final Logger logger = LoggerFactory.getLogger(OTPRequest.class);

    /**
     * Gets the unique identifier of this OTP request.
     * 
     * @return the ID of the OTP request
     */
    public String getId() {
        logger.debug("Getting OTPRequest ID: {}", id);
        return id;
    }

    /**
     * Sets the unique identifier of this OTP request.
     * 
     * @param id the new ID to set
     */
    public void setId(String id) {
        logger.debug("Setting OTPRequest ID to: {}", id);
        this.id = id;
    }

    /**
     * Gets the credit card associated with this OTP request.
     * 
     * @return the associated credit card
     */
    public CreditCard getCreditCard() {
        logger.debug("Getting CreditCard for OTPRequest ID: {}", id);
        return creditCard;
    }

    /**
     * Sets the credit card associated with this OTP request.
     * 
     * @param creditCard the credit card to associate
     */
    public void setCreditCard(CreditCard creditCard) {
        logger.debug("Setting CreditCard for OTPRequest ID: {}", id);
        this.creditCard = creditCard;
    }

    /**
     * Gets the OTP code of this request.
     * 
     * @return the OTP code
     */
    public String getOtp() {
        logger.debug("Getting OTP for OTPRequest ID: {}", id);
        return otp;
    }

    /**
     * Sets the OTP code for this request.
     * 
     * @param otp the new OTP code to set
     */
    public void setOtp(String otp) {
        logger.debug("Setting OTP for OTPRequest ID: {}", id);
        this.otp = otp;
    }

    /**
     * Gets the expiration time of this OTP request.
     * 
     * @return the expiration timestamp
     */
    public LocalDateTime getExpiresAt() {
        logger.debug("Getting expiration time for OTPRequest ID: {}", id);
        return expiresAt;
    }

    /**
     * Sets the expiration time for this OTP request.
     * 
     * @param expiresAt the new expiration timestamp to set
     */
    public void setExpiresAt(LocalDateTime expiresAt) {
        logger.debug("Setting expiration time for OTPRequest ID: {}", id);
        this.expiresAt = expiresAt;
    }

}
