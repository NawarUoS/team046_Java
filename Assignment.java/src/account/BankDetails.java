package src.account;

import src.util.HashedBankDetailsGenerator;

public class BankDetails {
    private String userID;
    private String cardName;
    private String cardHolder;
    private String cardNumberHash;
    private String expiryDate;

    // Constructor
    public BankDetails(String cardName, String cardHolder, long cardNumber,
                       String expiryDate, String userID) {
        this.cardName = cardName;
        this.cardHolder = cardHolder;
        this.cardNumberHash = HashedBankDetailsGenerator.hashBankDetail(
                String.valueOf(cardNumber),userID);
        this.expiryDate = expiryDate;
        this.userID = userID;
    }

    public BankDetails(String cardName, String cardHolder,
                   String cardNumberHash, String expiryDate, String userID) {
        this.cardName = cardName;
        this.cardHolder = cardHolder;
        this.cardNumberHash = cardNumberHash;
        this.expiryDate = expiryDate;
        this.userID = userID;
    }

    // Getter methods
    public String getUserID() { 
        return userID; }

    public String getCardName() {
        return cardName;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNumberHash() {
        return cardNumberHash;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    // Setter methods
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumberHash =
            HashedBankDetailsGenerator.hashBankDetail(
                    String.valueOf(cardNumber), userID);
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
