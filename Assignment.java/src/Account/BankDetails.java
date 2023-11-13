package src.Account;

public class BankDetails {
    private String cardName;
    private String cardHolder;
    private int[] cardNumber = new int[16];
    private String expiryDate;
    private int securityCode;

    //Get methods
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
}
