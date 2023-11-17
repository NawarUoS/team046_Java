package src.account;

public class BankDetails {
    private String cardName;
    private String cardHolder;
    private int[] cardNumber = new int[16];
    private String expiryDate;
    private int securityCode;

    // Constructor
    public BankDetails(String cardName, String cardHolder, int[] cardNumber,
                       String expiryDate, int securityCode) {
        this.cardName = cardName;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
    }

    // Getter methods
    public String getCardName() {
        return cardName;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public int[] getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    // Setter methods
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setCardNumber(int[] cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }
}
