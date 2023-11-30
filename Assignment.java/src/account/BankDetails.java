package src.account;

public class BankDetails {
    private String userID;
    private String cardName;
    private String cardHolder;
    private long cardNumber;
    private String expiryDate;

    // Constructor
    public BankDetails(String cardName, String cardHolder, long cardNumber,
                       String expiryDate, String userID) {
        this.cardName = cardName;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
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

    public long getCardNumber() {
        return cardNumber;
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

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
