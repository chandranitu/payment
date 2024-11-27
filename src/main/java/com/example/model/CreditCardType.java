package com.example.model;

/**
 * Enum to represent different types of Credit Cards.
 * This could include types like "VISA", "MasterCard", "AMEX", etc.
 */
public enum CreditCardType {

    VISA("Visa"),
    MASTER_CARD("MasterCard"),
    AMEX("American Express"),
    DISCOVER("Discover");

    private final String displayName;

    // Constructor to initialize the display name
    CreditCardType(String displayName) {
        this.displayName = displayName;
    }

    // Getter to retrieve the display name of the card type
    public String getDisplayName() {
        return displayName;
    }

    // Optional: You can override toString() if you want to return the display name when printing the enum
    @Override
    public String toString() {
        return this.displayName;
    }

    // Optional: Add a method to get an enum by name if needed
    public static CreditCardType fromString(String name) {
        for (CreditCardType type : CreditCardType.values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown card type: " + name);
    }
}
